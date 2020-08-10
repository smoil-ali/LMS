package com.example.lms.Repository;

import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.WebsiteSettingsListener;
import com.example.lms.Model.WebsiteResponse;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebsiteSettingsRepository {

    String TAG= WebsiteSettingsRepository.class.getSimpleName();
    WebsiteSettingsListener websiteSettingsListener;
    AcademyApis academyApis;

    public WebsiteSettingsRepository(ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
        academyApis= RetrofitService.createService(AcademyApis.class);
        Call<WebsiteResponse> websiteResponseCall=academyApis.getWebsiteData();
        websiteResponseCall.enqueue(new Callback<WebsiteResponse>() {
            @Override
            public void onResponse(Call<WebsiteResponse> call, Response<WebsiteResponse> response) {
                if (response.isSuccessful()){
                    WebsiteResponse websiteResponse=response.body();
                    if (websiteResponse.getCode().equals("200")){
                        websiteSettingsListener.websiteSettingsListener(websiteResponse.getData());
                        progressBar.setVisibility(View.GONE);
                    }else{
                        websiteSettingsListener.websiteSettingsError(websiteResponse.getMessage());
                    }
                }else{
                    websiteSettingsListener.websiteSettingsError(response.message());
                }
            }

            @Override
            public void onFailure(Call<WebsiteResponse> call, Throwable t) {
                websiteSettingsListener.websiteSettingsError(t.getMessage());
            }
        });
    }

    public void setListener(WebsiteSettingsListener listener){
        this.websiteSettingsListener=listener;
    }
}
