package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.CourseListener;
import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseCountResponse;
import com.example.lms.Model.CourseData;
import com.example.lms.Model.CourseResponse;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.Login;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseRepository {
    Context context;
    String TAG = CourseRepository.class.getSimpleName();
    CourseListener courseListener;
    AcademyApis academyApis ;

    public CourseRepository(Context context, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        this.context = context;
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CourseResponse> courseResponseCall = academyApis.getCourseResponse();
        Log.i(TAG,courseResponseCall.request().url()+"");
        //Getting Courses List///
        courseResponseCall.enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                if (response.isSuccessful()){
                    CourseResponse courseResponse = response.body();
                    if (courseResponse.getCode().equals("200")){
                        courseListener.courseListener(courseResponse.getData(),courseResponse.getMessage());
                    }else {
                        courseListener.errorListener(courseResponse.getMessage());
                    }
                }else {
                    courseListener.errorListener(response.message());
                }

            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                courseListener.errorListener(t.getMessage());
            }
        });
        // Finish Getting Courses List///
    }

    public void getCountCourse(){
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CourseCountResponse> courseCountCall = academyApis.getCourseCount();
        Log.i(TAG,courseCountCall.request().url()+"");
        courseCountCall.enqueue(new Callback<CourseCountResponse>() {
            @Override
            public void onResponse(Call<CourseCountResponse> call, Response<CourseCountResponse> response) {
                if (response.isSuccessful()){
                    CourseCountResponse courseCountResponse = response.body();
                    if (courseCountResponse.getCode().equals("200")){
                        courseListener.courseCountListener(courseCountResponse.getCourse_count());
                    }else {
                        courseListener.errorListener(courseCountResponse.getMessage());
                    }
                }else {
                    courseListener.errorListener(response.message());
                }
            }

            @Override
            public void onFailure(Call<CourseCountResponse> call, Throwable t) {
                courseListener.errorListener(t.getMessage());
            }
        });
    }

    public void setCourseListener(CourseListener courseListener){
        this.courseListener = courseListener;
    }

}
