package com.example.lms.ui.instructors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.R;
import com.example.lms.databinding.FragmentPayoutCompletedBinding;

public class CompletePayoutFragment extends Fragment {

    FragmentPayoutCompletedBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPayoutCompletedBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }
}
