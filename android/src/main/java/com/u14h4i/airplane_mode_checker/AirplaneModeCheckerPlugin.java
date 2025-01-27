package com.u14h4i.airplane_mode_checker;

import androidx.annotation.NonNull;

import android.os.Build;
import android.provider.Settings;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * AirplaneModeCheckerPlugin
 */
public class AirplaneModeCheckerPlugin implements FlutterPlugin, MethodCallHandler {
    private MethodChannel channel;

    private FlutterPluginBinding mFlutterPluginBinding;

    public AirplaneModeCheckerPlugin() {

    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "airplane_mode_checker");
        channel.setMethodCallHandler(this);
        mFlutterPluginBinding = flutterPluginBinding;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("checkAirplaneMode")) {
            if (isAirModeOn())
                result.success("ON");
            else
                result.success("OFF");
        } else {
            result.notImplemented();
        }
    }

    private Boolean isAirModeOn() {
        Boolean isAirplaneMode;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isAirplaneMode = Settings.System.getInt(mFlutterPluginBinding.getApplicationContext().getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) == 1;
        } else {
            isAirplaneMode = Settings.Global.getInt(mFlutterPluginBinding.getApplicationContext().getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
        }
        return isAirplaneMode;
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
