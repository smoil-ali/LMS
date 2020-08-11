package com.example.lms.ui.instructors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.R;
import com.google.android.material.tabs.TabLayout;

public class InstructorApplicationFragment extends Fragment {
    TabLayout tabLayout;
    Fragment fragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_instructor_application,container,false);
        tabLayout=root.findViewById(R.id.tabs);
        setTabLayout();
        return root;
    }
    private void setTabLayout(){
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.host,new PendingApplicationFragment()).commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tabLayout.getSelectedTabPosition()){
                    case 0:
                        fragment = new PendingApplicationFragment();
                        break;
                    case 1:
                        fragment=new ApprovedApplicationFragment();
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
