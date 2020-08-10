package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.InstructorListener;
import com.example.lms.Listener.LessonListener;
import com.example.lms.Listener.SectionListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.LessonData;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.SectionResponse;
import com.example.lms.Model.Section_LessonModel;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.RESPONSE_FAILED;
import static com.example.lms.Model.Constants.SUCCESS;

public class SectionRepository implements LessonListener {
    String TAG = SectionRepository.class.getSimpleName();
    LessonRepository lessonRepository;
    SectionListener listener;
    AcademyApis academyApis ;
    List<Section_LessonModel> section_lessonModels;
    int sectionListSize = 0;


    public SectionRepository(Context context, ProgressBar progressBar,String id) {
        progressBar.setVisibility(View.VISIBLE);
        section_lessonModels = new ArrayList<>();
        lessonRepository = new LessonRepository(context);
        lessonRepository.setLessonListener(this);
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<SectionResponse> SectionResponseCall = academyApis.getSectionData(id);
        Log.i(TAG,SectionResponseCall.request().url()+"");
        SectionResponseCall.enqueue(new Callback<SectionResponse>() {
            @Override
            public void onResponse(Call<SectionResponse> call, Response<SectionResponse> response) {

                if (response.isSuccessful()){
                    if (response.body().getCode().equals("200") && response.body().getStatus().equals(SUCCESS)){
                        sectionListSize = response.body().getData().size();
                        for (SectionData sectionData:response.body().getData()){
                            lessonRepository.getLesson(sectionData);
                        }
                    }else {
                        progressBar.setVisibility(View.GONE);
                        Utils.showDialog(context,response.body().getStatus()+" In Section",response.body().getMessage());
                    }
                }else {
                    progressBar.setVisibility(View.GONE);
                    Utils.showDialog(context,RESPONSE_FAILED+" In Section",response.message());
                }

            }
            @Override
            public void onFailure(Call<SectionResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Utils.showDialog(context, Constants.FAILED+" In Section",t.getMessage());
            }
        });
    }

    public void setListener(SectionListener listener){
        this.listener = listener;
    }

    @Override
    public void onSuccess(List<LessonData> lessonData,SectionData sectionData) {
        Section_LessonModel model = new Section_LessonModel();
        model.setSectionData(sectionData);
        model.setLessonDataList(lessonData);
        section_lessonModels.add(model);
        if (sectionListSize == section_lessonModels.size()){
            listener.onSuccess(section_lessonModels);
        }
    }
}
