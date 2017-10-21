package com.gcme.wedechurchdatacollector.model;

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by kzone on 10/2/2017.
 */

public class Home extends SugarRecord {


        @Unique
        public String churchId;

    public Home() {
    }

    public Home(Context context, String churchId) {
        this.churchId = churchId;
    }

    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }
}
