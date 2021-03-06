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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.CategoryData;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.Category_subCategoryModel;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.AddCategory;
import com.example.lms.databinding.CategoriesItemBinding;
import com.example.lms.databinding.EnrollHistoryBinding;
import com.example.lms.dialogs.DeleteDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements deleteListener {
    Context context;
    List<Category_subCategoryModel> categoryData;
    public final String TAG = CategoryAdapter.class.getSimpleName();
    subCategoryAdapter adapter;
    deleteListener deleteListener;
    DeleteDialog deleteDialog = new DeleteDialog();
    FragmentManager fragmentManager;

    public CategoryAdapter(Context context, List<Category_subCategoryModel> categoryData,FragmentManager fragmentManager) {
        this.context = context;
        this.categoryData = categoryData;
        this.fragmentManager = fragmentManager;

    }

    public void setDeleteListener(deleteListener deleteListener){
        this.deleteListener = deleteListener;
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
        ((CategoryViewHolder)holder).bindView(categoryData.get(position));
    }

    @Override
    public int getItemCount() {
        Log.i(TAG,categoryData.size()+" ");
        return categoryData.size();
    }

    @Override
    public void OnDelete(String status, String message) {
        deleteListener.OnDelete(status,message);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        CategoriesItemBinding binding;
        public CategoryViewHolder(CategoriesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(Category_subCategoryModel categoryData){
            Log.i(TAG,categoryData.getCategoryData().getName());
            binding.categoryName.setText(categoryData.getCategoryData().getName());

            setUpRecyclerView(categoryData.getList());

            binding.deleteBtn.setOnClickListener(view -> {
                new AlertDialog.Builder(context)
                        .setTitle("Alert!!!")
                        .setMessage("Are You Sure?")
                        .setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            Utils.openDialog(fragmentManager,deleteDialog);
                            DeleteCategory(categoryData.getCategoryData().getId());
                        }).show();
            });

            binding.editBtn.setOnClickListener((View.OnClickListener) view -> {
                Intent intent = new Intent(context,AddCategory.class);
                intent.putExtra("Data", (Serializable) categoryData.getCategoryData());
                intent.putExtra("tag","Edit");
                context.startActivity(intent);
            });
        }

        private void setUpRecyclerView(List<CategoryData> subCategoryList){
            binding.rvSubcategory.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvSubcategory.getContext(),
                    DividerItemDecoration.HORIZONTAL);
            binding.rvSubcategory.addItemDecoration(dividerItemDecoration);
            adapter=new subCategoryAdapter(context,subCategoryList,fragmentManager,CategoryAdapter.this);
            binding.rvSubcategory.setAdapter(adapter);
        }


        private void DeleteCategory(String id){
            AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
            Call<CategoryResponse> categoryResponseCall = academyApis.deleteCategory(id);
            Log.i(TAG,categoryResponseCall.request().url()+"");
            categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                    if (response.isSuccessful()){
                        CategoryResponse categoryResponse = response.body();
                        if (categoryResponse.getCode().equals("200") && !categoryResponse.getStatus().equals("FAILED")){
                            deleteDialog.dismiss();
                            deleteListener.OnDelete(categoryResponse.getStatus(),categoryResponse.getMessage());
                        }else {
                            deleteDialog.dismiss();
                            new AlertDialog.Builder(context)
                                    .setTitle(categoryResponse.getStatus())
                                    .setMessage(categoryResponse.getMessage())
                                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                            Log.i(TAG,categoryResponse.getStatus()+" delete");
                        }
                    }else {
                        deleteDialog.dismiss();
                        new AlertDialog.Builder(context)
                                .setTitle("Failed")
                                .setMessage(response.message())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                        Log.i(TAG,response.message()+" delete");
                    }
                }

                @Override
                public void onFailure(Call<CategoryResponse> call, Throwable t) {
                    deleteDialog.dismiss();
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
