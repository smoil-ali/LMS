package com.example.lms.ui.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.Factories.SettingsFactory;
import com.example.lms.Model.AllCurrencies;
import com.example.lms.Model.SettingsResponse;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.ViewModels.SettingsViewModel;
import com.example.lms.databinding.FragmentCurrencySettingsBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SystemCurrencySettingsFragment extends Fragment {

    FragmentCurrencySettingsBinding binding;
    SettingsViewModel settingsViewModel;
    ArrayAdapter<CharSequence> arrayAdapter;
    ArrayAdapter<String> currencyAdapter;
    List<String> list;
    Context context;
    String key,value;
    Handler handler;
    String currency;
    int pos;
    static int count=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentCurrencySettingsBinding.inflate(inflater, container, false);
        list=new ArrayList<>();
        setCurrencyPositionSpinner();

        handler=new Handler();

        settingsViewModel=new ViewModelProvider(requireActivity(), new SettingsFactory(binding.progressBar)).get(SettingsViewModel.class);
        settingsViewModel.getSettingsMutableData().observe(requireActivity(),data -> {
            String item=data.getCurrency_position().substring(0,1).toUpperCase()+data.getCurrency_position().substring(1);
            if (data.getCurrency_position().equalsIgnoreCase("left"))
                binding.currencyPositionSettings.setSelection(0);
            else if (data.getCurrency_position().equalsIgnoreCase("right"))
                binding.currencyPositionSettings.setSelection(1);
            else if (data.getCurrency_position().equals("left-space"))
                binding.currencyPositionSettings.setSelection(2);
            else
                binding.currencyPositionSettings.setSelection(3);
            binding.progressBar.setVisibility(View.GONE);

            currency=data.getSystem_currency();
            getAllCurrency();

        });

        binding.currencySettings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value=binding.currencySettings.getSelectedItem().toString();
                key="system_currency";
                Log.i("spin",value);

                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.currencyPositionSettings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==2)
                    value="left-space";
                else if (position==3)
                    value="right-space";
                else
                value=binding.currencyPositionSettings.getSelectedItem().toString().toLowerCase();

                key="currency_position";
                Log.i("spin",value);
                ++count;
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
    private void setCurrencyPositionSpinner() {
        arrayAdapter=ArrayAdapter.createFromResource(requireActivity(),R.array.currency_position,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.currencyPositionSettings.setAdapter(arrayAdapter);
    }

    private void getAllCurrency(){
        AcademyApis academyApis= RetrofitService.createService(AcademyApis.class);
        Call<AllCurrencies> allCurrenciesCall=academyApis.getAllCurrencies();
        allCurrenciesCall.enqueue(new Callback<AllCurrencies>() {
            @Override
            public void onResponse(Call<AllCurrencies> call, Response<AllCurrencies> response) {
                if (response.isSuccessful()){
                    AllCurrencies currenciesResponse=response.body();
                    if (currenciesResponse.getCode().equals("200")){
                       list= currenciesResponse.getData();
                        currencyAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,list);
                        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.currencySettings.setAdapter(currencyAdapter);
                        pos=currencyAdapter.getPosition(currency);
                        binding.currencySettings.setSelection(pos);
                       Log.i("list",String.valueOf(pos));
                       Log.i("currency",currency+"");

                    }else{
                        Toast.makeText(requireContext(),response.message(),Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(requireContext(),response.message(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<AllCurrencies> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
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


}
