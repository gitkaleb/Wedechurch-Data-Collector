package com.gcme.wedechurchdatacollector.Services;

import android.content.Context;

/**
 * Created by kzone on 2/4/2017.
 */

public interface RequestCallback {
    void successCallback(String string, String service, Context context);
    void failedCallback(String string);
}
