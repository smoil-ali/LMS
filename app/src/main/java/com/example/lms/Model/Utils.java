package com.example.lms.Model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

public class Utils {

    public static void showDialog(Context context,String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
    }

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

    public static void HideKeyBoard(Context context){
        View view = ((Activity)context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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

    public static void setProfileData(data userDetail,Context context,boolean check){
        if (check){
            String json = userDetail.getUser().getSocial_links();
            SocialLinks model = new Gson().fromJson(json,SocialLinks.class);
            userDetail.getUser().setFacebook(model.getFacebook());
            userDetail.getUser().setTwitter(model.getTwitter());
            userDetail.getUser().setLinkedin(model.getLinkedin());
        }
        SharedPreferences preferences = context.getSharedPreferences("AdminData",0);
        String profileObj = new Gson().toJson(userDetail);
        Log.i("'json",profileObj);
        SharedPreferences.Editor editor  = preferences.edit();
        editor.putString("AdminProfile",profileObj);
        editor.apply();
    }

}
