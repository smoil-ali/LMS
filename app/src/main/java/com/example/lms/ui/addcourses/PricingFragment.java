package com.example.lms.ui.addcourses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.databinding.FragmentPricingCourseBinding;

public class PricingFragment extends Fragment {

    FragmentPricingCourseBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentPricingCourseBinding.inflate(inflater,container,false);
        binding.freeCourseCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    binding.container.setVisibility(View.GONE);
                }else{
                    binding.container.setVisibility(View.VISIBLE);
                }

            }
        });
        return binding.getRoot();
    }
}
