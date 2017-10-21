package com.gcme.wedechurchdatacollector.model;

import com.orm.SugarRecord;

/**
 * Created by kzone on 12/10/2016.
 */

public class Schedules extends SugarRecord {


    private String Catagory,Date,Time;

    public Schedules(String catagory, String date, String time) {
        Catagory = catagory;
        Date = date;
        Time = time;
    }

    public Schedules() {

    }

    public String getCatagory() {
        return Catagory;
    }

    public void setCatagory(String catagory) {
        Catagory = catagory;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
