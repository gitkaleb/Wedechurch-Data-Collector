package com.gcme.wedechurchdatacollector.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by kzone on 2/4/2017.
 */

public class Task extends SugarRecord {

    @Unique
    private int tasksirId;

    public Task() {

    }

    public int gettasksirId() {
        return tasksirId;
    }
    public void setId(int id) {
        this.tasksirId = id;
    }




    public Task(int taskId){
        tasksirId=taskId;
    }


}
