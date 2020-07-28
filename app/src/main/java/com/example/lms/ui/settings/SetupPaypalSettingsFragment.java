package com.example.lms.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.databinding.FragmentSetupPaypalSettingsBinding;

public class SetupPaypalSettingsFragment extends Fragment {
    FragmentSetupPaypalSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSetupPaypalSettingsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
