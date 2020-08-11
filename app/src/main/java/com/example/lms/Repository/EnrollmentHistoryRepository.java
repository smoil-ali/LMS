package com.example.lms.Repository;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.lms.Listener.CourseListener;
import com.example.lms.Listener.EnrollListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.NetworkState;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import java.util.ArrayList;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollmentHistoryRepository {
    String TAG = EnrollmentHistoryRepository.class.getSimpleName();
    AcademyApis academyApis ;
    EnrollListener enrollListener;


    public EnrollmentHistoryRepository(Context context, ProgressBar progressBar,String from,String to) {
        progressBar.setVisibility(View.VISIBLE);
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<EnrollmentHistoryResponse> enrollmentHistoryResponseCall = academyApis.getEnrollHistoryByDateRange(from,to);
        Log.i(TAG,enrollmentHistoryResponseCall.request().url()+"");
        enrollmentHistoryResponseCall.enqueue(new Callback<EnrollmentHistoryResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<EnrollmentHistoryResponse> call, Response<EnrollmentHistoryResponse> response) {
                enrollListener.enrollListener(response);
            }

            @Override
            public void onFailure(Call<EnrollmentHistoryResponse> call, Throwable t) {
                Log.i(TAG,"in response failed");
                Utils.showDialog(context, Constants.FAILED,t.getMessage());
            }
        });
    }


    public void setEnrollListener(EnrollListener enrollListener){
        this.enrollListener = enrollListener;
    }
}
