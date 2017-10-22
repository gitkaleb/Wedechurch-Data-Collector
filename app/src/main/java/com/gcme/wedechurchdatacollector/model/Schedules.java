package com.gcme.wedechurchdatacollector.model;

import com.orm.SugarRecord;

/**
 * Created by kzone on 12/10/2016.
 */

public class Schedules extends SugarRecord {


    private String schedule_name,schedule_Date,schedule_Time;

    public Schedules(String catagory, String date, String time) {
        schedule_name = catagory;
        schedule_Date = date;
        schedule_Time = time;
    }

    public Schedules() {

    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String catagory) {
        schedule_name = catagory;
    }

    public String getSchedule_Date() {
        return schedule_Date;
    }

    public void setSchedule_Date(String date) {
        schedule_Date = date;
    }

    public String getSchedule_Time() {
        return schedule_Time;
    }

    public void setSchedule_Time(String time) {
        schedule_Time = time;
    }
}
