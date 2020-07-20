package com.example.lms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.CourseData;
import com.example.lms.Model.EnrollHistoryUserData;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.databinding.CoursesItemBinding;
import com.example.lms.databinding.EnrollHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class EnrollHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<EnrollHistoryUserData> enrollmentHistoryData;
    public static final String TAG = EnrollHistoryAdapter.class.getSimpleName();

    public EnrollHistoryAdapter(Context context, List<EnrollHistoryUserData> enrollmentHistoryData) {
        this.context = context;
        this.enrollmentHistoryData = enrollmentHistoryData;
        System.out.println(enrollmentHistoryData);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        EnrollHistoryBinding binding = EnrollHistoryBinding.inflate(layoutInflater,parent,false);
        return new EnrollHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG,enrollmentHistoryData.size()+"");
        ((EnrollHistoryViewHolder)holder).bindView(enrollmentHistoryData.get(position));
    }

    @Override
    public int getItemCount() {
        return enrollmentHistoryData.size();
    }

    public class EnrollHistoryViewHolder extends RecyclerView.ViewHolder{
        EnrollHistoryBinding binding;
        public EnrollHistoryViewHolder(EnrollHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(EnrollHistoryUserData enrollmentHistoryData){
            binding.setEnrollDate.setText(enrollmentHistoryData.getDate_added());
            binding.studentEmail.setText("Email here");
            binding.studentName.setText(enrollmentHistoryData.getUserName());
            binding.enrolCourse.setText(enrollmentHistoryData.getCourseName());
        }
    }
}
