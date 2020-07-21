package com.example.lms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.CategoryData;
import com.example.lms.databinding.CategoriesItemBinding;
import com.example.lms.databinding.SubcategoryItemBinding;

import java.util.List;

public class subCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = subCategoryAdapter.class.getSimpleName();
    Context context;
    List<CategoryData> dataList ;

    public subCategoryAdapter(Context context, List<CategoryData> dataList) {
        this.context = context;
        this.dataList = dataList;
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
        }

    }
}
