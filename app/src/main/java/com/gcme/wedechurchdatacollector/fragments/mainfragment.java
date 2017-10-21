package com.gcme.wedechurchdatacollector.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gcme.wedechurchdatacollector.Activity.LoginActivity;
import com.gcme.wedechurchdatacollector.PrefManagers.UserPrefManager;
import com.gcme.wedechurchdatacollector.R;
import com.gcme.wedechurchdatacollector.Services.APIResponseService;
import com.gcme.wedechurchdatacollector.Services.RequestCallback;
import com.gcme.wedechurchdatacollector.Services.RequestServices;
import com.gcme.wedechurchdatacollector.Services.SendRequestService;
import com.gcme.wedechurchdatacollector.Services.registerProcessResult;
import com.gcme.wedechurchdatacollector.Services.signUpProcessResult;
import com.gcme.wedechurchdatacollector.model.Church;
import com.gcme.wedechurchdatacollector.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

/**
 * A simple {@link Fragment} subclass.
 */
public class mainfragment extends Fragment implements RequestCallback, registerProcessResult {

    EditText input_church_name,input_church_email,input_church_phone,input_church_website,input_church_location,input_church_program,input_church_end_date,input_church_start_time;
    AppCompatButton btn_pic,btn_gps,btn_submit;
    FloatingActionButton getchurchdirection;
    ListView ScheduleList;
    private TextView btnLogin;
    private SendRequestService mySendRequestService;
    private ProgressDialog myProgressDialog;
    private UserPrefManager userPrefManager;
    private Church New_Church;
    private Gson myParser;
    private APIResponseService apiResponseService;
    private static final String TAG = "SignupActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mainfragment, container, false);
        input_church_name= (EditText) view.findViewById(R.id.input_church_name);
        input_church_email= (EditText) view.findViewById(R.id.input_church_email);
        input_church_phone= (EditText) view.findViewById(R.id.input_church_phone);
        input_church_website= (EditText) view.findViewById(R.id.input_church_website);
        input_church_location= (EditText) view.findViewById(R.id.input_church_location);
        input_church_program= (EditText) view.findViewById(R.id.input_church_program);
        input_church_start_time= (EditText) view.findViewById(R.id.input_church_start_time);
        input_church_end_date= (EditText) view.findViewById(R.id.input_church_end_date);


        btn_pic= (AppCompatButton) view.findViewById(R.id.btn_pic);
        btn_gps= (AppCompatButton) view.findViewById(R.id.btn_gps);
        btn_submit= (AppCompatButton) view.findViewById(R.id.btn_submit);


        getchurchdirection= (FloatingActionButton) view.findViewById(R.id.getchurchdirection);

        ScheduleList= (ListView) view.findViewById(R.id.ScheduleList);

        apiResponseService = new APIResponseService();
        apiResponseService = new APIResponseService();
        myProgressDialog = new ProgressDialog(getActivity());
        userPrefManager = new UserPrefManager(getActivity());

        btn_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        myParser = new Gson();


        return view;
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            Toast.makeText(getActivity(),"Invalid information provided!!!",Toast.LENGTH_LONG).show();
            return;
        }else {
            sendRegisterRequest();
        }




    }


    public boolean validate() {
        boolean valid = true;
        String churchname = input_church_name.getText().toString();
        String email = input_church_email.getText().toString();
        String website = input_church_website.getText().toString();
        String mobile = input_church_phone.getText().toString();
        String location = input_church_location.getText().toString();
        String program = input_church_program.getText().toString();
        String time = input_church_start_time.getText().toString();
        String date = input_church_end_date.getText().toString();

        if (churchname.isEmpty() ) {
            input_church_name.setError("insert church name");
            valid = false;
        } else {
            input_church_name.setError(null);
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
        } else {
            input_church_email.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=9) {
            input_church_phone.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            input_church_phone.setError(null);
        }

        if (program.isEmpty() ) {
            input_church_program.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_church_program.setError(null);
        }

        if (time.isEmpty() ) {
            input_church_start_time.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_church_start_time.setError(null);
        }

        if (date.isEmpty() ) {
            input_church_end_date.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_church_end_date.setError(null);
        }

        if (location.isEmpty() ) {
            input_church_location.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_church_location.setError(null);
        }



        return valid;
    }
    public void sendRegisterRequest() {
        myProgressDialog.setTitle("Registering ....");
        myProgressDialog.show();
        EditText input_church_name,input_church_email,input_church_phone,input_church_website,input_church_location,input_church_program,input_church_end_date,input_church_start_time;


        New_Church = new Church();
        New_Church.setBanner(_passwordText.getText().toString());
        New_Church.setChurchName(input_church_name.getText().toString());
        New_Church.setCities(_mobileText.getText().toString());
        New_Church.setPhone("+251"+input_church_phone.getText().toString());
        New_Church.setCountry(_countryText.getText().toString());
        New_Church.setDenomination(_nameFText.getText().toString());
        New_Church.setLocation(input_church_location.getText().toString());
        New_Church.setWebUrl(input_church_website.getText().toString());
        New_Church.setState(_nameLText.getText().toString());
        New_Church.setLongitude(_nameLText.getText().toString());
        New_Church.setEmail(input_church_email.getText().toString());
       
        final ProgressDialog myDialog = new ProgressDialog(getActivity());
        myDialog.setTitle(R.string.app_name);
        myDialog.setCancelable(false);
        myDialog.setMessage("Registering Church ....");
        myDialog.show();




        List<Pair<String, String>> Send_Param = new ArrayList<>();
        Send_Param.add(new kotlin.Pair<String, String>("param", myParser.toJson(New_Church)));


        mySendRequestService = new SendRequestService(Send_Param, "http://wede.api.myims.org/api/churches",RequestServices.REGISTRATION_REQUEST.SERVICE.toString(),getActivity());
        mySendRequestService.processRequest(this);

    }

    @Override
    public void successCallback(String str,String service,Context context) {
        if(service.equalsIgnoreCase(RequestServices.REGISTRATION_REQUEST.SERVICE.toString())) {
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
