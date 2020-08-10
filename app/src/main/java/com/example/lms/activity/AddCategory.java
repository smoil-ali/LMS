package com.example.lms.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.lms.MainActivity;
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

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategory extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public final String EDIT = "Edit";
    public final String ADD = "Add";
    ActivityAddCategoryBinding binding;
    List<List<CategoryData>> MainList = new ArrayList<>();
    List<CategoryData> dataList;
    List<String> categoryList = new ArrayList<>();
    final String TAG = AddCategory.class.getSimpleName();
    AcademyApis academyApis;
    final int n =20;
    public String code;
    public String Parent="None";
    int index=-1;
    int pageCount = 0;
    Bundle bundle;
    int count =0 ;
    boolean dispose = false;
    CategoryData categoryData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bundle=getIntent().getExtras();
        Log.i(TAG,categoryData+"");
        binding.categorySpinner.setOnItemSelectedListener(this);
        if (bundle.getString("tag").equals(EDIT)){
            categoryData = (CategoryData) bundle.getSerializable("Data");
            binding.category.setEnabled(false);
            binding.category.setText(categoryData.getCode());
            binding.categoryTitleText1.setText(categoryData.getName());
            getCategoryList();
            binding.submit.setOnClickListener(view -> GoUpdate());
        }else {
            getCategoryList();
            binding.category.setEnabled(false);
            binding.category.setText(RequiredString(n));
            binding.submit.setOnClickListener(view -> validate());
        }
    }

    public void GoUpdate(){
        if (!binding.categoryTitleText1.getText().toString().trim().matches("")){
            if (Parent.equals("None") && index<0){
                academyApis = RetrofitService.createService(AcademyApis.class);
                Call<CategoryResponse> categoryResponseCall = academyApis.updateCategory(binding.categoryTitleText1.getText().toString(),
                        categoryData.getParent(),binding.categoryTitleText1.getText().toString().toLowerCase()
                        ,categoryData.getId());
                updateCategory(categoryResponseCall);
            }else {
                if (Parent.equals("None")){
                    academyApis = RetrofitService.createService(AcademyApis.class);
                    Log.i(TAG,index+" "+MainList.get(1).size());
                    Call<CategoryResponse> categoryResponseCall = academyApis.updateCategory(binding.categoryTitleText1.getText().toString()
                            ,IsEqual(MainList.get(pageCount-1).get(index).getId(),categoryData.getId()),
                            binding.categoryTitleText1.getText().toString().toLowerCase(),
                            categoryData.getId());
                    updateCategory(categoryResponseCall);
                }else {
                    academyApis = RetrofitService.createService(AcademyApis.class);
                    Call<CategoryResponse> categoryResponseCall = academyApis.updateCategory(binding.categoryTitleText1.getText().toString()
                            ,IsEqual(MainList.get(pageCount).get(index).getId(),categoryData.getId()),
                            binding.categoryTitleText1.getText().toString().toLowerCase(),
                            categoryData.getId());
                    updateCategory(categoryResponseCall);
                }

            }



        }else {
            binding.categoryTitleText1.setError("Empty Fields!!!");
        }
    }

    public String IsEqual(String parentId,String Id){
        if (!parentId.equals(Id)){
            return parentId;
        }
        return "0";
    }

    public void validate(){
        if (!binding.category.getText().toString().trim().matches("") && !binding.categoryTitleText1.getText().toString().trim().matches("")){
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmm");
            String date = sdf1.format(new Date());
            Log.i(TAG,date);
            if (Parent.equals("None") && index<0){
                academyApis = RetrofitService.createService(AcademyApis.class);
                Call<CategoryResponse> categoryResponseCall = academyApis.addParentCategory(binding.categoryTitleText1.getText().toString(),
                        binding.categoryTitleText1.getText().toString().toLowerCase()
                        ,code,date);
                setCategory(categoryResponseCall);
            }else {
                if (Parent.equals("None")){
                    academyApis = RetrofitService.createService(AcademyApis.class);
                    Log.i(TAG,index+" "+MainList.get(1).size());
                    Call<CategoryResponse> categoryResponseCall = academyApis.addSubCategory(binding.categoryTitleText1.getText().toString(),
                            binding.categoryTitleText1.getText().toString().toLowerCase()
                            ,code,date,MainList.get(pageCount-1).get(index).getId());

                    setCategory(categoryResponseCall);
                }else {
                    academyApis = RetrofitService.createService(AcademyApis.class);
                    Call<CategoryResponse> categoryResponseCall = academyApis.addSubCategory(binding.categoryTitleText1.getText().toString(),
                            binding.categoryTitleText1.getText().toString().toLowerCase()
                            ,code,date,MainList.get(pageCount).get(index).getId());
                    setCategory(categoryResponseCall);
                }

            }

        }else {
            binding.categoryTitleText1.setError("Empty Field!!!");
        }
    }

    public void updateCategory(Call<CategoryResponse>  categoryResponseCall){
        binding.addCategoryProgressBar.setVisibility(View.VISIBLE);
        Log.i(TAG,categoryResponseCall.request().url()+"");
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse.getCode().equals("200")){
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

    public void setCategory(Call<CategoryResponse>  categoryResponseCall){
        binding.addCategoryProgressBar.setVisibility(View.VISIBLE);
        Log.i(TAG,categoryResponseCall.request().url()+"");
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse.getCode().equals("200")){
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
                        dataList=new ArrayList<>();
                        dataList.addAll(categoryResponse.getData());
                        Log.i(TAG,dataList.size()+" data list size");
                        categoryList.add("None");
                        MainList.add(dataList);
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

    public void getCategoryListById(String id){
        binding.addCategoryProgressBar.setVisibility(View.VISIBLE);
        AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CategoryResponse> categoryResponseCall = academyApis.getSubCategories(id);
        Log.i(TAG,categoryResponseCall.request().url()+"");
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse.getCode().equals("200")){
                        categoryList.clear();
                        dataList=new ArrayList<>();
                        dataList.addAll(categoryResponse.getData());
                        categoryList.add("None");
                        MainList.add(dataList);
                        for (CategoryData categoryData:dataList){
                            categoryList.add(categoryData.getName());
                        }
                        pageCount++;
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Parent = categoryList.get(i);
        if (!Parent.equals("None")){
            index = i-1;
            getCategoryListById(dataList.get(index).getId());
        }else {
            if (pageCount == 0){
                index =-1;
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddCategory.this, MainActivity.class));
        finishAffinity();
    }
}