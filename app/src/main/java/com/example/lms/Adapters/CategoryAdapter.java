package com.example.lms.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.CategoryData;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.AddCategory;
import com.example.lms.databinding.CategoriesItemBinding;
import com.example.lms.databinding.EnrollHistoryBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<CategoryData> categoryData;
    List<CategoryData> subCategoryList = new ArrayList<>();
    public final String TAG = CategoryAdapter.class.getSimpleName();
    subCategoryAdapter adapter;

    public CategoryAdapter(Context context, List<CategoryData> categoryData) {
        this.context = context;
        this.categoryData = categoryData;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CategoriesItemBinding binding = CategoriesItemBinding.inflate(layoutInflater,parent,false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG,categoryData.size()+" view holder");
        ((CategoryViewHolder)holder).bindView(categoryData.get(position),position);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG,categoryData.size()+" ");
        return categoryData.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        CategoriesItemBinding binding;
        public CategoryViewHolder(CategoriesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(CategoryData categoryData,int Position){
            Log.i(TAG,categoryData.getName());
            binding.categoryName.setText(categoryData.getName());
            binding.categoriesProgressBar.setVisibility(View.VISIBLE);
            getSubCategoryById(categoryData.getId());
            binding.deleteBtn.setOnClickListener(view -> new AlertDialog.Builder(context)
                    .setTitle("Alert!!!")
                    .setMessage("Are You Sure?")
                    .setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                    .setPositiveButton("OK", (dialogInterface, i) -> DeleteCategory(categoryData.getId(),Position)).show());

            binding.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,AddCategory.class);
                    intent.putExtra("Data", (Serializable) categoryData);
                    intent.putExtra("tag","Edit");
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            });
        }

        private void setUpRecyclerView(){
            binding.rvSubcategory.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvSubcategory.getContext(),
                    DividerItemDecoration.HORIZONTAL);
            binding.rvSubcategory.addItemDecoration(dividerItemDecoration);
            adapter=new subCategoryAdapter(context,subCategoryList);
            binding.rvSubcategory.setAdapter(adapter);
        }

        private void getSubCategoryById(String id){
            AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
            Call<CategoryResponse> categoryResponseCall = academyApis.getSubCategories(id);
            Log.i(TAG,categoryResponseCall.request().url()+"");
            categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                    if (response.isSuccessful()){
                        CategoryResponse categoryResponse = response.body();
                        if (categoryResponse.getCode().equals("200")){
                            Log.i(TAG,categoryResponse.getStatus());
                            subCategoryList.clear();
                            subCategoryList.addAll(categoryResponse.getData());
                            Log.i(TAG,"list size "+subCategoryList.size());
                            binding.categoriesProgressBar.setVisibility(View.GONE);
                            setUpRecyclerView();
                        }else {
                            binding.categoriesProgressBar.setVisibility(View.GONE);
                            Log.i(TAG,categoryResponse.getStatus());
                        }
                    }else {
                        binding.categoriesProgressBar.setVisibility(View.GONE);
                        Log.i(TAG,response.message());
                    }

                }

                @Override
                public void onFailure(Call<CategoryResponse> call, Throwable t) {
                    binding.categoriesProgressBar.setVisibility(View.GONE);
                    Log.i(TAG,t.getMessage());
                }
            });

        }

        private void DeleteCategory(String id,int position){
            binding.categoriesProgressBar.setVisibility(View.VISIBLE);
            AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
            Call<CategoryResponse> categoryResponseCall = academyApis.deleteCategory(id);
            Log.i(TAG,categoryResponseCall.request().url()+"");
            categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                    if (response.isSuccessful()){
                        CategoryResponse categoryResponse = response.body();
                        if (categoryResponse.getCode().equals("200")){
                            binding.categoriesProgressBar.setVisibility(View.GONE);
                            new AlertDialog.Builder(context)
                                    .setTitle(categoryResponse.getStatus())
                                    .setMessage(categoryResponse.getMessage())
                                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                            categoryData.remove(position);
                            notifyItemChanged(position);
                        }else {
                            binding.categoriesProgressBar.setVisibility(View.GONE);
                            new AlertDialog.Builder(context)
                                    .setTitle(categoryResponse.getStatus())
                                    .setMessage(categoryResponse.getMessage())
                                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                            Log.i(TAG,categoryResponse.getStatus()+" delete");
                        }
                    }else {
                        binding.categoriesProgressBar.setVisibility(View.GONE);
                        new AlertDialog.Builder(context)
                                .setTitle("Failed")
                                .setMessage(response.message())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                        Log.i(TAG,response.message()+" delete");
                    }
                }

                @Override
                public void onFailure(Call<CategoryResponse> call, Throwable t) {
                    binding.categoriesProgressBar.setVisibility(View.GONE);
                    new AlertDialog.Builder(context)
                            .setTitle("Failed")
                            .setMessage(t.getMessage())
                            .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                    Log.i(TAG,t.getMessage()+" delete");
                }
            });
        }
    }
}
