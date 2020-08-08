package com.example.lms.ui.addcourses;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.Model.Constants;
import com.example.lms.Model.Container;
import com.example.lms.Model.PriceFragmentModel;
import com.example.lms.activity.UpdateCourse;
import com.example.lms.databinding.FragmentPricingCourseBinding;

import java.time.temporal.TemporalAccessor;

public class PricingFragment extends Fragment {

    public final String TAG = PriceFragmentModel.class.getSimpleName();
    FragmentPricingCourseBinding binding;
    PriceFragmentModel model = new PriceFragmentModel();
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
                model.setIfFreeCourse(String.valueOf(isChecked));
            }
        });

        binding.discountCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                model.setIfDiscount(String.valueOf(b));
            }
        });

        binding.coursePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                model.setCoursePrice(editable.toString());
            }
        });

        binding.discountPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,editable.toString());
                model.setDiscountPrice(editable.toString());
            }
        });

        setValues();

        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Container.setPriceFragmentModel(model);
    }

    public void setValues(){
        binding.freeCourseCheck.setSelected(Boolean.parseBoolean(UpdateCourse.courseData.getIs_free_course()));
        binding.coursePrice.setText(UpdateCourse.courseData.getPrice());
        binding.discountCheck.setSelected(Boolean.parseBoolean(UpdateCourse.courseData.getDiscount_flag()));
        binding.discountPrice.setText(UpdateCourse.courseData.getDiscounted_price());

    }
}
