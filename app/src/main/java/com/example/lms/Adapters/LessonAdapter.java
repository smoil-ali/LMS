package com.example.lms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.databinding.LessonItemBinding;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LessonItemBinding binding = LessonItemBinding.inflate(layoutInflater,parent,false);
        return new LessonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder {
        LessonItemBinding binding;
        public LessonViewHolder(LessonItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(){

        }
    }
}
