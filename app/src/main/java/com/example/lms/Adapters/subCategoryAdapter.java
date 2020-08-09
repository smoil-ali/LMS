package com.example.lms.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
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
import com.example.lms.Model.Utils;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.AddCategory;
import com.example.lms.databinding.CategoriesItemBinding;
import com.example.lms.databinding.SubcategoryItemBinding;
import com.example.lms.dialogs.DeleteDialog;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.SUCCESS;

public class subCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = subCategoryAdapter.class.getSimpleName();
    Context context;
    List<CategoryData> dataList ;
    DeleteDialog deleteDialog = new DeleteDialog();
    com.example.lms.Listener.deleteListener deleteListener;
    FragmentManager fragmentManager;

    public subCategoryAdapter(Context context, List<CategoryData> dataList,FragmentManager fragmentManager,deleteListener deleteListener) {
        this.context = context;
        this.dataList = dataList;
        this.fragmentManager = fragmentManager;
        this.deleteListener = deleteListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SubcategoryItemBinding binding = SubcategoryItemBinding.inflate(layoutInflater,parent,false);
        return new SubCategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG,dataList.size()+"");
        ((SubCategoryViewHolder)holder).bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        SubcategoryItemBinding binding;
        public SubCategoryViewHolder(SubcategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(CategoryData categoryData){
            Log.i(TAG,"cat Name "+categoryData.getName());
            binding.subcategory.setText(categoryData.getName());
            binding.subcategory.setOnLongClickListener((View.OnLongClickListener) view -> {
                PopupMenu popupMenu  = new PopupMenu(context,binding.subcategory);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) menuItem -> {
                    switch (menuItem.getItemId()){
                        case R.id.edit:
                            Intent intent = new Intent(context, AddCategory.class);
                            intent.putExtra("Data", (Serializable) categoryData);
                            intent.putExtra("tag","Edit");
                            context.startActivity(intent);
                            ((Activity)context).finish();
                            break;
                        case R.id.delete:
                            new AlertDialog.Builder(context)
                                    .setTitle("Alert!!!")
                                    .setMessage("Are You Sure?")
                                    .setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                                    .setPositiveButton("OK", (dialogInterface, i) -> {
                                        Utils.openDialog(fragmentManager,deleteDialog);
                                        DeleteCategory(categoryData.getId());
                                    }).show();
                            break;
                    }
                    return false;
                });
                popupMenu.show();
                return true;
            });
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
                        if (categoryResponse.getCode().equals("200") && categoryResponse.getStatus().equals(SUCCESS)){
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
