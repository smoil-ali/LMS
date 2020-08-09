package com.example.lms.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.lms.MainActivity;
import com.example.lms.Model.LoginResponse;
import com.example.lms.Model.SharedPref;
import com.example.lms.Model.Utils;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.databinding.ActivityLoginBinding;
import com.example.lms.dialogs.DeleteDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();
    ActivityLoginBinding binding;
    DeleteDialog deleteDialog = new DeleteDialog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setStatusBarGradiant(this);
        setContentView(binding.getRoot());
        binding.loginButton.setOnClickListener(view -> {
            Utils.HideKeyBoard(Login.this);
            validate();
        });

        if (Utils.getSharedPref(this).isStatus()){
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

    }

    private void validate(){

        if (!binding.userEmail.getText().toString().trim().matches("") ||
                !binding.userPassword.getText().toString().trim().matches("")){
            deleteDialog.setText("Logging!!!");
            Utils.openDialog(getSupportFragmentManager(),deleteDialog);
            LoginUser(binding.userEmail.getText().toString(),binding.userPassword.getText().toString());
        }else {
            binding.userPassword.setError("Empty Field");
            binding.userEmail.setError("Empty Field");
        }
    }

    private void LoginUser(String email,String password){
        AcademyApis apis = RetrofitService.createService(AcademyApis.class);
        Call<LoginResponse> loginResponseCall = apis.getLoginResponse(email,password);
        Log.i(TAG,loginResponseCall.request().url()+"");
        loginResponseCall.enqueue(new CallbackListener());
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getDrawable(R.drawable.statusbar_bg);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    private class CallbackListener implements Callback<LoginResponse>{

        @Override
        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

            if (response.isSuccessful()){
                LoginResponse loginResponse = response.body();
                if (loginResponse.getCode().equals("200")){
                    if(!loginResponse.getStatus().equals("fail")){
                        deleteDialog.dismiss();
                        Log.i(TAG,loginResponse.getData().getUser().getId()+"");
                        Utils.setProfileData(loginResponse.getData(),Login.this,true);
                        Utils.setSharedPref(Login.this,new SharedPref(loginResponse.getData().getUser().getId(),true));
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    }else {
                        deleteDialog.dismiss();
                        new AlertDialog.Builder(Login.this)
                                .setTitle(loginResponse.getStatus())
                                .setMessage(loginResponse.getMessage())
                                .setPositiveButton("ok", (dialogInterface, i) -> dialogInterface.dismiss()).show();
                    }
                }else {
                    deleteDialog.dismiss();
                    new AlertDialog.Builder(Login.this)
                            .setTitle(loginResponse.getStatus())
                            .setMessage(loginResponse.getMessage())
                            .setPositiveButton("ok", (dialogInterface, i) -> dialogInterface.dismiss()).show();
                }
            }else {
                deleteDialog.dismiss();
                new AlertDialog.Builder(Login.this)
                        .setTitle("Login Failed")
                        .setMessage(response.message())
                        .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
            }
        }

        @Override
        public void onFailure(Call<LoginResponse> call, Throwable t) {
            deleteDialog.dismiss();
            new AlertDialog.Builder(Login.this)
                    .setTitle("Failed")
                    .setMessage(t.getMessage())
                    .setPositiveButton("ok", (dialogInterface, i) -> dialogInterface.dismiss()).show();
        }
    }
}