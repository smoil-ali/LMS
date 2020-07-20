package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.CourseListener;
import com.example.lms.Listener.EnrollListener;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollmentHistoryRepository {
    Context context;
    String TAG = EnrollmentHistoryRepository.class.getSimpleName();
    EnrollListener enrollListener;
    AcademyApis academyApis ;

    public EnrollmentHistoryRepository(Context context, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        this.context = context;
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<EnrollmentHistoryResponse> enrollmentHistoryResponseCall = academyApis.getEnrollmentHistory();
        Log.i(TAG,enrollmentHistoryResponseCall.request().url()+"");
        enrollmentHistoryResponseCall.enqueue(new Callback<EnrollmentHistoryResponse>() {
            @Override
            public void onResponse(Call<EnrollmentHistoryResponse> call, Response<EnrollmentHistoryResponse> response) {
                if (response.isSuccessful()){
                    EnrollmentHistoryResponse enrollmentHistoryResponse = response.body();
                    if (enrollmentHistoryResponse.getCode().equals("200")){
                        enrollListener.enrollListener(enrollmentHistoryResponse.getData(),"successful");
                    }else {
                        enrollListener.errorListener(enrollmentHistoryResponse.getMessage());
                    }
                }else {
                    Log.i(TAG,"in response unSuccessful");
                    enrollListener.errorListener(response.message());
                }
            }

            @Override
            public void onFailure(Call<EnrollmentHistoryResponse> call, Throwable t) {
                Log.i(TAG,"in response failed");
                enrollListener.errorListener(t.getMessage());
            }
        });
    }

    public void setEnrollListener(EnrollListener enrollListener){
        this.enrollListener = enrollListener;
    }
}
