package com.gcme.wedechurchdatacollector.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by kzone on 2/4/2017.
 */

public class Fav extends SugarRecord {


    @Unique
    private String churchId;


    public Fav() {
    }

    public Fav(String userid, String churchid){

        this.churchId=churchid;
    }






    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String  churchId) {
        this.churchId = churchId;
    }



}
