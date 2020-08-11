package com.example.lms;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lms.Listener.deleteListener;
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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.ADD;
import static com.example.lms.Model.Constants.AddCourse;
import static com.example.lms.Model.Constants.FAILED;
import static com.example.lms.Model.Constants.RESPONSE_FAILED;
import static com.example.lms.Model.Constants.SUCCESS;

public class Student_finish extends Fragment {


    final String TAG = Student_finish.class.getSimpleName();
    FragmentStudentFinishBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentFinishBinding.inflate(inflater,container,false);
        Log.i(TAG, String.valueOf(new Date().getTime()));
        binding.submit.setOnClickListener(view -> {
            if (AddStudent.ACTION.equals(ADD)){
                validate();
            }else {
                updateValidate();
            }
        });
        return binding.getRoot();
    }



    public void updateValidate(){
        if (!addContainer.getModel().getFirstName().trim().matches("") &&
                !addContainer.getModel().getLastName().trim().matches("") &&
                !addContainer.getAddUserLogindata().getEmail().trim().matches("")){

            //Update User
            binding.finishProgressBar.setVisibility(View.VISIBLE);
            binding.submit.setVisibility(View.GONE);
            updateUser();

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
        }else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage("Email Required")
                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
        }
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
        String date = simpleDateFormat.format(new Date());
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
                                .setTitle("Error Failed")
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

    public void updateUser(){
        Observable<Response<StudentResponse>> observable = Observable.create(new ObservableOnSubscribe<Response<StudentResponse>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<StudentResponse>> emitter) throws Exception {
                AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
                Call<StudentResponse> call = academyApis.updateUser(AddStudent.userData.getId(),AddStudent.ROLE,addContainer.getModel().getFirstName(),
                        addContainer.getModel().getLastName(),addContainer.getModel().getBiography(),addContainer.getAddUserLogindata().getEmail(),
                        addContainer.getAddUserPaymentData().getPaypalClientId(),
                        addContainer.getAddUserPaymentData().getPaypalSecretId(),addContainer.getAddUserPaymentData().getStripeSecretKey(),
                        addContainer.getAddUserPaymentData().getStripePublicKey(),"",
                        addContainer.getAddUserSocialData().getFacebook(),addContainer.getAddUserSocialData().getTwitter(),
                        addContainer.getAddUserSocialData().getLinkedin());
                Log.i(TAG,call.request().url().toString());
                call.enqueue(new Callback<StudentResponse>() {
                    @Override
                    public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(Call<StudentResponse> call, Throwable t) {
                        emitter.onError(t);
                    }
                });

            }
        });

        observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<Response<StudentResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<StudentResponse> studentResponseResponse) {
                if (studentResponseResponse.isSuccessful()){
                    StudentResponse studentResponse = studentResponseResponse.body();
                    if (studentResponse.getCode().equals("200") && studentResponse.getStatus().equals(SUCCESS)){
                        Utils.showDialog(getContext(),studentResponse.getStatus(),studentResponse.getMessage());
                        binding.finishProgressBar.setVisibility(View.GONE);
                        binding.submit.setVisibility(View.VISIBLE);
                        makeEmpty();
                    }else {
                        binding.finishProgressBar.setVisibility(View.GONE);
                        binding.submit.setVisibility(View.VISIBLE);
                        Utils.showDialog(getContext(),studentResponse.getStatus(),studentResponse.getMessage());
                    }
                }else {
                    binding.finishProgressBar.setVisibility(View.GONE);
                    binding.submit.setVisibility(View.VISIBLE);
                    Utils.showDialog(getContext(),RESPONSE_FAILED,studentResponseResponse.message());
                }
            }

            @Override
            public void onError(Throwable e) {
                binding.finishProgressBar.setVisibility(View.GONE);
                binding.submit.setVisibility(View.VISIBLE);
                Utils.showDialog(getContext(),FAILED,e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}