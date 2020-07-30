package com.example.lms.ui.courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lms.Adapters.CourserAdapter;
import com.example.lms.Factories.CourseFactory;
import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseData;
import com.example.lms.R;
import com.example.lms.ViewModels.CourseViewModel;
import com.example.lms.activity.Login;
import com.example.lms.databinding.FragmentCoursesBinding;

import java.util.ArrayList;

public class CoursesFragment extends Fragment {

    FragmentCoursesBinding binding;
    CourserAdapter courserAdapter;
    ArrayList<CourseData> courseDataArrayList=new ArrayList<>();
    CourseViewModel courseViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCoursesBinding.inflate(inflater,container,false);
        setUpRecyclerView();
        courseViewModel = new ViewModelProvider(getActivity(),new CourseFactory(getContext(),binding.courseProgressBar)).get(CourseViewModel.class);
        courseViewModel.getArrayListMutableLiveData().observe(requireActivity(), courseData -> {
            if (courseData.size() > 0 ){
                courseDataArrayList.clear();
                courseDataArrayList.addAll(courseData);
                courserAdapter.notifyDataSetChanged();
                binding.courseProgressBar.setVisibility(View.GONE);
            }else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Data Not Found")
                        .setMessage("data not found")
                        .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
            }

        });

        courseViewModel.errorMessage.observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.courseProgressBar.setVisibility(View.GONE);
                new AlertDialog.Builder(getContext())
                        .setTitle("Data Not Found")
                        .setMessage(s)
                        .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
            }
        });

        courseViewModel.getCourseCountMutableLiveData().observe(requireActivity(), courseCount -> {
            binding.countActiveCourses.setText(courseCount.getActive());
            binding.countFreeCourses.setText(courseCount.getFree());
            binding.countPaidCourses.setText(courseCount.getPaid());
            binding.countPendingCourses.setText(courseCount.getPending());
        });
        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        binding.rvCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvCourses.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.rvCourses.addItemDecoration(dividerItemDecoration);
     //   binding.rvCourses.setNestedScrollingEnabled(false);
        courserAdapter=new CourserAdapter(getContext(),courseDataArrayList);
        binding.rvCourses.setAdapter(courserAdapter);
    }
}
