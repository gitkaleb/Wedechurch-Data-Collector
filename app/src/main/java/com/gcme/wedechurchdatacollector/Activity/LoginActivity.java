package com.gcme.wedechurchdatacollector.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.gcme.wedechurchdatacollector.HomeActivity;
import com.gcme.wedechurchdatacollector.PrefManagers.UserPrefManager;
import com.gcme.wedechurchdatacollector.R;
import com.gcme.wedechurchdatacollector.Services.APIResponseService;
import com.gcme.wedechurchdatacollector.Services.RequestCallback;
import com.gcme.wedechurchdatacollector.Services.RequestServices;
import com.gcme.wedechurchdatacollector.Services.SendRequestService;
import com.gcme.wedechurchdatacollector.Services.loginProcessResult;
import com.gcme.wedechurchdatacollector.model.User;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

/**
 * Created by bengeos on 7/24/17.
 */

public class LoginActivity extends AppCompatActivity implements RequestCallback, loginProcessResult {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private AppCompatButton btnLogin;
    private Context myContext;
    private TextView btnSignup, btnSkipLogin;
    private EditText txtEmail,txtPass;
    private ProgressDialog myProgressDialog;
    private SendRequestService mySendRequestService;
    private List<Pair<String,String>> SendParam;
    private UserPrefManager userPrefManager;
    TextView mForgotPassword;
    private APIResponseService apiResponseService;

    public static android.content.SharedPreferences SharedPreferences = null;

    // User Session Manager Class

    private static final String PREFER_NAME = null;
    String uname,name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiResponseService = new APIResponseService();
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();



        myContext = this;

        myProgressDialog = new ProgressDialog(myContext);

        userPrefManager = new UserPrefManager(myContext);

        txtEmail = (EditText) findViewById(R.id.input_email);
        txtPass = (EditText) findViewById(R.id.input_password);
        mForgotPassword=(TextView) findViewById (R.id.forgotPassword);
        btnLogin = (AppCompatButton) findViewById(R.id.btn_login);
        btnSkipLogin = (TextView) findViewById(R.id.link_skip);
        btnSkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myContext.startActivity(intent);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myProgressDialog.setTitle("Authenticating ...");
                myProgressDialog.show();
                sendLoginRequest();
            }
        });
        btnSignup = (TextView) findViewById(R.id.link_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myContext.startActivity(intent);
            }
        });


        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(getApplicationContext(), RecoveryActivity.class);
                startActivity(i);

                finish();


            }
        });

        if(bd != null)
        {
            name  = bd.getString("fname")+" "+bd.getString("lname");
            uname  = bd.getString("uname");

            txtEmail.setText(uname);

        }

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + userPrefManager.isLoggedIn(),
                Toast.LENGTH_LONG).show();

        if (userPrefManager.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent main = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(main);
            finish();
        }

    }

    private void sendLoginRequest() {
        if(txtEmail.getText().toString().length()>3 && txtPass.getText().toString().length()>5){
            SendParam = new ArrayList<>();
            SendParam.add(new Pair<String, String>(RequestServices.AUTHENTICATION.USER_EMAIL.toString(),txtEmail.getText().toString()));
            SendParam.add(new Pair<String, String>(RequestServices.AUTHENTICATION.USER_PASS.toString(),txtPass.getText().toString()));
            SendParam.add(new Pair<String, String>(RequestServices.MainServices,RequestServices.AUTHENTICATION.SERVICE.toString()));
            SendParam.add(new Pair<String, String>(RequestServices.PARAM,""));

            mySendRequestService = new SendRequestService(SendParam, "http://192.168.0.39/wedechurch_api/public/api", RequestServices.AUTHENTICATION.SERVICE.toString(),this);
            mySendRequestService.processRequest(this);
        }else {
            myProgressDialog.cancel();
            Toast.makeText(myContext,"Invalid phone number and password used",Toast.LENGTH_SHORT).show();
            txtPass.setText("");
            txtEmail.setText("");
        }
    }
    public void getMainActivity(){
        Intent intent = new Intent(myContext, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        myContext.startActivity(intent);
        finish();
    }
    @Override
    public void successCallback(String str,String service,Context context) {
        if(service.equalsIgnoreCase(RequestServices.AUTHENTICATION.SERVICE.toString())) {
            Toast.makeText(myContext, "Success response\n" + str, Toast.LENGTH_SHORT).show();
            myProgressDialog.cancel();
            apiResponseService.processLoginResponse(str, this);


        }

//
    }

    @Override
    public void failedCallback(String string) {
        Toast.makeText(myContext,string,Toast.LENGTH_SHORT).show();
        myProgressDialog.cancel();
    }




    @Override
    public void onProcessError(String error_string) {
        Toast.makeText(myContext,"This is Process error ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserLogin(User user) {
        if (uname == null && name == null) {
            uname = " ";
            name = " ";
        }

        user.setUser_pass(txtPass.getText().toString());
        user.save();


        userPrefManager.setLogin(true,name,uname,txtPass.getText().toString(),txtEmail.getText().toString());
        Intent intent = new Intent(myContext, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        myContext.startActivity(intent);



        finish();
    }

    @Override
    public void onUnAutorizedUserLogin() {

        Toast.makeText(myContext,"check you inserted the correct user name and password ",Toast.LENGTH_SHORT).show();
    }

}
