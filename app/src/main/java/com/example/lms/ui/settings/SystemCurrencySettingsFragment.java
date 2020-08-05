package com.example.lms.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.Factories.SettingsFactory;
import com.example.lms.Model.AllCurrencies;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.ViewModels.SettingsViewModel;
import com.example.lms.databinding.FragmentCurrencySettingsBinding;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentCurrencySettingsBinding.inflate(inflater, container, false);
        list=new ArrayList<>();
        setCurrencyPositionSpinner();
        getAllCurrency();

        settingsViewModel=new ViewModelProvider(requireActivity(), new SettingsFactory(binding.progressBar)).get(SettingsViewModel.class);
        settingsViewModel.getSettingsMutableData().observe(requireActivity(),data -> {
            String item=data.getCurrency_position().substring(0,1).toUpperCase()+data.getCurrency_position().substring(1);
            int position=arrayAdapter.getPosition(item);
            Log.i("position",String.valueOf(position+" "+item));
            binding.currencyPositionSettings.setSelection(position);
            binding.progressBar.setVisibility(View.GONE);
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
                       Log.i("list",list.get(1));

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


}
