package com.gcme.wedechurchdatacollector.Services;

/**
 * Created by kzone on 2/4/2017.
 */

public interface registerProcessResult {

    void onProcessError(String error_string);
    void onChurchRegister();
    void onRegisterFailed();
}
