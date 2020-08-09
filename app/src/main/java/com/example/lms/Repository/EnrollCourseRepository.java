package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.lms.Listener.EnrollmentCourseListener;
import com.example.lms.Listener.LessonListener;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.LessonResponse;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.UserData;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.FAILED;
import static com.example.lms.Model.Constants.RESPONSE_FAILED;
import static com.example.lms.Model.Constants.SUCCESS;

public class EnrollCourseRepository {
    final String TAG = EnrollCourseRepository.class.getSimpleName();
    Context context;
    EnrollmentCourseListener listener;


    public void setListener(EnrollmentCourseListener listener) {
        this.listener = listener;
    }

    public EnrollCourseRepository(Context context) {
        this.context = context;
    }

    public void getEnrollCourse(UserData userData){
        AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
        Call<EnrollmentHistoryResponse> ResponseCall = academyApis.getEnrollmentHistoryById(userData.getId());
        Log.i(TAG,ResponseCall.request().url()+"");
        ResponseCall.enqueue(new Callback<EnrollmentHistoryResponse>() {
            @Override
            public void onResponse(Call<EnrollmentHistoryResponse> call, Response<EnrollmentHistoryResponse> response) {
                if (response.isSuccessful()){
                    EnrollmentHistoryResponse EnrollmentHistoryResponse = response.body();
                    if (EnrollmentHistoryResponse.getCode().equals("200") && EnrollmentHistoryResponse.getStatus().equals(SUCCESS)){
                        listener.onSuccess(EnrollmentHistoryResponse.getData(),userData);
                    }else {
                        Log.i(TAG,EnrollmentHistoryResponse.getStatus());
                        listener.onSuccess(new ArrayList<>(),userData);
                    }
                }else {
                    Utils.showDialog(context,RESPONSE_FAILED+" Course Requirements",response.message());
                    Log.i(TAG,response.message());
                }

            }

            @Override
            public void onFailure(Call<EnrollmentHistoryResponse> call, Throwable t) {
                Utils.showDialog(context,FAILED+" Course Requirements",t.getMessage());
                Log.i(TAG,t.getMessage());
            }
        });
    }
}
