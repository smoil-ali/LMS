package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.CategoryListener;
import com.example.lms.Listener.EnrollListener;
import com.example.lms.Listener.sub_categoryListener;
import com.example.lms.Model.CategoryData;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.Category_subCategoryModel;
import com.example.lms.Model.Constants;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.Section_LessonModel;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.RESPONSE_FAILED;

public class CategoryRepository implements sub_categoryListener {
    String TAG = CategoryRepository.class.getSimpleName();
    CategoryListener categoryListener;
    AcademyApis academyApis ;
    sub_categoryRepository repository;
    List<Category_subCategoryModel> list;
    int categorySize = 0;

    public CategoryRepository(Context context,ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        repository = new sub_categoryRepository(context);
        repository.setSub_categoryListener(this);
        list = new ArrayList<>();
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CategoryResponse> categoryResponseCall = academyApis.getCategories();
        Log.i(TAG,categoryResponseCall.request().url()+"");
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    categorySize = response.body().getData().size();
                    for (CategoryData categoryData:response.body().getData()){
                        repository.getSubCategory(categoryData);
                    }
                }else {
                    Utils.showDialog(context,RESPONSE_FAILED+" In Category",response.message());
                }
            }
            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Utils.showDialog(context, Constants.FAILED+" In Category",t.getMessage());
            }

        });
    }
    public void setCategoryListener(CategoryListener categoryListener){
        this.categoryListener = categoryListener;
    }

    @Override
    public void onSuccess(List<CategoryData> sub_categories, CategoryData categoryData) {
        Category_subCategoryModel model = new Category_subCategoryModel();
        model.setCategoryData(categoryData);
        model.setList(sub_categories);
        list.add(model);
        if (categorySize == list.size()){
            categoryListener.onCategoryList(list);
        }
    }
}
