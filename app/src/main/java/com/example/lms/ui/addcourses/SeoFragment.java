package com.example.lms.ui.addcourses;

import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.Model.Container;
import com.example.lms.Model.SeoModelClass;
import com.example.lms.R;
import com.example.lms.databinding.FragmentSeoCourseBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.lms.activity.UpdateCourse.courseData;

public class SeoFragment extends Fragment {

    FragmentSeoCourseBinding binding;
    final String TAG=SeoFragment.class.getSimpleName();
    List<String> meta_keyWords = new ArrayList<>();
    SeoModelClass model = new SeoModelClass();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSeoCourseBinding.inflate(inflater,container,false);

        binding.metaKeywords.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode==KeyEvent.KEYCODE_ENTER)){
                Log.i(TAG,"press enter btn");
                if (!binding.metaKeywords.getText().toString().matches("")){
                    addChip("null");
                }
                return true;
            }
            return false;

        });

        binding.seoDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                model.setMetaDiscription(editable.toString());
            }
        });

        setValues();
        return binding.getRoot();
    }


    private void addChip(String text){
        Chip chip=new Chip(requireContext());
        ChipDrawable chipDrawable = ChipDrawable.createFromResource(requireContext(), R.xml.chip_item);
        if (!text.equals("null")){
            chipDrawable.setText(text.trim());
        }else {
            chipDrawable.setText(binding.metaKeywords.getText().toString().trim());
        }
        meta_keyWords.add(binding.metaKeywords.getText().toString());
        binding.metaKeywords.getText().clear();
        chip.setChipDrawable(chipDrawable);
        binding.chipgroup.addView(chip);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.chipgroup.removeView(v);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        model.setMeta_list(meta_keyWords);
        Container.setSeoModelClass(model);
    }

    public void setValues(){
        binding.metaDescription.setText(courseData.getMeta_description());
        Log.i(TAG, courseData.getMeta_keywords());
        String json = courseData.getMeta_keywords();
        if (!json.equals("null")){
            List<String> listOfRequirements = Arrays.asList(new Gson().fromJson(json,String[].class));
            for (int i=0;i<listOfRequirements.size();i++){
                addChip(listOfRequirements.get(i));
            }
        }
    }
}
