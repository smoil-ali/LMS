package com.example.lms.ViewModels;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.PayoutListener;
import com.example.lms.Model.PayoutResponse;
import com.example.lms.Repository.InstructorPayoutRepository;

import retrofit2.Response;

public class PayoutViewModel extends ViewModel implements PayoutListener {
    public MutableLiveData<Response<PayoutResponse>> payoutDataMutabeLiveData;
    InstructorPayoutRepository instructorPayoutRepository;
    String TAG=PayoutViewModel.class.getSimpleName();

    public PayoutViewModel(Context context, ProgressBar progressBar){
        instructorPayoutRepository=new InstructorPayoutRepository(context,progressBar);
        instructorPayoutRepository.setPayoutListener(this);
        payoutDataMutabeLiveData=new MutableLiveData<>();

    }

    public void update(Context context,ProgressBar progressBar){
        instructorPayoutRepository=new InstructorPayoutRepository(context,progressBar);
        instructorPayoutRepository.setPayoutListener(this);
    }

    public MutableLiveData<Response<PayoutResponse>> getPayoutDataMutabeLiveData() {
        return payoutDataMutabeLiveData;
    }

    @Override
    public void payoutDataListner(Response<PayoutResponse> response) {
        payoutDataMutabeLiveData.setValue(response);
    }
}
