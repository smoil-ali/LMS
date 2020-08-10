package com.example.lms.Repository;

import android.content.Context;

import com.example.lms.Listener.ProfileListener;
import com.example.lms.Listener.StudentListener;
import com.example.lms.Model.Utils;

public class ProfileRepository {
    ProfileListener profileListener;

    public ProfileRepository(Context context,ProfileListener profileListener) {
        this.profileListener = profileListener;
        profileListener.onSuccess(Utils.getProfileData(context));
    }

}
