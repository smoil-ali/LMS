package com.example.lms;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lms.Model.AddBasicUserModel;
import com.example.lms.Model.AddUserLogindata;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Model.Utils;
import com.example.lms.Model.addContainer;
import com.example.lms.Model.addUserPaymentData;
import com.example.lms.Model.addUserSocialData;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.AddCategory;
import com.example.lms.activity.AddStudent;
import com.example.lms.databinding.FragmentStudentFinishBinding;

import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Student_finish extends Fragment {


    final String TAG = Student_finish.class.getSimpleName();
    FragmentStudentFinishBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentFinishBinding.inflate(inflater,container,false);
        Log.i(TAG, String.valueOf(new Date().getTime()));
        binding.submit.setOnClickListener(view -> {
            validate();
        });
        return binding.getRoot();
    }

    public void validate(){
        if (!addContainer.getModel().getFirstName().trim().matches("") &&
              !addContainer.getModel().getLastName().trim().matches("") &&
              !addContainer.getAddUserLogindata().getEmail().trim().matches("")&&
              !addContainer.getAddUserLogindata().getPassword().trim().matches("")){

            //Add User
            binding.finishProgressBar.setVisibility(View.VISIBLE);
            binding.submit.setVisibility(View.GONE);
            addUser();

        }else if (addContainer.getModel().getFirstName().trim().matches("")){
            new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage("First Name Required")
                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();

        }else if (addContainer.getModel().getLastName().trim().matches("")){
            new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage("Last Name Required")
                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
        }else if (addContainer.getAddUserLogindata().getEmail().trim().matches("")){
            new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage("Email Required")
                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
        }else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage("Password Required")
                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
        }
    }

    public void makeEmpty(){
        addContainer.setModel(new AddBasicUserModel());
        addContainer.setAddUserLogindata(new AddUserLogindata());
        addContainer.setAddUserPaymentData(new addUserPaymentData());
        addContainer.setAddUserSocialData(new addUserSocialData());
    }


    public void addUser(){
        AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH-mm-ss");
        String date = String.valueOf(new Date().getTime());
        Log.i(TAG,date);
        Observable<StudentResponse> observable = academyApis.AddUser(AddStudent.ROLE,addContainer.getModel().getFirstName(),
                addContainer.getModel().getLastName(),addContainer.getModel().getBiography(),addContainer.getAddUserLogindata().getEmail(),
                addContainer.getAddUserLogindata().getPassword(),addContainer.getAddUserPaymentData().getPaypalClientId(),
                addContainer.getAddUserPaymentData().getPaypalSecretId(),addContainer.getAddUserPaymentData().getStripeSecretKey(),
                addContainer.getAddUserPaymentData().getStripePublicKey(),date,"",
                addContainer.getAddUserSocialData().getFacebook(),addContainer.getAddUserSocialData().getTwitter(),
                addContainer.getAddUserSocialData().getLinkedin());

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StudentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG,"subscribed");
                    }

                    @Override
                    public void onNext(StudentResponse studentResponse) {
                        if (!studentResponse.getStatus().equals("failed") && studentResponse.getCode().equals("200")){
                            new AlertDialog.Builder(getContext())
                                    .setTitle(studentResponse.getStatus())
                                    .setMessage(studentResponse.getMessage())
                                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                            binding.finishProgressBar.setVisibility(View.GONE);
                            binding.submit.setVisibility(View.VISIBLE);
                            makeEmpty();
                        }else {
                            new AlertDialog.Builder(getContext())
                                    .setTitle(studentResponse.getStatus())
                                    .setMessage(studentResponse.getMessage())
                                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                            binding.finishProgressBar.setVisibility(View.GONE);
                            binding.submit.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Error")
                                .setMessage(e.getMessage())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                        binding.finishProgressBar.setVisibility(View.GONE);
                        binding.submit.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"complete");
                    }
                });
    }
}