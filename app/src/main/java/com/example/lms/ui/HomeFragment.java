package com.example.lms.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.MainActivity;
import com.example.lms.R;
import com.example.lms.databinding.ActivityMainBinding;
import com.example.lms.databinding.FragmentHomeBinding;
import com.example.lms.ui.categories.CategoriesFragment;
import com.example.lms.ui.courses.CoursesFragment;
import com.example.lms.ui.dashboard.DashboardFragment;
import com.example.lms.ui.enrolment.EnrolHistoryFragment;
import com.example.lms.ui.instructors.InstructorListFragment;
import com.example.lms.ui.students.StudentFragment;

public class HomeFragment extends Fragment {

   FragmentHomeBinding binding;
   ActivityMainBinding mainBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding=FragmentHomeBinding.inflate(inflater, container, false);
       init();
       return binding.getRoot();
    }
    public void init(){
        binding.dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectFragment(new DashboardFragment());
            }
        });

        binding.categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectFragment(new CategoriesFragment());
            }
        });

        binding.students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectFragment(new StudentFragment());
            }
        });

        binding.instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectFragment(new InstructorListFragment());
            }
        });

        binding.courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectFragment(new CoursesFragment());
            }
        });

        binding.enrolHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectFragment(new EnrolHistoryFragment());
            }
        });
    }

    public void redirectFragment(Fragment fragment){
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.hostFragment,fragment).commit();
    }

}
