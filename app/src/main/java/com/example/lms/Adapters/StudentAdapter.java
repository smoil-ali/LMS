package com.example.lms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.CategoryData;
import com.example.lms.Model.StudentData;
import com.example.lms.databinding.StudentItemBinding;
import com.example.lms.databinding.SubcategoryItemBinding;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<StudentData> dataList;
    final String TAG = StudentAdapter.class.getSimpleName();

    public StudentAdapter(Context context, List<StudentData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        StudentItemBinding binding = StudentItemBinding.inflate(layoutInflater,parent,false);
        return new StudentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG,dataList.size()+"");
        ((StudentViewHolder)holder).bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        StudentItemBinding binding;
        public StudentViewHolder(StudentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(StudentData studentData){
            binding.studentName.setText(studentData.getFirst_name());
            binding.studentEmail.setText(studentData.getEmail());
            binding.studentStatus.setText(studentData.getStatus());
        }

    }
}
