package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.InstructorListener;
import com.example.lms.Listener.SectionListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.SectionResponse;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionRepository {
    String TAG = SectionRepository.class.getSimpleName();
    SectionListener listener;
    AcademyApis academyApis ;


    public SectionRepository(Context context, ProgressBar progressBar,String id) {
        progressBar.setVisibility(View.VISIBLE);
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<SectionResponse> SectionResponseCall = academyApis.getSectionData(id);
        Log.i(TAG,SectionResponseCall.request().url()+"");
        SectionResponseCall.enqueue(new Callback<SectionResponse>() {
            @Override
            public void onResponse(Call<SectionResponse> call, Response<SectionResponse> response) {
                listener.onSuccess(response);
            }
            @Override
            public void onFailure(Call<SectionResponse> call, Throwable t) {
                Log.i(TAG,"in response failed");
                Utils.showDialog(context, Constants.FAILED,t.getMessage());
            }
        });
    }

    public void setListener(SectionListener listener){
        this.listener = listener;
    }

}
