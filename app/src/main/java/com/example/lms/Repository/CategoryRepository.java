package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.CategoryListener;
import com.example.lms.Listener.EnrollListener;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.Constants;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    String TAG = CategoryRepository.class.getSimpleName();
    CategoryListener categoryListener;
    AcademyApis academyApis ;

    public CategoryRepository(Context context,ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CategoryResponse> categoryResponseCall = academyApis.getCategories();
        Log.i(TAG,categoryResponseCall.request().url()+"");
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                categoryListener.onCategoryList(response);
            }
            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Utils.showDialog(context, Constants.FAILED,t.getMessage());
            }

        });
    }
    public void setCategoryListener(CategoryListener categoryListener){
        this.categoryListener = categoryListener;
    }
}
