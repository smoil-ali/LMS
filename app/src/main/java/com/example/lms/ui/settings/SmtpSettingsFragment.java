package com.example.lms.ui.settings;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.example.lms.databinding.FragmentSmtpSettingsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmtpSettingsFragment extends Fragment {

    FragmentSmtpSettingsBinding binding;
    String key;
    String text;
    Handler handler;
    SettingsViewModel settingsViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSmtpSettingsBinding.inflate(inflater,container,false);
        settingsViewModel=new ViewModelProvider(requireActivity(),new SettingsFactory(binding.progressBar)).get(SettingsViewModel.class);
        settingsViewModel.getSettingsMutableData().observe(requireActivity(),data -> {
            binding.host.setText(data.getSmtp_host());
            binding.smtpUsername.setText(data.getSmtp_user());
            binding.smtpPassword.setText(data.getSmtp_pass());
            binding.smtpPort.setText(data.getSmtp_port());
            binding.protocol.setText(data.getProtocol());
            binding.progressBar.setVisibility(View.GONE);
        });


        binding.protocol.addTextChangedListener(new GenericTextWatcher(binding.protocol));
        binding.smtpPort.addTextChangedListener(new GenericTextWatcher(binding.smtpPort));
        binding.smtpUsername.addTextChangedListener(new GenericTextWatcher(binding.smtpUsername));
        binding.smtpPassword.addTextChangedListener(new GenericTextWatcher(binding.smtpPassword));
        binding.host.addTextChangedListener(new GenericTextWatcher(binding.host));
        handler=new Handler();
        return binding.getRoot();
    }



    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            updateRecord(key,text);
        }
    };

    public void updateRecord(String key, String value){

        AcademyApis academyApis= RetrofitService.createService(AcademyApis.class);
        Call<SettingsResponse> settingsResponseCall=academyApis.updateSettings(key,value);
        settingsResponseCall.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {

                SettingsResponse settingsResponse=response.body();
                if (response.isSuccessful()){
                    settingsViewModel.udpateData(binding.progressBar);
                    Toast.makeText(requireContext(),settingsResponse.getStatus(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(requireContext(),settingsResponse.getStatus(),Toast.LENGTH_SHORT).show();
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
    private class GenericTextWatcher implements TextWatcher{

        View view;
        public GenericTextWatcher(View view){
            this.view=view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            text=s.toString();
            switch (view.getId()){
                case R.id.protocol:
                    key="protocol";
                    break;
                case R.id.host:
                    key="smtp_host";
                    break;
                case R.id.smtp_port:
                    key="smtp_port";
                    break;
                case R.id.smtp_username:
                    key="smtp_user";
                    break;
                case R.id.smtp_password:
                    key="smtp_pass";
                    break;
            }

            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable,1000);

        }
    }
}
