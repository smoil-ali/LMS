package com.example.lms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;

import com.example.lms.R;
import com.example.lms.databinding.ActivityAddCourseBinding;
import com.example.lms.ui.HomeFragment;
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
    int position = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.hostFragment,new BasicFragment()).commit();
        }else {
            position = savedInstanceState.getInt("pos");
            binding.tabAddCourses.getTabAt(position).select();
        }
        binding.nextBtn.setOnClickListener(v -> {
            int position=binding.tabAddCourses.getSelectedTabPosition();
            Log.i("position",String.valueOf(position));
            binding.tabAddCourses.getTabAt(++position).select();
        });
        binding.backBtn.setOnClickListener(v -> {
            int position=binding.tabAddCourses.getSelectedTabPosition();
            Log.i("position",String.valueOf(position));
            binding.tabAddCourses.getTabAt(--position).select();
        });

        setTabLayout();
    }



    private void setTabLayout() {


        binding.tabAddCourses.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (binding.tabAddCourses.getSelectedTabPosition()){
                    case 0:
                        position = 0;
                        fragment=new BasicFragment();
                        break;
                    case 1:
                        position = 1;
                        Bundle data = new Bundle();
                        data.putString("key","hello");
                        fragment=new RequirementsFragment();
                        fragment.setArguments(data);
                        break;
                    case 2:
                        position = 2;
                        fragment=new OutcomesFragment();
                        break;
                    case 3:
                        position = 3;
                        fragment=new PricingFragment();
                        break;
                    case 4:
                        position = 4;
                        fragment=new MediaFragment();
                        break;
                    case 5:
                        position = 5;
                        fragment=new SeoFragment();
                        break;
                    case 6:
                        position = 6;
                        binding.frwBckContainer.setVisibility(View.GONE);
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos",position);
    }
}