package com.example.lms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.lms.Model.CourseData;
import com.example.lms.R;
import com.example.lms.databinding.ActivityUpdateCourseBinding;
import com.example.lms.ui.addcourses.BasicFragment;
import com.example.lms.ui.addcourses.FinishFragment;
import com.example.lms.ui.addcourses.MediaFragment;
import com.example.lms.ui.addcourses.OutcomesFragment;
import com.example.lms.ui.addcourses.PricingFragment;
import com.example.lms.ui.addcourses.RequirementsFragment;
import com.example.lms.ui.addcourses.SeoFragment;
import com.example.lms.ui.updateCourse.Curriculum;
import com.google.android.material.tabs.TabLayout;

import static com.example.lms.Model.Constants.DATA;

public class UpdateCourse extends AppCompatActivity {

    ActivityUpdateCourseBinding binding;
    Fragment fragment;
    Bundle bundle;
    public static CourseData courseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bundle = getIntent().getExtras();
        courseData = (CourseData) bundle.getSerializable(DATA);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.hostFragment,new Curriculum()).commit();
        }
        binding.nextBtn.setOnClickListener(v -> {
            int position=binding.tabAddCourses.getSelectedTabPosition();
            Log.i("position",String.valueOf(position));
            binding.tabAddCourses.getTabAt(++position).select();
        });
        binding.backBtn.setOnClickListener(v -> {
            int position=binding.tabAddCourses.getSelectedTabPosition();
            Log.i("position",String.valueOf(position));
            if (position>0){
                binding.tabAddCourses.getTabAt(--position).select();
            }

        });

        setTabLayout();

    }

    private void setTabLayout() {


        binding.tabAddCourses.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (binding.tabAddCourses.getSelectedTabPosition()){
                    case 0:
                        fragment=new Curriculum();
                        break;
                    case 1:
                        fragment=new BasicFragment();
                        break;
                    case 2:
                        fragment=new RequirementsFragment();
                        break;
                    case 3:
                        fragment=new OutcomesFragment();
                        break;
                    case 4:
                        fragment=new PricingFragment();
                        break;
                    case 5:
                        fragment=new MediaFragment();
                        break;
                    case 6:
                        fragment=new SeoFragment();
                        break;
                    case 7:
                        binding.frwBckContainer.setVisibility(View.GONE);
                        fragment=new FinishFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.hostFragment,fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                binding.frwBckContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}