package com.example.lms;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lms.Model.addContainer;
import com.example.lms.Model.addUserPaymentData;
import com.example.lms.databinding.FragmentStudentPaymentInfoBinding;


public class Student_Payment_info extends Fragment {



    final String TAG = Student_Payment_info.class.getSimpleName();
    FragmentStudentPaymentInfoBinding binding;
    addUserPaymentData model = new addUserPaymentData();
    String PaypalClientId,PaypalSecretId,StripePublicKey,StripeSecretKey;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStudentPaymentInfoBinding.inflate(inflater,container,false);

        if (savedInstanceState != null){
            binding.paypalClientId.setText(savedInstanceState.getString("pcId"));
            binding.payPalSecretId.setText(savedInstanceState.getString("psId"));
            binding.StripePublicKey.setText(savedInstanceState.getString("spk"));
            binding.stripeSecretKey.setText(savedInstanceState.getString("ssk"));
        }

        binding.paypalClientId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                PaypalClientId = editable.toString();
                model.setPaypalClientId(editable.toString());
            }
        });
        binding.payPalSecretId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                PaypalSecretId = editable.toString();
                model.setPaypalSecretId(editable.toString());
            }
        });

        binding.StripePublicKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                StripePublicKey = editable.toString();
                model.setStripePublicKey(editable.toString());
            }
        });
        binding.stripeSecretKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                StripeSecretKey = editable.toString();
                model.setStripeSecretKey(editable.toString());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addContainer.setAddUserPaymentData(model);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("pcId",PaypalClientId);
        outState.putString("psId",PaypalSecretId);
        outState.putString("spk",StripePublicKey);
        outState.putString("ssk",StripeSecretKey);
    }
}