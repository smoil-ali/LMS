package com.example.lms.ui.addcourses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.databinding.FragmentBasicBinding;

public class BasicFragment extends Fragment {

    FragmentBasicBinding fragmentBasicBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentBasicBinding=FragmentBasicBinding.inflate(inflater,container,false);

        return fragmentBasicBinding.getRoot();
    }
}
