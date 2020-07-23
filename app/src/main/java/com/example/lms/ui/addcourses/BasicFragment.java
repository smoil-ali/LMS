package com.example.lms.ui.addcourses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.R;
import com.example.lms.activity.AddCourse;
import com.example.lms.databinding.ActivityAddCourseBinding;
import com.example.lms.databinding.FragmentBasicBinding;
import com.google.android.material.tabs.TabLayout;

public class BasicFragment extends Fragment {

    FragmentBasicBinding binding;
    ActivityAddCourseBinding addbinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding=FragmentBasicBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }
}
