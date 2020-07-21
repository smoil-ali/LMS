package com.example.lms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.Instructor;
import com.example.lms.Model.InstructorData;
import com.example.lms.Model.StudentData;
import com.example.lms.databinding.InstructorItemBinding;
import com.example.lms.databinding.StudentItemBinding;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<InstructorData> dataList;
    final String TAG = InstructorAdapter.class.getSimpleName();

    public InstructorAdapter(Context context, List<InstructorData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        InstructorItemBinding binding = InstructorItemBinding.inflate(layoutInflater,parent,false);
        return new InstructorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG,dataList.size()+"");
        ((InstructorViewHolder)holder).bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class InstructorViewHolder extends RecyclerView.ViewHolder {
        InstructorItemBinding binding;
        public InstructorViewHolder(InstructorItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(InstructorData instructorData){
            binding.studentName.setText(instructorData.getFirst_name());
            binding.instEmail.setText(instructorData.getEmail());
            binding.instActiveCourses.setText(instructorData.getStatus());
        }

    }
}
