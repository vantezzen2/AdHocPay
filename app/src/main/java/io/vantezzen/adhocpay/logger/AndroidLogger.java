package io.vantezzen.adhocpay.logger;

import android.util.Log;

/**
 * AndroidLogger: Logger Adapter für das Android Logging System
 */
public class AndroidLogger implements Logger {
    @Override
    public void log(String start, String message) {
        Log.d(start, message);
    }
}
