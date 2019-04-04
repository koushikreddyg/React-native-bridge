package com.iosbridge;

import android.widget.Toast;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class CounterModule extends ReactContextBaseJavaModule {

  
    public static final Number Counter = 20;

  public CounterModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "Counter";
  }

  @ReactMethod
  public void show(Callback cb) {
    cb.invoke("koushikred");
  }

}