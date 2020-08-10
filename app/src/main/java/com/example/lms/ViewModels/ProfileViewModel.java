package com.example.lms.ViewModels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.ProfileListener;
import com.example.lms.Model.data;
import com.example.lms.Repository.ProfileRepository;

public class ProfileViewModel extends ViewModel implements ProfileListener {
    MutableLiveData<data> mutableLiveData;
    ProfileRepository profileRepository;
    public final String TAG = ProfileRepository.class.getSimpleName();


    public ProfileViewModel(Context context) {
        mutableLiveData = new MutableLiveData<>();
        profileRepository = new ProfileRepository(context,this);
    }

    public MutableLiveData<data> getMutableLiveData() {
        return mutableLiveData;
    }

    @Override
    public void onSuccess(data data) {
        Log.i(TAG,data.getUser().getFirst_name());
        mutableLiveData.setValue(data);
    }


}
