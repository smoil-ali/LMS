package com.example.lms.ui.addcourses;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.Model.BasicFragmentModel;
import com.example.lms.Model.CategoryData;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.Constants;
import com.example.lms.Model.Container;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.AddCourse;
import com.example.lms.activity.UpdateCourse;
import com.example.lms.databinding.ActivityAddCourseBinding;
import com.example.lms.databinding.FragmentBasicBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasicFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public final String TAG = BasicFragment.class.getSimpleName();
    FragmentBasicBinding binding;
    ActivityAddCourseBinding addbinding;
    List<CategoryData> dataList;
    List<String> categoryList = new ArrayList<>();
    BasicFragmentModel model = new BasicFragmentModel();
    int categoryPos,languagePos,levelPos;
    boolean checkTopCourse;
    String courseTitle,shortDescription,Description;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding=FragmentBasicBinding.inflate(inflater,container,false);
        binding.categorySpinner.setOnItemSelectedListener(this);

        binding.languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                model.setLanguage(Constants.listOfLanguages.get(i));
                languagePos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                model.setLevel(Constants.listOfLevel.get(i));
                levelPos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.courseTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG,charSequence.toString()+" Before Text");

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG,charSequence.toString()+" TEXT Change");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString()+" AFter");
                courseTitle = editable.toString();
                model.setCourseTitle(editable.toString());
            }
        });

        binding.shortDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                shortDescription = editable.toString();
                model.setShortDescription(editable.toString());
            }
        });

        binding.description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Description = editable.toString();
                model.setDescription(editable.toString());
            }
        });

        binding.checkTopCourse.setOnCheckedChangeListener((compoundButton, b) -> {
            Log.i(TAG,b+" checkbox");
            model.setIsTopCourse(String.valueOf(b));
             checkTopCourse = b;
        });

        if (savedInstanceState == null){
            setValues();
            setupLevelSpinner();
            setupLanguageSpinner();
            getCategoryList();
        }
        return binding.getRoot();
    }

    public void setupSpinner(){
        binding.BasicProgressBar.setVisibility(View.GONE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,categoryList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(dataAdapter);
    }

    public void setupLevelSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Constants.listOfLevel);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.levelSpinner.setAdapter(dataAdapter);
    }

    public void setupLanguageSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Constants.listOfLanguages);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.languageSpinner.setAdapter(dataAdapter);
    }

    public void getCategoryList(){
        binding.BasicProgressBar.setVisibility(View.VISIBLE);
        AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CategoryResponse> categoryResponseCall = academyApis.getCategories();
        Log.i(TAG,categoryResponseCall.request().url()+"");
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse.getCode().equals("200")){
                        dataList=new ArrayList<>();
                        dataList.addAll(categoryResponse.getData());
                        Log.i(TAG,dataList.size()+" data list size");
                        for (CategoryData categoryData:dataList){
                            categoryList.add(categoryData.getName());
                        }
                        setupSpinner();
                    }else {
                        binding.BasicProgressBar.setVisibility(View.GONE);
                        Log.i(TAG,categoryResponse.getMessage());
                    }
                }else {
                    binding.BasicProgressBar.setVisibility(View.GONE);
                    Log.i(TAG,"in response unSuccessful");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                binding.BasicProgressBar.setVisibility(View.GONE);
                Log.i(TAG,"in response failed");
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        model.setCategory_id(dataList.get(i).getId());
        categoryPos = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setValues(){
        binding.courseTitle.setText(UpdateCourse.courseData.getTitle());
        binding.shortDescription.setText(UpdateCourse.courseData.getShort_description());
        binding.description.setText(UpdateCourse.courseData.getDescription());
        binding.checkTopCourse.setSelected(Boolean.parseBoolean(UpdateCourse.courseData.getIs_top_course()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Container.setModel(model);
    }


}
