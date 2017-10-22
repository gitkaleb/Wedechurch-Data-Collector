package com.gcme.wedechurchdatacollector.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gcme.wedechurchdatacollector.Adapters.ScheduleAdapter;
import com.gcme.wedechurchdatacollector.PrefManagers.UserPrefManager;
import com.gcme.wedechurchdatacollector.R;
import com.gcme.wedechurchdatacollector.Services.APIResponseService;
import com.gcme.wedechurchdatacollector.Services.RequestCallback;
import com.gcme.wedechurchdatacollector.Services.RequestServices;
import com.gcme.wedechurchdatacollector.Services.SendRequestService;
import com.gcme.wedechurchdatacollector.Services.registerProcessResult;
import com.gcme.wedechurchdatacollector.model.Church;
import com.gcme.wedechurchdatacollector.model.Schedules;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

/**
 * A simple {@link Fragment} subclass.
 */
public class churchschedule extends Fragment implements RequestCallback, registerProcessResult {

    EditText input_church_program,input_church_end_date,input_church_start_time;
    AppCompatButton btn_submit;
    ListView ScheduleList;
    private APIResponseService apiResponseService;
    private Schedules New_Church_schedule;
    private SendRequestService mySendRequestService;
    private ProgressDialog myProgressDialog;
    private Gson myParser;
    List<Schedules> List=new ArrayList<Schedules>();
    private static final String TAG = "SignupActivity";
    FloatingActionButton getchurchdirection;

    ScheduleAdapter scheduleAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_churchschedule, container, false);

        input_church_program= (EditText) view.findViewById(R.id.input_church_program);
        input_church_start_time= (EditText) view.findViewById(R.id.input_church_start_time);
        input_church_end_date= (EditText) view.findViewById(R.id.input_church_end_date);
        ScheduleList= (ListView) view.findViewById(R.id.ScheduleList);
        btn_submit= (AppCompatButton) view.findViewById(R.id.btn_submit);

        apiResponseService = new APIResponseService();
        apiResponseService = new APIResponseService();
        myProgressDialog = new ProgressDialog(getActivity());
        getchurchdirection= (FloatingActionButton) view.findViewById(R.id.getchurchdirection);

        scheduleAdapter = new ScheduleAdapter(List, getActivity());
        ScheduleList.setAdapter(scheduleAdapter);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        getchurchdirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                New_Church_schedule = new Schedules();
                New_Church_schedule.setSchedule_name(input_church_program.getText().toString());
                New_Church_schedule.setSchedule_Date(input_church_end_date.getText().toString());
                New_Church_schedule.setSchedule_Time(input_church_start_time.getText().toString());
                List.add(New_Church_schedule);
                scheduleAdapter.notifyDataSetChanged();


            }
        });
        return view;

}

    public boolean validate() {
        boolean valid = true;
        String program = input_church_program.getText().toString();
        String time = input_church_start_time.getText().toString();
        String date = input_church_end_date.getText().toString();


        if (program.isEmpty() ) {
            input_church_program.setError("insert program name to the fild");
            valid = false;
        } else {
            input_church_program.setError(null);
        }

        if (time.isEmpty() ) {
            input_church_start_time.setError("insert program time to the fild");
            valid = false;
        } else {
            input_church_start_time.setError(null);
        }

        if (date.isEmpty() ) {
            input_church_end_date.setError("insert program date to the fild");
            valid = false;
        } else {
            input_church_end_date.setError(null);
        }



        return valid;
    }
    public void sendRegisterRequest() {
        myProgressDialog.setTitle("Registering ....");
        myProgressDialog.show();






        final ProgressDialog myDialog = new ProgressDialog(getActivity());
        myDialog.setTitle(R.string.app_name);
        myDialog.setMessage("Registering Church ....");
        myDialog.show();

        Gson mygson=new Gson();
        List<Pair<String, String>> Send_Param = new ArrayList<>();
        Send_Param.add(new kotlin.Pair<String, String>("param", mygson.toJson(List)));
        mySendRequestService = new SendRequestService(Send_Param, "http://wede.api.myims.org/api/schedules", RequestServices.REGISTRATION_REQUEST.SCHEDULESERVICE.toString(),getActivity());
        mySendRequestService.processRequest(this);

    }

    @Override
    public void successCallback(String str,String service,Context context) {
        if(service.equalsIgnoreCase(RequestServices.REGISTRATION_REQUEST.ChurchSERVICE.toString())) {
            myProgressDialog.cancel();
            apiResponseService.processRegisterResponse(str, this);
        }

    }

    @Override
    public void failedCallback(String string) {
        myProgressDialog.cancel();
    }



    @Override
    public void onProcessError(String error_string) {
        Toast.makeText(getActivity(),"Something went wrong try again",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChurchRegister() {
        Toast.makeText(getActivity(),"Church Registered!!!",Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        intent.putExtra("uname",_nameFText.getText().toString());
//        intent.putExtra("fname",_nameLText.getText().toString());
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        myContext.startActivity(intent);
//        finish();
    }

    @Override
    public void onRegisterFailed() {
        Toast.makeText(getActivity(),"please check the fields and try again",Toast.LENGTH_SHORT).show();
    }



}
