package com.example.lms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.CourseData;
import com.example.lms.ViewModels.CourseViewModel;
import com.example.lms.activity.Login;
import com.example.lms.databinding.CoursesItemBinding;

import java.util.ArrayList;

public class CourserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<CourseData> courseDataArrayList ;
    String TAG = CourserAdapter.class.getSimpleName();

    public CourserAdapter(Context context, ArrayList<CourseData> courseDataArrayList) {
        this.context = context;
        this.courseDataArrayList = courseDataArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CoursesItemBinding binding = CoursesItemBinding.inflate(layoutInflater,parent,false);
        return new CourseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG,courseDataArrayList.size()+"");
        ((CourseViewHolder)holder).bindView(courseDataArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return courseDataArrayList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder{
        CoursesItemBinding binding;
        public CourseViewHolder(CoursesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(CourseData courseData){
            binding.courseCategory.setText((courseData.getCategory() != null)?courseData.getCategory().getName():"Null");
            binding.totalSection.setText(String.valueOf((courseData.getSection() != null)?courseData.getSection().getCount():"Null"));
            binding.courseStatus.setText(courseData.getStatus());
            binding.courseTitle.setText(courseData.getTitle());
            binding.totalEnrol.setText(String.valueOf((courseData.getEnrollment() != null)?courseData.getEnrollment().getCount():"Null"));
            binding.price.setText("$"+courseData.getPrice());
            binding.courseInstructor.setText("Instructor : "+((courseData.getInstructor() != null)?courseData.getInstructor().getFirst_name():"Null"));
            binding.totalLesson.setText(String.valueOf((courseData.getLesson() != null)?courseData.getLesson().getCount():"Null"));

        }
    }
}
