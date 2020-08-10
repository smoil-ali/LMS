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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.Factories.SettingsFactory;
import com.example.lms.Model.SettingsResponse;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.ViewModels.SettingsViewModel;
import com.example.lms.databinding.FragmentSystemSettingsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SystemSettingsFragment extends Fragment{

    FragmentSystemSettingsBinding binding;
    Handler handler;
    String text ;
    String key;
    SettingsViewModel settingsViewModel;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentSystemSettingsBinding.inflate(inflater,container,false);
        settingsViewModel=new  ViewModelProvider(requireActivity(),new SettingsFactory(binding.progressBar)).get(SettingsViewModel.class);

        settingsViewModel.getSettingsMutableData().observe(requireActivity(),data -> {
            binding.websiteName.setText(data.getSystem_name());
            binding.websiteTitle.setText(data.getSystem_title());
            binding.websiteKeywords.setText(data.getWebsite_keywords());
            binding.websiteDescrioption.setText(data.getWebsite_description());
            binding.author.setText(data.getAuthor());
            binding.slogan.setText(data.getSlogan());
            binding.systemEmail.setText(data.getSystem_email());
            binding.address.setText(data.getAddress());
            binding.phone.setText(data.getPhone());
            binding.youtubeApiKey.setText(data.getYoutube_api_key());
            binding.vimeoApiKey.setText(data.getVimeo_api_key());
            binding.purchaseCode.setText(data.getPurchase_code());
            binding.footerLink.setText(data.getFooter_link());
            binding.footerText.setText(data.getFooter_text());
            binding.progressBar.setVisibility(View.GONE);
            if (data.getStudent_email_verification().equalsIgnoreCase("enable"))
            binding.studentEmailVerification.setSelection(1);
            else if (data.getStudent_email_verification().equalsIgnoreCase("disable"))
            binding.studentEmailVerification.setSelection(0);

            if (data.getLanguage().equalsIgnoreCase("english"))
                binding.systemLanguage.setSelection(0);
            else if (data.getLanguage().equalsIgnoreCase("urdu"))
                binding.systemLanguage.setSelection(1);
            else if (data.getLanguage().equalsIgnoreCase("bengali"))
                binding.systemLanguage.setSelection(2);
        });


        handler =new Handler();
        binding.websiteTitle.addTextChangedListener(new GenricTextWatcher(binding.websiteTitle));
        binding.websiteName.addTextChangedListener(new GenricTextWatcher(binding.websiteName));
        binding.websiteDescrioption.addTextChangedListener(new GenricTextWatcher(binding.websiteDescrioption));
        binding.purchaseCode.addTextChangedListener(new GenricTextWatcher(binding.purchaseCode));
        binding.vimeoApiKey.addTextChangedListener(new GenricTextWatcher(binding.vimeoApiKey));
        binding.youtubeApiKey.addTextChangedListener(new GenricTextWatcher(binding.youtubeApiKey));
        binding.phone.addTextChangedListener(new GenricTextWatcher(binding.phone));
        binding.address.addTextChangedListener(new GenricTextWatcher(binding.address));
        binding.systemEmail.addTextChangedListener(new GenricTextWatcher(binding.systemEmail));
        binding.slogan.addTextChangedListener(new GenricTextWatcher(binding.slogan));
        binding.author.addTextChangedListener(new GenricTextWatcher(binding.author));
        binding.footerText.addTextChangedListener(new GenricTextWatcher(binding.footerText));
        binding.footerLink.addTextChangedListener(new GenricTextWatcher(binding.footerLink));
        binding.studentEmailVerification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text=binding.studentEmailVerification.getItemAtPosition(position).toString().toLowerCase();
                Log.i("text",text);
                key="student_email_verification";
                handler.removeCallbacks(runable);
                handler.postDelayed(runable, 1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.systemLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text=binding.systemLanguage.getItemAtPosition(position).toString().toLowerCase();
                key="language";
                handler.removeCallbacks(runable);
                handler.postDelayed(runable, 1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return binding.getRoot();
    }


        Runnable runable=new Runnable() {
            @Override
            public void run() {
                updateRecord(key,text);

            }
        };

    private class GenricTextWatcher implements TextWatcher {
        View view;

        public GenricTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            text = s.toString();

            switch (view.getId()){
                case R.id.websiteTitle:
                    key="system_title";
                    break;
                case R.id.websiteName:
                    key="system_name";
                    break;
                case R.id.websiteDescrioption:
                    key="website_description";
                    break;
                case R.id.purchaseCode:
                    key="purchase_code";
                    break;
                case R.id.vimeoApiKey:
                    key="vimeo_api_key";
                    break;
                case R.id.youtubeApiKey:
                    key="youtube_api_key";
                    break;
                case R.id.phone:
                    key="phone";
                    break;
                case R.id.address:
                    key="address";
                    break;

                case R.id.systemEmail:
                    key="system_email";
                    break;

                case R.id.slogan:
                    key="slogan";
                    break;
                case R.id.author:
                    key="author";
                    break;
                case R.id.footerText:
                    key="footer_text";
                    break;
                case R.id.footerLink:
                    key="footer_link";
                    break;
            }

            handler.removeCallbacks(runable);
            handler.postDelayed(runable, 1000);

        }

    }

    public void updateRecord(String key, String value){

        AcademyApis academyApis= RetrofitService.createService(AcademyApis.class);
        Call<SettingsResponse> settingsResponseCall=academyApis.updateSettings(key,value);
        settingsResponseCall.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {

                SettingsResponse settingsResponse=response.body();
                if (response.isSuccessful()){
                    settingsViewModel.udpateData(binding.progressBar);
                    Toast.makeText(context,settingsResponse.getStatus(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,settingsResponse.getStatus(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SettingsResponse> call, Throwable t) {
                new AlertDialog.Builder(context)
                        .setTitle("Failed")
                        .setMessage(t.getMessage())
                        .setPositiveButton("Ok",(((dialog, which) -> dialog.dismiss()))).show();
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
