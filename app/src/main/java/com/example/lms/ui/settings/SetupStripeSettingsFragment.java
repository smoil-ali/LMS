package com.example.lms.ui.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.Factories.SettingsFactory;
import com.example.lms.Model.AllCurrencies;
import com.example.lms.Model.SettingsResponse;
import com.example.lms.Model.StripeKey;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.ViewModels.SettingsViewModel;
import com.example.lms.databinding.FragmentSetupStripeSettingsBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetupStripeSettingsFragment extends Fragment {
    FragmentSetupStripeSettingsBinding binding;
    SettingsViewModel settingsViewModel;
    JSONObject jsonObject;
    ArrayAdapter currencyAdapter;
    ArrayAdapter arrayAdapter;
    List<String> list;
    Context context;
    String key;
    String value;
    Handler handler;
    String currency;
    int pos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSetupStripeSettingsBinding.inflate(inflater, container, false);
        setActiveSpinner();
        setTestModeSpinner();

        handler=new Handler();

        binding.stripeCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value=binding.stripeCurrency.getSelectedItem().toString();
                key="stripe_currency";
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStripeSettings();
            }
        });
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


            currency=data.getStripe_currency();
            getAllCurrency();

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

    private void getAllCurrency(){
        AcademyApis academyApis= RetrofitService.createService(AcademyApis.class);
        Call<AllCurrencies> allCurrenciesCall=academyApis.getStripeCurrencies();
        allCurrenciesCall.enqueue(new Callback<AllCurrencies>() {
            @Override
            public void onResponse(Call<AllCurrencies> call, Response<AllCurrencies> response) {
                if (response.isSuccessful()){
                    AllCurrencies currenciesResponse=response.body();
                    if (currenciesResponse.getCode().equals("200")){
                        list= currenciesResponse.getData();
                        currencyAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,list);
                        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.stripeCurrency.setAdapter(currencyAdapter);
                        pos=currencyAdapter.getPosition(currency);

                        binding.stripeCurrency.setSelection(pos);
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
    private void updateStripeSettings(){
        AcademyApis academyApis=RetrofitService.createService(AcademyApis.class);
        String status;
        if (binding.active.getSelectedItem().toString().equalsIgnoreCase("yes"))
            status="1";
        else
            status="0";
        Call<AllCurrencies> currenciesCall=academyApis.updateStripeSettings(status,
                binding.mode.getSelectedItem().toString().toLowerCase(), Objects.requireNonNull(binding.testPublicKey.getText()).toString()
        , Objects.requireNonNull(binding.testSecretKey.getText()).toString(), Objects.requireNonNull(binding.livePublicKey.getText()).toString(), Objects.requireNonNull(binding.liveSecretKey.getText()).toString());

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
