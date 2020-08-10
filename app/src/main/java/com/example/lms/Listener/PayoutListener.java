package com.example.lms.Listener;


import com.example.lms.Model.PayoutResponse;

import retrofit2.Response;

public interface PayoutListener {
    public void payoutDataListner(Response<PayoutResponse> response);

}
