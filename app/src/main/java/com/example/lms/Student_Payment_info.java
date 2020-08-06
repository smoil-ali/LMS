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
import com.example.lms.activity.AddStudent;
import com.example.lms.databinding.FragmentStudentPaymentInfoBinding;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import static com.example.lms.Model.Constants.EDIT;


public class Student_Payment_info extends Fragment {



    final String TAG = Student_Payment_info.class.getSimpleName();
    FragmentStudentPaymentInfoBinding binding;
    addUserPaymentData model = new addUserPaymentData();
    String PaypalClientId,PaypalSecretId,StripePublicKey,StripeSecretKey;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStudentPaymentInfoBinding.inflate(inflater,container,false);


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

        if (savedInstanceState != null){
            binding.paypalClientId.setText(savedInstanceState.getString("pcId"));
            binding.payPalSecretId.setText(savedInstanceState.getString("psId"));
            binding.StripePublicKey.setText(savedInstanceState.getString("spk"));
            binding.stripeSecretKey.setText(savedInstanceState.getString("ssk"));
        }else {
            if (AddStudent.ACTION.equals(EDIT)){
                setValues();
            }
        }

        return binding.getRoot();
    }

    private void setValues() {
        String json = AddStudent.userData.getPaypal_keys();
        String json2 = AddStudent.userData.getStripe_keys();
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONArray jsonArray1 = new JSONArray(json2);

            for (int i=0;i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                binding.payPalSecretId.setText(binding.payPalSecretId.getText().toString()+" "+jsonObject.getString("production_secret_key"));
                binding.paypalClientId.setText(binding.paypalClientId.getText().toString()+" "+jsonObject.getString("production_client_id"));
            }

            for (int i=0;i<jsonArray1.length();i++){
                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                binding.stripeSecretKey.setText(binding.stripeSecretKey.getText().toString()+" "+jsonObject.getString("secret_live_key"));
                binding.StripePublicKey.setText(binding.StripePublicKey.getText().toString()+" "+jsonObject.getString("public_live_key"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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