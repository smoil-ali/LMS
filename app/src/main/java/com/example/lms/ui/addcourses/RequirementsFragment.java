package com.example.lms.ui.addcourses;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.lms.MainActivity;
import com.example.lms.Model.Constants;
import com.example.lms.Model.Container;
import com.example.lms.R;
import com.example.lms.activity.AddStudent;
import com.example.lms.databinding.FragmentRequirementsCourseBinding;
import com.example.lms.dialogs.AddLesson;
import com.example.lms.dialogs.AddSection;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.lms.activity.UpdateCourse.courseData;

public class RequirementsFragment extends Fragment {

    public final String TAG= RequirementsFragment.class.getSimpleName();
    FragmentRequirementsCourseBinding binding;
    List<String> listOfRequirements = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentRequirementsCourseBinding.inflate(inflater,container,false);
        if (!getActivity().getClass().getSimpleName().equals(Constants.AddCourse))
            setValues();
        binding.addbtn.setOnClickListener(v -> generateField("null"));

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, Container.getModel().getCourseTitle());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getAllChildElements(binding.container);
    }

    private void generateField(String text) {
        LinearLayout linearLayout=new LinearLayout(getContext());
        linearLayout.setWeightSum(2);
        TextInputEditText textInputEditText=new TextInputEditText(getContext());
        textInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        TextInputLayout textInputLayout=new TextInputLayout(getContext());
        textInputLayout.addView(textInputEditText);
        textInputLayout.setHint(getResources().getString(R.string.requirements));
        if (!text.equals("null"))
            textInputEditText.setText(text);
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

        imageButton.setOnClickListener(v -> linearLayout.removeAllViews());

        linearLayout1.addView(imageButton);
        linearLayout1.setGravity(Gravity.END);



        linearLayout.addView(textInputLayout);
        linearLayout.addView(linearLayout1);

        binding.container.addView(linearLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public final void getAllChildElements(ViewGroup layoutCont) {
        if (layoutCont == null) return;
        final int mCount = layoutCont.getChildCount();
        for (int i = 0; i < mCount; i++) {
           Log.i(TAG,i+" loop val");
            final ViewGroup mChild = (ViewGroup) layoutCont.getChildAt(i);
            final TextInputLayout textInputLayout= (TextInputLayout) mChild.getChildAt(0);
            final EditText editText=textInputLayout.getEditText();
            if (editText instanceof EditText) {
                Log.i(TAG,editText.getText().toString()+" val");
                if (!editText.getText().toString().trim().matches("")){
                    listOfRequirements.add(editText.getText().toString());
                }
            }
        }
        Container.setListOfRequirements(listOfRequirements);
    }


    public void setValues(){
        Log.i(TAG, courseData.getRequirements());
        String json = courseData.getRequirements();
        if (!json.equals("null")){
            List<String> listOfRequirements = Arrays.asList(new Gson().fromJson(json,String[].class));
            binding.requiremets.setText(listOfRequirements.get(0));
            for (int i=1;i<listOfRequirements.size();i++){
                generateField(listOfRequirements.get(i));
            }
        }
    }

}
