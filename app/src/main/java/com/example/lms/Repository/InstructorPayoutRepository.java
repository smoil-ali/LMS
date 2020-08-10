package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.PayoutListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.PayoutResponse;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstructorPayoutRepository {
    AcademyApis academyApis;
    PayoutListener payoutListener;

    public InstructorPayoutRepository(Context context, ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
        academyApis= RetrofitService.createService(AcademyApis.class);
        Call<PayoutResponse> payoutResponseCall=academyApis.getPayout("0");
        payoutResponseCall.enqueue(new Callback<PayoutResponse>() {
            @Override
            public void onResponse(Call<PayoutResponse> call, Response<PayoutResponse> response) {
                Log.i("response data",response.body().getCode().toString()+"");
                payoutListener.payoutDataListner(response);
            }

            @Override
            public void onFailure(Call<PayoutResponse> call, Throwable t) {
                Utils.showDialog(context, Constants.FAILED,t.getMessage());
            }
        });
    }

    public void setPayoutListener(PayoutListener payoutListener)
    {
        this.payoutListener=payoutListener;
    }
}
