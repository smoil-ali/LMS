package com.example.lms.Listener;

import com.example.lms.Model.SettingsData;

public interface SettingsListener {
    public void settingsDataListener(SettingsData data);
    public void settingsError(String error);
}
