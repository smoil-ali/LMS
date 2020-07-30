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

import com.example.lms.Model.AddBasicUserModel;
import com.example.lms.Model.addContainer;
import com.example.lms.databinding.FragmentStudentBasicInfoBinding;
import com.example.lms.ui.addcourses.FinishFragment;


public class Student_Basic_Info extends Fragment {



    final String TAG = Student_Basic_Info.class.getSimpleName();
    FragmentStudentBasicInfoBinding binding;
    AddBasicUserModel model= new AddBasicUserModel();
    String FirstName,LastName,Bio;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStudentBasicInfoBinding.inflate(inflater,container,false);

        if (savedInstanceState != null){
            binding.firstName.setText(savedInstanceState.getString("firstName"));
            binding.lastName.setText(savedInstanceState.getString("lastName"));
            binding.biography.setText(savedInstanceState.getString("bio"));
        }

        binding.firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                FirstName = editable.toString();
                model.setFirstName(editable.toString());
            }
        });

        binding.lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                LastName = editable.toString();
                model.setLastName(editable.toString());
            }
        });

        binding.biography.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                Bio = editable.toString();
                model.setBiography(editable.toString());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addContainer.setModel(model);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("lastName",LastName);
        outState.putString("firstName",FirstName);
        outState.putString("bio",Bio);
    }
}