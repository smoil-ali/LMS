package com.example.lms.ui.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.Factories.SettingsFactory;
import com.example.lms.ViewModels.SettingsViewModel;
import com.example.lms.databinding.FragmentSetupPaypalSettingsBinding;

public class SetupPaypalSettingsFragment extends Fragment {
    FragmentSetupPaypalSettingsBinding binding;
    SettingsViewModel settingsViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSetupPaypalSettingsBinding.inflate(inflater, container, false);
        settingsViewModel= new ViewModelProvider(requireActivity(), new SettingsFactory(binding.progressBar)).get(SettingsViewModel.class);

        settingsViewModel.getSettingsMutableData().observe(requireActivity(),data -> {
            String paypal=data.getPaypal();
            Log.i("paypal",paypal);
            /*binding.clientIdSandbox.setText();
            binding.secretKeySandbox.setText();
            binding.clientIdProduction.setText();
            binding.secretKeyProduction.setText();*/
        });
        return binding.getRoot();
    }
}
