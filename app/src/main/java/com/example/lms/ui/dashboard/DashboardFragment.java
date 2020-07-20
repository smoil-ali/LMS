package com.example.lms.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.Factories.CourseFactory;
import com.example.lms.Factories.DashboardFatory;
import com.example.lms.Model.DashboardCount;
import com.example.lms.R;
import com.example.lms.ViewModels.DashboardViewModel;
import com.example.lms.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    FragmentDashboardBinding binding;
    DashboardViewModel dashboardViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentDashboardBinding.inflate(inflater,container,false);
        dashboardViewModel=new ViewModelProvider(requireActivity(),new DashboardFatory(binding.dashboardProgressBar,getContext())).get(DashboardViewModel.class);


        dashboardViewModel.getDashboardCountMutableLiveData().observe(requireActivity(), dashboardCount -> {
            binding.totalCourses.setText(String.valueOf(dashboardCount.getCourse()));
            binding.totalEnrol.setText(String.valueOf(dashboardCount.getEnrollment()));
            binding.totalLesson.setText(String.valueOf(dashboardCount.getLesson()));
            binding.totalStudent.setText(String.valueOf(dashboardCount.getStudent()));
            binding.dashboardProgressBar.setVisibility(View.GONE);

        });
        return binding.getRoot();
    }
}
