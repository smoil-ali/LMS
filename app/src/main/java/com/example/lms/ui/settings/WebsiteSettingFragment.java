package com.example.lms.ui.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.Factories.WebSettingsFactory;
import com.example.lms.Model.SettingsResponse;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.ViewModels.WebSettingsViewModel;
import com.example.lms.databinding.FragmentWebsiteSettingsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebsiteSettingFragment extends Fragment {
    FragmentWebsiteSettingsBinding binding;
    Handler handler;
    String value;
    String key;
    Context context;
    WebSettingsViewModel webSettingsViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentWebsiteSettingsBinding.inflate(inflater,container,false);

        webSettingsViewModel=new ViewModelProvider(requireActivity(),new WebSettingsFactory(binding.progressBar)).get(WebSettingsViewModel.class);
        webSettingsViewModel.getwebMutableLiveData().observe(requireActivity(),websiteSettingData -> {
            binding.bannerTitle.setText(websiteSettingData.getBanner_title());
            binding.bannerSubtitle.setText(websiteSettingData.getBanner_sub_title());
            binding.privacyPolicy.setText(websiteSettingData.getPrivacy_policy());
            binding.cookiePolicy.setText(websiteSettingData.getCookie_policy());
            binding.cookieNote.setText(websiteSettingData.getCookie_note());
            binding.termsAndCondition.setText(websiteSettingData.getTerms_and_condition());
            binding.aboutUs.setText(websiteSettingData.getAbout_us());
            binding.progressBar.setVisibility(View.GONE);
            if (websiteSettingData.getCookie_status().equals("active"))
                binding.active.setChecked(true);
            else if (websiteSettingData.getCookie_status().equals("inactive"))
                binding.inActive.setChecked(true);

            binding.progressBar.setVisibility(View.GONE);

        });
        handler=new Handler();

        binding.bannerTitle.addTextChangedListener(new GenricTextWatcher(binding.bannerTitle));
        binding.bannerSubtitle.addTextChangedListener(new GenricTextWatcher(binding.bannerSubtitle));
        binding.aboutUs.addTextChangedListener(new GenricTextWatcher(binding.aboutUs));
        binding.cookieNote.addTextChangedListener(new GenricTextWatcher(binding.cookieNote));
        binding.cookiePolicy.addTextChangedListener(new GenricTextWatcher(binding.cookiePolicy));
        binding.termsAndCondition.addTextChangedListener(new GenricTextWatcher(binding.termsAndCondition));
        binding.privacyPolicy.addTextChangedListener(new GenricTextWatcher(binding.privacyPolicy));
        binding.cookieStatusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=binding.cookieStatusGroup.findViewById(checkedId);
                if (radioButton.isChecked()){
                    value=radioButton.getText().toString();
                    updateRecord("cookie_status",value.toLowerCase());
                }
            }
        });
        return binding.getRoot();
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            updateRecord(key,value);
            Log.i("runable","runi");
        }
    };
    public void updateRecord(String key, String value){

        AcademyApis academyApis= RetrofitService.createService(AcademyApis.class);
        Call<SettingsResponse> settingsResponseCall=academyApis.updateWebSettings(key,value);
        settingsResponseCall.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {

                SettingsResponse settingsResponse=response.body();
                if (response.isSuccessful()){
                    webSettingsViewModel.update(binding.progressBar);
                    Toast.makeText(context,settingsResponse.getStatus(),Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context,settingsResponse.getStatus(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SettingsResponse> call, Throwable t) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Failed")
                        .setMessage(t.getMessage())
                        .setPositiveButton("Ok",(((dialog, which) -> dialog.dismiss()))).show();
            }
        });


    }
    private class GenricTextWatcher implements TextWatcher {

        View view;

        public GenricTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            value=s.toString();
            switch (view.getId()){
                case R.id.bannerTitle:
                        key="banner_title";
                    break;
                case R.id.bannerSubtitle:
                        key="banner_sub_title";
                    break;
                case R.id.aboutUs:
                        key="about_us";
                    break;
                case R.id.cookieNote:
                        key="cookie_note";
                    break;
                case R.id.cookiePolicy:
                    key="cookie_policy";
                    break;
                case R.id.termsAndCondition:
                    key="terms_and_condition";
                    break;
                case R.id.privacyPolicy:
                    key="privacy_policy";
                    break;
            }
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable,1000);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

}
