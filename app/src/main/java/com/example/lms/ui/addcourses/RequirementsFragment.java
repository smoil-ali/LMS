package com.example.lms.ui.addcourses;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.lms.MainActivity;
import com.example.lms.R;
import com.example.lms.databinding.FragmentRequirementsCourseBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class RequirementsFragment extends Fragment {

    FragmentRequirementsCourseBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentRequirementsCourseBinding.inflate(inflater,container,false);

        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateField();
            }
        });

        return binding.getRoot();
    }

    private void generateField() {

        LinearLayout linearLayout=new LinearLayout(getContext());
        linearLayout.setWeightSum(2);

        TextInputEditText textInputEditText=new TextInputEditText(getContext());
        textInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        TextInputLayout textInputLayout=new TextInputLayout(getContext());
        textInputLayout.addView(textInputEditText);
        textInputLayout.setHint(getResources().getString(R.string.requirements));
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,(float)1.5);
        textInputLayout.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,(float)0.5);

        LinearLayout linearLayout1=new LinearLayout(getContext());
        linearLayout1.setLayoutParams(layoutParams1);


        ImageButton imageButton=new ImageButton(getContext());
        imageButton.setImageResource(R.drawable.remove);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (60 * scale + 0.5f);
        int elevation=(int)(5 * scale+0.5f);

        ViewGroup.LayoutParams layoutParams2=new ViewGroup.LayoutParams(pixels,pixels);
        imageButton.setLayoutParams(layoutParams2);
        imageButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.colorRed));
        imageButton.setElevation(elevation);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeAllViews();
            }
        });

        linearLayout1.addView(imageButton);
        linearLayout1.setGravity(Gravity.END);



        linearLayout.addView(textInputLayout);
        linearLayout.addView(linearLayout1);

        binding.container.addView(linearLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }


}
