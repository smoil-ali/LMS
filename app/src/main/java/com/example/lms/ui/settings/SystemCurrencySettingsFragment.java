package com.example.lms.ui.settings;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.Factories.SettingsFactory;
import com.example.lms.R;
import com.example.lms.ViewModels.SettingsViewModel;
import com.example.lms.databinding.FragmentCurrencySettingsBinding;

public class SystemCurrencySettingsFragment extends Fragment {

    FragmentCurrencySettingsBinding binding;
    SettingsViewModel settingsViewModel;
    ArrayAdapter<CharSequence> arrayAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentCurrencySettingsBinding.inflate(inflater, container, false);
        setCurrencyPositionSpinner();
        settingsViewModel=new ViewModelProvider(requireActivity(), new SettingsFactory(binding.progressBar)).get(SettingsViewModel.class);
        settingsViewModel.getSettingsMutableData().observe(requireActivity(),data -> {
            String item=data.getCurrency_position().substring(0,1).toUpperCase()+data.getCurrency_position().substring(1);
            int position=arrayAdapter.getPosition(item);
            Log.i("position",String.valueOf(position+" "+item));
            binding.currencyPositionSettings.setSelection(position);
        });
        return binding.getRoot();
    }

    private void setCurrencyPositionSpinner() {
        arrayAdapter=ArrayAdapter.createFromResource(requireActivity(),R.array.currency_position,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.currencyPositionSettings.setAdapter(arrayAdapter);
    }
}
