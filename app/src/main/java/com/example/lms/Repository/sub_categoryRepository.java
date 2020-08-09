package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.lms.Listener.sub_categoryListener;
import com.example.lms.Model.CategoryData;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.LessonResponse;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.FAILED;
import static com.example.lms.Model.Constants.RESPONSE_FAILED;
import static com.example.lms.Model.Constants.SUCCESS;

public class sub_categoryRepository {
    final String TAG = sub_categoryRepository.class.getSimpleName();
    Context context;
    sub_categoryListener sub_categoryListener;

    public void setSub_categoryListener(com.example.lms.Listener.sub_categoryListener sub_categoryListener) {
        this.sub_categoryListener = sub_categoryListener;
    }

    public sub_categoryRepository(Context context) {
        this.context = context;
    }

    public void getSubCategory(CategoryData categoryData){
        AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CategoryResponse> categoryResponseCall = academyApis.getSubCategories(categoryData.getId());
        Log.i(TAG,categoryResponseCall.request().url()+"");
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse.getCode().equals("200") && categoryResponse.getStatus().equals(SUCCESS)){
                        sub_categoryListener.onSuccess(categoryResponse.getData(),categoryData);
                    }else {
                        sub_categoryListener.onSuccess(new ArrayList<>(),categoryData);
                    }
                }else {
                    Utils.showDialog(context,RESPONSE_FAILED+" In Lesson",response.message());
                    Log.i(TAG,response.message());
                }
            }
            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Utils.showDialog(context,FAILED+" In Lesson",t.getMessage());
                Log.i(TAG,t.getMessage());
            }
        });
    }
}
