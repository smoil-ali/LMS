package com.example.lms.ViewModels;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.Listener.SectionListener;
import com.example.lms.Model.SectionResponse;
import com.example.lms.Model.Section_LessonModel;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Repository.SectionRepository;
import com.example.lms.Repository.StudentRepository;

import java.util.List;



public class SectionViewModel extends ViewModel implements SectionListener {
    MutableLiveData<List<Section_LessonModel>> MutableLiveData;
    SectionRepository repository;
    String TAG = SectionViewModel.class.getSimpleName();

    public SectionViewModel(Context context, ProgressBar progressBar, String id) {
        MutableLiveData = new MutableLiveData<>();
        repository = new SectionRepository(context,progressBar,id);
        repository.setListener(this);
    }

    public void update(Context context,ProgressBar progressBar,String id){
        repository = new SectionRepository(context,progressBar,id);
        repository.setListener(this);
    }

    public androidx.lifecycle.MutableLiveData<List<Section_LessonModel>> getMutableLiveData() {
        return MutableLiveData;
    }



    @Override
    public void onSuccess(List<Section_LessonModel> list) {
        MutableLiveData.setValue(list);
    }
}
