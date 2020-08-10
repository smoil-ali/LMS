package com.example.lms.Repository;

import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.SettingsListener;
import com.example.lms.Model.SettingsResponse;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsRepository {
    String TAG=SettingsRepository.class.getSimpleName();
    AcademyApis academyApis;
    SettingsListener settingsListener;

    public SettingsRepository(ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
        academyApis= RetrofitService.createService(AcademyApis.class);
        Call<SettingsResponse> settingsResponseCall=academyApis.getSettingsData();
        settingsResponseCall.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
                if (response.isSuccessful()){
                    SettingsResponse settingsResponse=response.body();
                    if (settingsResponse.getCode().equals("200")){
                        settingsListener.settingsDataListener(settingsResponse.getData());
                        progressBar.setVisibility(View.GONE);
                    }else{
                        settingsListener.settingsError(settingsResponse.getMessage());
                    }
                }else{
                    settingsListener.settingsError(response.message());
                }
            }
            @Override
            public void onFailure(Call<SettingsResponse> call, Throwable t) {
                settingsListener.settingsError(t.getMessage());
            }
        });

    }

    public void setSettingsListener(SettingsListener listener){
        this.settingsListener=listener;
    }
}
