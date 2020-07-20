package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.CategoryListener;
import com.example.lms.Listener.EnrollListener;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    Context context;
    String TAG = CategoryRepository.class.getSimpleName();
    CategoryListener categoryListener;
    AcademyApis academyApis ;

    public CategoryRepository(Context context,ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        this.context = context;
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CategoryResponse> categoryResponseCall = academyApis.getCategories();
        Log.i(TAG,categoryResponseCall.request().url()+"");
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse.getCode().equals("200")){
                        categoryListener.onCategoryList(categoryResponse.getData());
                    }else {
                        categoryListener.onError(categoryResponse.getMessage());
                    }
                }else {
                    Log.i(TAG,"in response unSuccessful");
                    categoryListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.i(TAG,"in response failed");
                categoryListener.onError(t.getMessage());
            }

        });
    }



    public void setCategoryListener(CategoryListener categoryListener){
        this.categoryListener = categoryListener;
    }
}
