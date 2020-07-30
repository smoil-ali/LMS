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

import com.example.lms.Model.AddUserLogindata;
import com.example.lms.Model.addContainer;
import com.example.lms.databinding.FragmentLoginCredentialsBinding;
import com.example.lms.databinding.FragmentStudentsBinding;


public class StudentLoginCredentials extends Fragment {

    final String TAG = StudentLoginCredentials.class.getSimpleName();
    FragmentLoginCredentialsBinding binding;
    AddUserLogindata model = new AddUserLogindata();
    String email,password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginCredentialsBinding.inflate(inflater,container,false);

        if (savedInstanceState != null){
            binding.email.setText(savedInstanceState.getString("email"));
            binding.password.setText(savedInstanceState.getString("password"));
        }

        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                email= editable.toString();
                model.setEmail(editable.toString());
            }
        });

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                password = editable.toString();
                model.setPassword(editable.toString());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addContainer.setAddUserLogindata(model);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email",email);
        outState.putString("password",password);
    }
}