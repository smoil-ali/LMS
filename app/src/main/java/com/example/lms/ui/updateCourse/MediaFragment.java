package com.example.lms.ui.updateCourse;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.Model.Constants;
import com.example.lms.Model.Container;
import com.example.lms.Model.MediaFragmentModel;
import com.example.lms.databinding.FragmentMediaCourseBinding;

public class MediaFragment extends Fragment {

    final String TAG = MediaFragment.class.getSimpleName();
    FragmentMediaCourseBinding binding;
    MediaFragmentModel model = new MediaFragmentModel();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= FragmentMediaCourseBinding.inflate(inflater,container,false);
        binding.url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                model.setUrl(editable.toString());
            }
        });
        setupOverViewSpinner();
        binding.overviewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                model.setProvider(Constants.listOfProvider.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return binding.getRoot();
    }


    public void setupOverViewSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Constants.listOfProvider);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.overviewSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Container.setMediaFragmentModel(model);
    }
}
