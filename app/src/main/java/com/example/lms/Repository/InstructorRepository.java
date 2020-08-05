package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.InstructorListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.InstructorResponse;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstructorRepository {
    String TAG = InstructorRepository.class.getSimpleName();
    InstructorListener listener;
    AcademyApis academyApis ;

    public InstructorRepository(Context context, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<InstructorResponse> InstructorResponseCall = academyApis.getInstructorList("instructor");
        Log.i(TAG,InstructorResponseCall.request().url()+"");
        InstructorResponseCall.enqueue(new Callback<InstructorResponse>() {
            @Override
            public void onResponse(Call<InstructorResponse> call, Response<InstructorResponse> response) {
                listener.onSuccess(response);
            }
            @Override
            public void onFailure(Call<InstructorResponse> call, Throwable t) {
                Log.i(TAG,"in response failed");
                Utils.showDialog(context, Constants.FAILED,t.getMessage());
            }
        });
    }

    public void setListener(InstructorListener listener){
        this.listener = listener;
    }
}
