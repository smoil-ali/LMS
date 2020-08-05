package com.example.lms.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.Factories.SettingsFactory;
import com.example.lms.Model.StripeKey;
import com.example.lms.R;
import com.example.lms.ViewModels.SettingsViewModel;
import com.example.lms.databinding.FragmentSetupStripeSettingsBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SetupStripeSettingsFragment extends Fragment {
    FragmentSetupStripeSettingsBinding binding;
    SettingsViewModel settingsViewModel;
    JSONObject jsonObject;
    ArrayAdapter arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSetupStripeSettingsBinding.inflate(inflater, container, false);
        setActiveSpinner();
        setTestModeSpinner();
        settingsViewModel= new ViewModelProvider(requireActivity(),new SettingsFactory(binding.progressBar)).get(SettingsViewModel.class);
        settingsViewModel.getSettingsMutableData().observe(requireActivity(),data -> {
            String strip=data.getStripe_keys();

            try {
                JSONArray jsonArray=new JSONArray(strip);
                jsonObject= jsonArray.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StripeKey stripeKey=new Gson().fromJson(String.valueOf(jsonObject),StripeKey.class);

            binding.testSecretKey.setText(stripeKey.getSecret_key());
            binding.liveSecretKey.setText(stripeKey.getSecret_live_key());
            binding.testPublicKey.setText(stripeKey.getPublic_key());
            binding.livePublicKey.setText(stripeKey.getPublic_live_key());
            if (stripeKey.getActive().equals("1"))
                binding.active.setSelection(0);
            else if (stripeKey.getActive().equals("0"))
                binding.active.setSelection(1);


            if (stripeKey.getTestmode().equalsIgnoreCase("on"))
                binding.mode.setSelection(0);
            else
                binding.mode.setSelection(1);
            binding.progressBar.setVisibility(View.GONE);
        });

        return binding.getRoot();
    }
    private void setTestModeSpinner(){
        arrayAdapter=ArrayAdapter.createFromResource(requireContext(),R.array.testMode,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.mode.setAdapter(arrayAdapter);
    }

    private void setActiveSpinner(){
        arrayAdapter=ArrayAdapter.createFromResource(requireContext(), R.array.active_paypal,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.active.setAdapter(arrayAdapter);
    }
}
