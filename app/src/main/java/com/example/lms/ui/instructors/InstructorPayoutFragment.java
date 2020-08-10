package com.example.lms.ui.instructors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.lms.MainActivity;
import com.example.lms.R;
import com.example.lms.databinding.ActivityMainBinding;
import com.example.lms.databinding.ContentMainBinding;
import com.example.lms.databinding.FragmentCoursesBinding;
import com.example.lms.databinding.FragmentInstructorPayoutBinding;
import com.example.lms.ui.dashboard.DashboardFragment;
import com.google.android.material.tabs.TabLayout;

public class InstructorPayoutFragment extends Fragment {

    TabLayout tabLayout;
    Fragment fragment;
    FragmentInstructorPayoutBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInstructorPayoutBinding.inflate(inflater,container,false);


        setTabLayout();
        return binding.getRoot();
    }
    private void setTabLayout(){

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tabLayout.getSelectedTabPosition()){
                    case 0:
                        Toast.makeText(getContext(), "first", Toast.LENGTH_SHORT).show();
                        fragment = new CompletePayoutFragment();
                        break;
                    case 1:
                        Toast.makeText(getContext(),"second",Toast.LENGTH_LONG).show();
                        fragment=new PendingPayoutFragment();
                        break;
                }
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host,fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
