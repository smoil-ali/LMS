package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lms.Listener.LessonListener;
import com.example.lms.Model.Lesson;
import com.example.lms.Model.LessonData;
import com.example.lms.Model.LessonResponse;
import com.example.lms.Model.Section;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.FAILED;
import static com.example.lms.Model.Constants.RESPONSE_FAILED;
import static com.example.lms.Model.Constants.SUCCESS;

public class LessonRepository {
    final String TAG = LessonRepository.class.getSimpleName();
    Context context;
    LessonListener lessonListener;

    public void setLessonListener(LessonListener lessonListener) {
        this.lessonListener = lessonListener;
    }

    public LessonRepository(Context context) {
        this.context = context;
    }

    public void getLesson(SectionData sectionData){
        AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
        Call<LessonResponse> call = academyApis.getLessonById(sectionData.getId());
        Log.i(TAG,call.request().url().toString());
        call.enqueue(new Callback<LessonResponse>() {
            @Override
            public void onResponse(Call<LessonResponse> call, Response<LessonResponse> response) {
                if (response.isSuccessful()) {
                    LessonResponse lessonResponse = response.body();
                    if (lessonResponse.getCode().equals("200") && lessonResponse.getStatus().equals(SUCCESS)) {
                        lessonListener.onSuccess(lessonResponse.getData(),sectionData);
                    }else  {
                        lessonListener.onSuccess(new ArrayList<>(),sectionData);
                    }
                }else {
                    Utils.showDialog(context,RESPONSE_FAILED+" In Lesson",response.message());
                }
            }
            @Override
            public void onFailure(Call<LessonResponse> call, Throwable t) {
                Utils.showDialog(context,FAILED+" In Lesson",t.getMessage());
            }
        });
    }
}
