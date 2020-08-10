package com.example.lms.Repository;


import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.ApplicationListener;
import com.example.lms.Model.ApplicationResponse;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovedApplicationRepository {
    String TAG=ApprovedApplicationRepository.class.getSimpleName();
    ApplicationListener applicationListener;
    AcademyApis academyApis;

    public ApprovedApplicationRepository(ProgressBar progressBar , int status){
        progressBar.setVisibility(View.VISIBLE);
        academyApis= RetrofitService.createService(AcademyApis.class);
        Call<ApplicationResponse> applicationResponseCall;
        if (status==1)
        applicationResponseCall=academyApis.getApprovedApplication();
        else
        applicationResponseCall=academyApis.getPendingApplication();

        applicationResponseCall.enqueue(new Callback<ApplicationResponse>() {
            @Override
            public void onResponse(Call<ApplicationResponse> call, Response<ApplicationResponse> response) {
                if (response.isSuccessful()){
                    ApplicationResponse applicationResponse=response.body();
                    if (applicationResponse.getCode().equals("200")){
                        applicationListener.applicationListner(applicationResponse.getData(),applicationResponse.getStatus());

                    }else{
                        applicationListener.applicationError(applicationResponse.getMessage());
                    }

                }else{
                    applicationListener.applicationError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ApplicationResponse> call, Throwable t) {
                applicationListener.applicationError(t.getMessage());
            }
        });
    }

    public void setApplicationListener(ApplicationListener applicationListener){
        this.applicationListener=applicationListener;
    }
}
