package com.example.lms.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.lms.Model.CategoryData;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.InstructorResponse;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.databinding.ActivityAddCategoryBinding;
import com.google.gson.internal.$Gson$Preconditions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategory extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityAddCategoryBinding binding;
    List<CategoryData> dataList = new ArrayList<>();
    List<String> categoryList = new ArrayList<>();
    final String TAG = AddCategory.class.getSimpleName();
    AcademyApis academyApis;
    final int n =20;
    public String code;
    public String Parent;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupSpinner();
        binding.categorySpinner.setOnItemSelectedListener(this);
        getCategoryList();
        binding.category.setEnabled(false);
        binding.category.setText(RequiredString(n));
        binding.submit.setOnClickListener(view -> validate());
    }

    public void validate(){
        if (!binding.category.getText().toString().trim().matches("") || !binding.categoryTitleText1.getText().toString().trim().matches("")){
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmm");
            String date = sdf1.format(new Date());
            Log.i(TAG,date);
            if (Parent.equals("None")){
                academyApis = RetrofitService.createService(AcademyApis.class);
                Call<CategoryResponse> categoryResponseCall = academyApis.addParentCategory(binding.categoryTitleText1.getText().toString(),
                        binding.categoryTitleText1.getText().toString().toLowerCase()
                        ,code,date);
                setCategory(categoryResponseCall);
            }else {
                academyApis = RetrofitService.createService(AcademyApis.class);
                Call<CategoryResponse> categoryResponseCall = academyApis.addSubCategory(binding.categoryTitleText1.getText().toString(),
                        binding.categoryTitleText1.getText().toString().toLowerCase()
                        ,code,date,dataList.get(index).getId());
                setCategory(categoryResponseCall);
            }

        }else {
            Toast.makeText(this, "Empty Fields!!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void setCategory(Call<CategoryResponse>  categoryResponseCall){
        binding.addCategoryProgressBar.setVisibility(View.VISIBLE);
        Log.i(TAG,categoryResponseCall.request().url()+"");
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse.getCode().equals("200")){
                        Toast.makeText(AddCategory.this, ""+categoryResponse.getStatus(), Toast.LENGTH_SHORT).show();
                        binding.addCategoryProgressBar.setVisibility(View.GONE);
                        binding.categoryTitleText1.setText("");
                        binding.category.setText(RequiredString(n));
                        new AlertDialog.Builder(AddCategory.this)
                                .setTitle(categoryResponse.getStatus())
                                .setMessage(categoryResponse.getMessage())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                    }else {
                        binding.addCategoryProgressBar.setVisibility(View.GONE);
                        Log.i(TAG,categoryResponse.getStatus());
                        new AlertDialog.Builder(AddCategory.this)
                                .setTitle(categoryResponse.getStatus())
                                .setMessage(categoryResponse.getMessage())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                    }
                }else {
                    binding.addCategoryProgressBar.setVisibility(View.GONE);
                    new AlertDialog.Builder(AddCategory.this)
                            .setTitle(response.code())
                            .setMessage(response.message())
                            .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                binding.addCategoryProgressBar.setVisibility(View.GONE);
                Log.i(TAG,t.getMessage());
                new AlertDialog.Builder(AddCategory.this)
                        .setTitle("Failed")
                        .setMessage(t.getMessage())
                        .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
            }
        });
    }
    public String RequiredString(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder s = new StringBuilder(n);
        int y;
        for ( y = 0; y < n; y++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            s.append(AlphaNumericString.charAt(index));
        }
        code = s.toString();
        return s.toString();
    }


    public void getCategoryList(){
        binding.addCategoryProgressBar.setVisibility(View.VISIBLE);
        AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CategoryResponse> categoryResponseCall = academyApis.getCategories();
        Log.i(TAG,categoryResponseCall.request().url()+"");
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse.getCode().equals("200")){
                        dataList.addAll(categoryResponse.getData());
                        categoryList.add("None");
                        for (CategoryData categoryData:dataList){
                            categoryList.add(categoryData.getName());
                        }
                        setupSpinner();
                    }else {
                        binding.addCategoryProgressBar.setVisibility(View.GONE);
                        Log.i(TAG,categoryResponse.getMessage());
                    }
                }else {
                    binding.addCategoryProgressBar.setVisibility(View.GONE);
                    Log.i(TAG,"in response unSuccessful");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                binding.addCategoryProgressBar.setVisibility(View.GONE);
                Log.i(TAG,"in response failed");
            }

        });
    }


    public void setupSpinner(){
        binding.addCategoryProgressBar.setVisibility(View.GONE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categoryList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Parent = categoryList.get(i);
        index = i-1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}