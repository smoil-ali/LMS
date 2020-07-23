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

import com.example.lms.R;
import com.example.lms.databinding.FragmentSeoCourseBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;

public class SeoFragment extends Fragment {

    FragmentSeoCourseBinding binding;
    final String TAG=SeoFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSeoCourseBinding.inflate(inflater,container,false);

        binding.metaKeywords.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode==KeyEvent.KEYCODE_ENTER)){
                    Log.i(TAG,"press enter btn");
                    addChip();
                    return true;
                }
                return false;

            }
        });

        return binding.getRoot();
    }


    private void addChip(){
        Chip chip=new Chip(requireContext());
        ChipDrawable chipDrawable = ChipDrawable.createFromResource(requireContext(), R.xml.chip_item);
        chipDrawable.setText(binding.metaKeywords.getText().toString().trim());
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
}
