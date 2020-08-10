package com.example.lms.ViewModels;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.PayoutListener;
import com.example.lms.Model.PayoutResponse;
import com.example.lms.Repository.InstructorPayoutRepository;
import com.example.lms.Repository.PayoutFilterRepository;

import retrofit2.Response;

public class PayoutFilterViewModel extends ViewModel implements PayoutListener {
    public MutableLiveData<Response<PayoutResponse>> payoutDataMutabeLiveData;
    PayoutFilterRepository repository;
    String TAG=PayoutViewModel.class.getSimpleName();

    public PayoutFilterViewModel(Context context, ProgressBar progressBar,String from,String to) {
        repository= new PayoutFilterRepository(context,progressBar,from,to);
        repository.setListener(this);
        payoutDataMutabeLiveData = new MutableLiveData<>();

    }

    public void update(Context context,ProgressBar progressBar,String from,String to){
        repository= new PayoutFilterRepository(context,progressBar,from,to);
        repository.setListener(this);
    }

    public MutableLiveData<Response<PayoutResponse>> getPayoutDataMutabeLiveData() {
        return payoutDataMutabeLiveData;
    }

    @Override
    public void payoutDataListner(Response<PayoutResponse> response) {
        payoutDataMutabeLiveData.setValue(response);
    }
}
