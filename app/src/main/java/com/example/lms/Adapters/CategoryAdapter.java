package com.example.lms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.CategoryData;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.databinding.CategoriesItemBinding;
import com.example.lms.databinding.EnrollHistoryBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<CategoryData> categoryData;
    public final String TAG = CategoryAdapter.class.getSimpleName();

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
        Log.i(TAG,categoryData.size()+"");
        ((CategoryViewHolder)holder).bindView(categoryData.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }
    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        CategoriesItemBinding binding;
        public CategoryViewHolder(CategoriesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(CategoryData categoryData){
            binding.categoryName.setText(categoryData.getName());
        }
    }
}
