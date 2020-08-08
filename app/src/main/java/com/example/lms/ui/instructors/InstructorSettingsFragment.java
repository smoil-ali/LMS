package com.example.lms.ui.instructors;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.lms.databinding.FragmentInstructorSettingsBinding;

import org.apache.http.conn.ConnectTimeoutException;
import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstructorSettingsFragment extends Fragment {
    FragmentInstructorSettingsBinding binding;
    SettingsViewModel settingsViewModel;
    Handler handler;
    String key,value;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentInstructorSettingsBinding.inflate(inflater,container,false);
        settingsViewModel=new ViewModelProvider(requireActivity(),new SettingsFactory(binding.progressBar)).get(SettingsViewModel.class);
        handler=new Handler();
        binding.revenuePercentage.addTextChangedListener(new GenricTextWatcher(binding.revenuePercentage));
        binding.note.addTextChangedListener(new GenricTextWatcher(binding.note));
        settingsViewModel.getSettingsMutableData().observe(requireActivity(),data -> {
            binding.note.setText(data.getInstructor_application_note());
            binding.revenuePercentage.setText(data.getInstructor_revenue());
            binding.adminPercentage.setText("");
            if (data.getAllow_instructor().equals("1"))
                binding.allowPb.setSelection(0);
            else if (data.getAllow_instructor().equals("0"))
                binding.allowPb.setSelection(1);

            binding.allowPb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    key="allow_instructor";
                    value=binding.allowPb.getSelectedItem().toString();
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable,1000);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            binding.progressBar.setVisibility(View.GONE);

        });
        return binding.getRoot();
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            updateRecord(key,value);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    public class GenricTextWatcher implements TextWatcher{
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
            value=s.toString();
            switch (view.getId()){
                case R.id.note:
                    key="instructor_application_note";
                    break;

                case R.id.revenue_percentage:
                    key="instructor_revenue";
                    break;
            }
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable,1000);
        }
    }
}
