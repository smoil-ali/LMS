package com.example.lms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.lms.R;
import com.example.lms.databinding.ActivityAddCourseBinding;
import com.example.lms.ui.addcourses.BasicFragment;
import com.example.lms.ui.addcourses.FinishFragment;
import com.example.lms.ui.addcourses.MediaFragment;
import com.example.lms.ui.addcourses.OutcomesFragment;
import com.example.lms.ui.addcourses.PricingFragment;
import com.example.lms.ui.addcourses.RequirementsFragment;
import com.example.lms.ui.addcourses.SeoFragment;
import com.google.android.material.tabs.TabLayout;

public class AddCourse extends AppCompatActivity {
    ActivityAddCourseBinding binding;
    Fragment fragment;

    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //tabLayout=findViewById(R.id.tabAddCourses);
        setTabLayout();
    }



    private void setTabLayout() {
        binding.tabAddCourses.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (binding.tabAddCourses.getSelectedTabPosition()){
                    case 0:
                        fragment=new BasicFragment();
                        break;
                    case 1:
                        Bundle data = new Bundle();
                        data.putString("key","hello");
                        fragment=new RequirementsFragment();
                        fragment.setArguments(data);
                        break;
                    case 2:
                        fragment=new OutcomesFragment();
                        break;
                    case 3:
                        fragment=new PricingFragment();
                        break;
                    case 4:
                        fragment=new MediaFragment();
                        break;
                    case 5:
                        fragment=new SeoFragment();
                        break;
                    case 6:
                        fragment=new FinishFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.hostFragment,fragment).commit();
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