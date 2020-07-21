package com.example.lms.Model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Utils {

    public static void openDialog(FragmentManager manager, DialogFragment fragment){
        FragmentTransaction ft = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag("dialog");
        if(prev != null)
        {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        fragment.show(ft, "dialog");
    }


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
