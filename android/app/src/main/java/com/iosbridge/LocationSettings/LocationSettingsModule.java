package com.iosbridge.LocationSettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.facebook.react.bridge.NativeArray;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

public class LocationSettingsModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;
    private static final String EVENT_STATUS_CHANGE = "OnStatusChange";
    private Boolean isReceive = false;
    private BroadcastReceiver mGpsSwitchStateReceiver = null;

    private static String message;

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

    @ReactMethod
    public void arrayForEach(Promise promise){
        HashMap<String, String> map= new HashMap<>();

        String msg= "[{ name= 'Koushik', age=24},{name= 'Yunus', age= 30}]";
        promise.resolve(message);

    }
    @ReactMethod
    public void sendData(ReadableMap str){







        str.getArray("array").toArrayList().forEach(i->{
            HashMap<String, HashMap<String, String>> mapMap= (HashMap<String, HashMap<String, String>>)i;

            HashMap<String, String> stringMapi= new HashMap<>();



            for(String kkset: mapMap.keySet()){
                if(kkset.contains("job")){
                    stringMapi.put("location",mapMap.get("job").get("location"));
                    stringMapi.put("comapny",mapMap.get("job").get("company"));

                    Log.i(" Koushik ",""+  stringMapi.get("location"));
                }
            }



        });






    }

    @ReactMethod
    public void sendIndObject(ReadableMap obj){
        Log.i(" "+ obj.toHashMap().get("name")+", your age is ", ""+obj.toHashMap().get("age"));
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