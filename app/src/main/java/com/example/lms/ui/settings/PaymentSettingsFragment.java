package com.example.lms.ui.settings;

import android.icu.text.StringPrepParseException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lms.R;
import com.example.lms.databinding.FragmentPaymentSettingsBinding;
import com.google.android.material.tabs.TabLayout;

public class PaymentSettingsFragment extends Fragment {

    FragmentPaymentSettingsBinding binding;
    Fragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentPaymentSettingsBinding.inflate(inflater,container,false);

        setTabLayout();
        return binding.getRoot();
    }

    private void setTabLayout() {

       fragment=new SystemCurrencySettingsFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.tabHostFragment,fragment).commit();

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (binding.tabs.getSelectedTabPosition()){
                    case 0:
                        fragment=new SystemCurrencySettingsFragment();
                        break;
                    case 1:
                        fragment=new SetupPaypalSettingsFragment();
                        break;
                    case 2:
                        fragment=new SetupStripeSettingsFragment();
                        break;
                }
                requireActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.tabHostFragment,fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
