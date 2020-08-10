package com.example.lms.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.lms.Model.BasicFragmentModel;
import com.example.lms.Model.Container;
import com.example.lms.Model.CourseData;
import com.example.lms.Model.MediaFragmentModel;
import com.example.lms.Model.PriceFragmentModel;
import com.example.lms.Model.SeoModelClass;
import com.example.lms.Model.SocialLinks;
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
import com.google.gson.Gson;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.lms.Model.Constants.COURSE_ID;
import static com.example.lms.Model.Constants.DATA;
import static java.util.stream.Collectors.toList;

public class UpdateCourse extends AppCompatActivity {

    private static final String TAG = UpdateCourse.class.getSimpleName();
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
        COURSE_ID = courseData.getId();
        setData();

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

    public void setData(){
        setBasicData();
        setRequirements();
        setOutcomes();
        setPrice();
        setMedia();
        setSeo();

    }

    public void setBasicData(){
        BasicFragmentModel model = new BasicFragmentModel();
        model.setCategory_id(courseData.getCategory_id());
        model.setLevel(courseData.getLevel());
        model.setLanguage(courseData.getLanguage());
        model.setIsTopCourse(courseData.getIs_top_course());
        model.setDescription(courseData.getDescription());
        model.setShortDescription(courseData.getShort_description());
        model.setCourseTitle(courseData.getTitle());
        Container.setModel(model);
    }

    public void setRequirements(){
        Log.i(TAG,courseData.getRequirements());
        String json = courseData.getRequirements();
        if (!json.equals("null")){
            List<String> listOfRequirements = Arrays.asList(new Gson().fromJson(json,String[].class));
            String json1 = new Gson().toJson(listOfRequirements);
            Log.i(TAG,json1);
            Container.setListOfRequirements(json1);
        }

    }

    public void setOutcomes(){
        Log.i(TAG,courseData.getOutcomes());
        String json = courseData.getOutcomes();
        if (!json.equals("null") && !json.equals("\"[]\"")){
            List<String> listOfRequirements = Arrays.asList(new Gson().fromJson(json,String[].class));
            Log.i(TAG,listOfRequirements.size()+" outcomes");
            String json1 = new Gson().toJson(listOfRequirements);
            Log.i(TAG,json1);
            Container.setListOfOutcomes(json1);
        }

    }

    public void setPrice(){
        PriceFragmentModel model = new PriceFragmentModel();
        model.setDiscountPrice(courseData.getDiscounted_price());
        model.setCoursePrice(courseData.getPrice());
        model.setIfDiscount(courseData.getDiscount_flag());
        model.setIfFreeCourse(courseData.getIs_free_course());
        Container.setPriceFragmentModel(model);
    }

    public void setMedia(){
        MediaFragmentModel model = new MediaFragmentModel();
        model.setProvider(courseData.getCourse_overview_provider());
        model.setUrl(courseData.getVideo_url());
        model.setThumbnail(courseData.getThumbnail());
        Container.setMediaFragmentModel(model);
    }

    public void setSeo(){
        SeoModelClass model = new SeoModelClass();
        model.setMetaDiscription(courseData.getMeta_description());
        String json = courseData.getMeta_keywords();
        if (json.equals("null")){
            List<String> listOfRequirements = new Gson().fromJson(json,ArrayList.class);
            String json1 = new Gson().toJson(listOfRequirements);
            Log.i(TAG,json1);
            model.setMeta_list(json1);
            Container.setSeoModelClass(model);
        }

    }

}