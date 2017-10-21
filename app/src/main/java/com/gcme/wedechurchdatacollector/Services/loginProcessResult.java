package com.gcme.wedechurchdatacollector.Services;


import com.gcme.wedechurchdatacollector.model.User;

/**
 * Created by kzone on 2/4/2017.
 */

public interface loginProcessResult {

    void onProcessError(String error_string);
    void onUserLogin(User user);
    void onUnAutorizedUserLogin();
}
