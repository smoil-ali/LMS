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

import com.example.lms.Model.SocialLinks;
import com.example.lms.Model.addContainer;
import com.example.lms.Model.addUserSocialData;
import com.example.lms.activity.AddStudent;
import com.example.lms.databinding.FragmentStudentSocialInformationBinding;
import com.google.gson.Gson;

import static com.example.lms.Model.Constants.EDIT;


public class StudentSocialInformation extends Fragment {


    final String TAG = StudentSocialInformation.class.getSimpleName();
    FragmentStudentSocialInformationBinding binding;
    addUserSocialData model = new addUserSocialData();
    String fb,twitter,linkedin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStudentSocialInformationBinding.inflate(inflater,container,false);


        binding.facebook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                fb = editable.toString();
                model.setFacebook(editable.toString());
            }
        });
        binding.twitter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                twitter = editable.toString();
                model.setTwitter(editable.toString());
            }
        });
        binding.linkedin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                linkedin = editable.toString();
                model.setLinkedin(editable.toString());
            }
        });

        if (savedInstanceState != null){
            binding.facebook.setText(savedInstanceState.getString("fb"));
            binding.twitter.setText(savedInstanceState.getString("twitter"));
            binding.linkedin.setText(savedInstanceState.getString("linkedin"));
        }else {
            if (AddStudent.ACTION.equals(EDIT)){
                setValues();
            }
        }

        return binding.getRoot();
    }

    private void setValues() {
        String json = AddStudent.userData.getSocial_links();
        SocialLinks model = new Gson().fromJson(json,SocialLinks.class);
        binding.facebook.setText(model.getFacebook());
        binding.twitter.setText(model.getTwitter());
        binding.linkedin.setText(model.getLinkedin());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addContainer.setAddUserSocialData(model);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fb",fb);
        outState.putString("twitter",twitter);
        outState.putString("linkedin",linkedin);
    }
}