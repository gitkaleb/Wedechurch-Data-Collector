package com.gcme.wedechurchdatacollector.Activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.gcme.wedechurchdatacollector.Activity.LoginActivity;
import com.gcme.wedechurchdatacollector.PrefManagers.UserPrefManager;
import com.gcme.wedechurchdatacollector.R;
import com.gcme.wedechurchdatacollector.Services.APIResponseService;
import com.gcme.wedechurchdatacollector.Services.RequestCallback;
import com.gcme.wedechurchdatacollector.Services.RequestServices;
import com.gcme.wedechurchdatacollector.Services.SendRequestService;
import com.gcme.wedechurchdatacollector.Services.signUpProcessResult;
import com.gcme.wedechurchdatacollector.model.User;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;

/**
 * Created by bengeos on 7/24/17.
 */

public class SignUpActivity extends AppCompatActivity  implements RequestCallback, signUpProcessResult {

    private static final String TAG = "SignupActivity";

    private Context myContext;
    private TextView btnLogin;
    private SendRequestService mySendRequestService;
    private ProgressDialog myProgressDialog;
    private UserPrefManager userPrefManager;
    private User New_User;
    private Gson myParser;
    EditText _nameFText;
    EditText _nameLText;
    EditText _countryText;
    EditText _emailText;
    EditText _mobileText;
    EditText _passwordText;
    EditText _reEnterPasswordText;
    AppCompatButton btnSignUp;
    TextView loginLink;
    private  AlertDialog.Builder builder;
    private APIResponseService apiResponseService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        myContext = this;
        apiResponseService = new APIResponseService();
        myProgressDialog = new ProgressDialog(myContext);
        userPrefManager = new UserPrefManager(myContext);
        loginLink=(TextView) findViewById(R.id.link_login);
        _nameFText=(EditText)findViewById (R.id.input_first_name);
        _nameLText=(EditText)findViewById(R.id.input_last_name);
        _emailText=(EditText)findViewById(R.id.input_email);
        _mobileText=(EditText)findViewById(R.id.input_mobile);
        _passwordText=(EditText)findViewById(R.id.input_password);
        _reEnterPasswordText=(EditText)findViewById(R.id.input_reEnterPassword);

        myParser = new Gson();

        btnSignUp=(AppCompatButton) findViewById(R.id.btn_signup);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myContext.startActivity(intent);

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            Toast.makeText(myContext,"Invalid information provided!!!",Toast.LENGTH_LONG).show();
            return;
        }else {
            sendRegisterRequest();
        }




    }


    public boolean validate() {
        boolean valid = true;

        String fname = _nameFText.getText().toString();
        String lname = _nameLText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (fname.isEmpty() || fname.length() < 3) {
            _nameFText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameFText.setError(null);
        }

        if (lname.isEmpty() || lname.length() < 3) {
            _nameLText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameLText.setError(null);
        }



        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }



    public void sendRegisterRequest() {
        myProgressDialog.setTitle("Registering ....");
        myProgressDialog.show();


        New_User = new User();
        New_User.setUser_pass(_passwordText.getText().toString());
        New_User.setEmail(_emailText.getText().toString());
        New_User.setPhone(_mobileText.getText().toString());
        New_User.setCountry(_countryText.getText().toString());
        New_User.setFirst_name(_nameFText.getText().toString());
        New_User.setLast_name(_nameLText.getText().toString());

        final ProgressDialog myDialog = new ProgressDialog(this);
        myDialog.setTitle(R.string.app_name);
        myDialog.setMessage("Creating Account ....");
        myDialog.show();




        List<Pair<String, String>> Send_Param = new ArrayList<>();
        Send_Param.add(new kotlin.Pair<String, String>("user_name", New_User.getUser_name()));
        Send_Param.add(new kotlin.Pair<String, String>("user_pass", New_User.getPhone()));
        Send_Param.add(new kotlin.Pair<String, String>("service", RequestServices.REGISTRATION_REQUEST.SERVICE.toString()));
        Send_Param.add(new kotlin.Pair<String, String>("param", myParser.toJson(New_User)));


        mySendRequestService = new SendRequestService(Send_Param, "http://192.168.0.39/wedechurch_api/public/api",RequestServices.REGISTRATION_REQUEST.SERVICE.toString(),this);
        mySendRequestService.processRequest(this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void successCallback(String str,String service,Context context) {
        if(service.equalsIgnoreCase(RequestServices.REGISTRATION_REQUEST.SERVICE.toString())) {
            myProgressDialog.cancel();
            apiResponseService.processSignupResponse(str, this);
        }

    }

    @Override
    public void failedCallback(String string) {
        myProgressDialog.cancel();
    }



    @Override
    public void onProcessError(String error_string) {
        Toast.makeText(myContext,"Something went wrong try again",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserSignup() {
        Toast.makeText(myContext,"Welcome to Wedechurch!!!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(myContext, LoginActivity.class);
        intent.putExtra("uname",_nameFText.getText().toString());
        intent.putExtra("fname",_nameLText.getText().toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        myContext.startActivity(intent);
        finish();
    }

    @Override
    public void onSignupFailed() {
        Toast.makeText(myContext,"please check the fields and try again",Toast.LENGTH_SHORT).show();
    }
}