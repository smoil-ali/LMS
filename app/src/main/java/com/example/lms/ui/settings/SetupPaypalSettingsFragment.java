package com.example.lms.ui.settings;

import android.content.Context;
import android.os.Bundle;
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
import com.example.lms.Model.Paypal;
import com.example.lms.Model.paypalArray;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.ViewModels.SettingsViewModel;
import com.example.lms.databinding.FragmentSetupPaypalSettingsBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetupPaypalSettingsFragment extends Fragment {
    FragmentSetupPaypalSettingsBinding binding;
    SettingsViewModel settingsViewModel;
    JSONObject paypalObj;
    ArrayAdapter adapter;
    ArrayAdapter<String> currencyAdapter;
    List<String> list;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSetupPaypalSettingsBinding.inflate(inflater, container, false);
        setActiveSpinner();
        setModeSpinner();
        getAllCurrency();
        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePaypalSettings();
            }
        });



        settingsViewModel= new ViewModelProvider(requireActivity(), new SettingsFactory(binding.progressBar)).get(SettingsViewModel.class);
        settingsViewModel.getSettingsMutableData().observe(requireActivity(),data -> {
            String paypal=data.getPaypal();

            try {
                JSONArray jsonArray=new JSONArray(paypal);
                paypalObj=jsonArray.getJSONObject(0);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Paypal model=new Gson().fromJson(String.valueOf(paypalObj),  Paypal.class);
//            data.setMode(model.getMode());
           /* data.setSandbox(model.getSandbox());
            data.setSandbox_client_id(model.getSandbox_client_id());
            data.setSandbox_secret_key(model.getSandbox_secret_key());
            data.setProduction_client_id(model.getProduction_client_id());
            data.setProduction_secret_key(model.getProduction_secret_key());*/
            binding.clientIdSandbox.setText(model.getSandbox_client_id());
            binding.secretKeySandbox.setText(model.getSandbox_secret_key());
            binding.clientIdProduction.setText(model.getProduction_client_id());
            binding.secretKeyProduction.setText(model.getProduction_secret_key());
            if (model.getActive().equals("1"))
                binding.active.setSelection(0);
            else if (model.getActive().equals("0"))
                binding.active.setSelection(1);
            String item=model.getMode().substring(0,1).toUpperCase()+model.getMode().substring(1).toLowerCase();
            int position=adapter.getPosition(item);
            binding.mode.setSelection(position);
            binding.progressBar.setVisibility(View.GONE);

        });
        return binding.getRoot();
    }

    private void setActiveSpinner(){
        adapter=ArrayAdapter.createFromResource(requireContext(), R.array.active_paypal,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.active.setAdapter(adapter);
    }

    private void setModeSpinner(){
        adapter=ArrayAdapter.createFromResource(requireActivity(),R.array.mode,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.mode.setAdapter(adapter);
    }




    private void getAllCurrency(){
        AcademyApis academyApis= RetrofitService.createService(AcademyApis.class);
        Call<AllCurrencies> allCurrenciesCall=academyApis.getPaypalCurrencies();
        allCurrenciesCall.enqueue(new Callback<AllCurrencies>() {
            @Override
            public void onResponse(Call<AllCurrencies> call, Response<AllCurrencies> response) {
                if (response.isSuccessful()){
                    AllCurrencies currenciesResponse=response.body();
                    if (currenciesResponse.getCode().equals("200")){
                        list= currenciesResponse.getData();
                        currencyAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,list);
                        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.paypalCurrency.setAdapter(currencyAdapter);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    /*
        *update paypal settings method
    */
    private void updatePaypalSettings(){
        AcademyApis academyApis=RetrofitService.createService(AcademyApis.class);
        String status;
        if (binding.active.getSelectedItem().toString().equalsIgnoreCase("yes"))
            status="1";
        else
            status="0";
        Call<AllCurrencies> currenciesCall=academyApis.updatePaypalSettings(status
                ,binding.mode.getSelectedItem().toString().toLowerCase(), Objects.requireNonNull(binding.clientIdSandbox.getText()).toString()
                , Objects.requireNonNull(binding.secretKeySandbox.getText()).toString(), Objects.requireNonNull(binding.clientIdProduction.getText()).toString()
                , Objects.requireNonNull(binding.secretKeyProduction.getText()).toString());

        currenciesCall.enqueue(new Callback<AllCurrencies>() {
            @Override
            public void onResponse(Call<AllCurrencies> call, Response<AllCurrencies> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().getCode().equals("200"))
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AllCurrencies> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
