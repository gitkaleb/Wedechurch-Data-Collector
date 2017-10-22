package com.gcme.wedechurchdatacollector.Services;


import com.gcme.wedechurchdatacollector.model.Church;
import com.gcme.wedechurchdatacollector.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kzone on 2/4/2017.
 */

public class APIResponseService {
    public void processLoginResponse(String jsonResponse, loginProcessResult loginProcessResult){
        try {

            JSONObject jsonObject3 = new JSONObject(jsonResponse);
             if(!jsonObject3.isNull("response")){
                 JSONObject jsonObject2 =  jsonObject3.getJSONObject("response");


                 User.deleteAll(User.class);
//
//                JSONArray jsonArray = jsonObject.getJSONObject("response");
//
//                JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                User New_User = new User();
                New_User.setUser_name(jsonObject2.getString("user_name"));
                New_User.setEmail(jsonObject2.getString("email"));
                New_User.setSex(jsonObject2.getString("sex"));
                New_User.setPhone(jsonObject2.getString("phone"));
                New_User.setCountry(jsonObject2.getString("country"));
                New_User.setFirst_name(jsonObject2.getString("first_name"));
                New_User.setLast_name(jsonObject2.getString("last_name"));



                loginProcessResult.onUserLogin(New_User);


            }else {
                loginProcessResult.onUnAutorizedUserLogin();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            loginProcessResult.onProcessError(e.toString());
        }
    }
    public void processSignupResponse(String jsonResponse, signUpProcessResult signUpProcessResult){
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if(!jsonObject.isNull("response")){

                signUpProcessResult.onUserSignup();

            }else {
                signUpProcessResult.onSignupFailed();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            signUpProcessResult.onProcessError(e.toString());
        }
    }

    public void processRegisterResponse(String jsonResponse, registerProcessResult regiserProcessResult){
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if(!jsonObject.isNull("response")){

                regiserProcessResult.onChurchRegister();

            }else {
                regiserProcessResult.onRegisterFailed();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            regiserProcessResult.onProcessError(e.toString());
        }
    }

    public void processScheduleResponse(String jsonResponse,scheduleProcessResult regiserProcessResult){
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if(!jsonObject.isNull("response")){

                regiserProcessResult.onChurchRegister();

            }else {
                regiserProcessResult.onRegisterFailed();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            regiserProcessResult.onProcessError(e.toString());
        }
    }

    public ArrayList processGetChurchData(String json_array, NewChurchData newChurches){
        ArrayList<Church> churchlist=new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(json_array);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            if(jsonArray.length()>0){
                for(int i=0;i<jsonArray.length();i++){
                    boolean already_there = false;
                    Church newChurchObject = new Church();
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    newChurchObject.setsirId(jsonObject2.getString("id"));
                    newChurchObject.setChurchName(jsonObject2.getString("name"));
                    newChurchObject.setCountry(jsonObject2.getString("country"));
                    newChurchObject.setCities(jsonObject2.getString("cities"));
                    newChurchObject.setLocation(jsonObject2.getString("location"));
                    newChurchObject.setLatitude(jsonObject2.getString("latitude"));
                    newChurchObject.setLongitude(jsonObject2.getString("longitude"));
                    newChurchObject.setPhone(jsonObject2.getString("Phone"));
                    newChurchObject.setBanner(jsonObject2.getString("banner"));
                    newChurchObject.setWebUrl(jsonObject2.getString("WebUrl"));
                    newChurchObject.setDenomination(jsonObject2.getString("denomination"));
                    newChurchObject.setDescription(jsonObject2.getString("description"));
                    newChurchObject.setLogo(jsonObject2.getString("logo"));
                    newChurchObject.setParentChurchId(jsonObject2.getString("parent_church_id"));
                    newChurchObject.setState(jsonObject2.getString("state"));

                    for (Church churchObject: com.gcme.wedechurchdatacollector.model.Church.listAll(com.gcme.wedechurchdatacollector.model.Church.class)) {
                        if(churchObject.getsirId().equals(newChurchObject.getsirId())){
                            already_there = true;
                            newChurchObject.setId(churchObject.getId());
                            break;
                        }
                    }
                    if(already_there){
                        newChurchObject.update();
                        churchlist.add(newChurchObject);
                        newChurches.onChurchUpdated();
                    }else {
                        newChurches.onChurchAdded();
                        churchlist.add(newChurchObject);
                        newChurchObject.save();
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return churchlist;
    }


}
