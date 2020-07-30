package com.example.lms.Listener;

import com.example.lms.Model.WebsiteSettingData;

public interface WebsiteSettingsListener {
    public void websiteSettingsListener(WebsiteSettingData websiteSettingData);
    public void websiteSettingsError(String error);
}
