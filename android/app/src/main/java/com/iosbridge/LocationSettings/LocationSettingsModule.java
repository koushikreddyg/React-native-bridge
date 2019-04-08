package com.iosbridge.LocationSettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class LocationSettingsModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;
    private static final String EVENT_STATUS_CHANGE = "OnStatusChange";
    private Boolean isReceive = false;
    private BroadcastReceiver mGpsSwitchStateReceiver = null;

    public LocationSettingsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "LocationSettings";
    }

    @ReactMethod
    public void show(Callback cb) {
        cb.invoke("Welcome to the application");

    }

    @ReactMethod
    public void openGPSSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        reactContext.startActivity(intent);
    }

    @ReactMethod
    public void openLocationSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", reactContext.getPackageName(), null);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        reactContext.startActivity(intent);
    }

    @ReactMethod
    public void isLocationEnabled(Callback status) {
        LocationManager manager = (LocationManager) getCurrentActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = false;

        try {
            isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        status.invoke(isGPSEnabled);

    }

    @ReactMethod
    public void _startListen() {
        _stopListen();

        try {
            mGpsSwitchStateReceiver = new GPSProvideChangeReceiver();
            reactContext.registerReceiver(mGpsSwitchStateReceiver,
                    new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
            isReceive = true;
        } catch (Exception ex) {
        }
    }

    @ReactMethod
    public void _stopListen() {
        isReceive = false;
        try {
            // locationManager.removeGpsStatusListener(this);
            if (mGpsSwitchStateReceiver != null) {
                reactContext.unregisterReceiver(mGpsSwitchStateReceiver);
                mGpsSwitchStateReceiver = null;
            }
        } catch (Exception ex) {
        }
    }

    private final class GPSProvideChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.matches("android.location.PROVIDERS_CHANGED")) {
                if (isReceive) {
                    LocationManager locationManager = (LocationManager) getCurrentActivity()
                            .getSystemService(Context.LOCATION_SERVICE);
                    if (locationManager != null) {
                        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                        

                        if (reactContext != null) {
                            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                    .emit("locationProviderStatusChange", enabled);
                        }
                    }
                }
            }
        }
    }

}