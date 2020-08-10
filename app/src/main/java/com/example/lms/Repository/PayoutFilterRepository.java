package com.example.lms.Repository;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import com.example.lms.Listener.EnrollListener;
import com.example.lms.Listener.PayoutListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.PayoutResponse;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayoutFilterRepository {
    final String TAG = PayoutFilterRepository.class.getSimpleName();
    AcademyApis academyApis ;
    PayoutListener listener;

    public void setListener(PayoutListener listener) {
        this.listener = listener;
    }

    public PayoutFilterRepository(Context context, ProgressBar progressBar, String from, String to) {
        progressBar.setVisibility(View.VISIBLE);
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<PayoutResponse> enrollmentHistoryResponseCall = academyApis.getPaymentHistory(from,to);
        Log.i(TAG,enrollmentHistoryResponseCall.request().url()+"");
        enrollmentHistoryResponseCall.enqueue(new Callback<PayoutResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<PayoutResponse> call, Response<PayoutResponse> response) {
                listener.payoutDataListner(response);
            }

            @Override
            public void onFailure(Call<PayoutResponse> call, Throwable t) {
                Log.i(TAG,"in response failed");
                Utils.showDialog(context, Constants.FAILED,t.getMessage());
            }
        });
    }
}
