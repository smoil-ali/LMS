package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.CourseListener;
import com.example.lms.Listener.DashboardListner;
import com.example.lms.Model.DashboardResponse;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepository {
    Context context;
    String TAG=DashboardRepository.class.getSimpleName();
    AcademyApis academyApis;
    DashboardListner dashboardListner;

    public void getDashboardCount(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        academyApis= RetrofitService.createService(AcademyApis.class);
        Call<DashboardResponse> dashboardResponseCall=academyApis.getDashBoardResponse();
        Log.i(TAG,dashboardResponseCall.request().url()+"");
        dashboardResponseCall.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                if (response.isSuccessful()){
                    DashboardResponse dashboardResponse=response.body();
                    if (dashboardResponse.getCode().equalsIgnoreCase("200")){
                        dashboardListner.dashboardCountListner(dashboardResponse.getCount());
                    }else{
                        dashboardListner.dashboardCountError(dashboardResponse.getMessage());
                    }
                }else{
                    dashboardListner.dashboardCountError(response.message());
                }
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                dashboardListner.dashboardCountError(t.getMessage());
            }
        });
    }

    public void setDashboardListner(DashboardListner dashboardListner){
        this.dashboardListner=dashboardListner;
    }
}
