package com.example.lms.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Utils {

    public static void setSharedPref(Context context, SharedPref pref){
        SharedPreferences preferences = context.getSharedPreferences("loginStatus",0);
        SharedPreferences.Editor editor  = preferences.edit();
        editor.putBoolean("status",pref.isStatus());
        editor.putString("id",pref.getId());
        editor.apply();
    }
    public static SharedPref getSharedPref(Context context){
        SharedPreferences preferences = context.getSharedPreferences("loginStatus",0);
        boolean a = preferences.getBoolean("status",false);
        String id = preferences.getString("id",null);

        return new SharedPref(id,a);
    }


    public static data getProfileData(Context context){
        SharedPreferences preferences = context.getSharedPreferences("AdminData",0);
        String profileObj = preferences.getString("AdminProfile",null);
        return new Gson().fromJson(profileObj, new TypeToken<data>(){}.getType());
    }

    public static void setProfileData(data userDetail,Context context){
        SharedPreferences preferences = context.getSharedPreferences("AdminData",0);
        String profileObj = new Gson().toJson(userDetail);
        SharedPreferences.Editor editor  = preferences.edit();
        editor.putString("AdminProfile",profileObj);
        editor.apply();
    }
}
