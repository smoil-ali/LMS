package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.CourseListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseCountResponse;
import com.example.lms.Model.CourseData;
import com.example.lms.Model.CourseResponse;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.Login;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseRepository {
    String TAG = CourseRepository.class.getSimpleName();
    CourseListener courseListener;
    AcademyApis academyApis ;

    public CourseRepository(Context context, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CourseResponse> courseResponseCall = academyApis.getCourseResponse();
        Log.i(TAG,courseResponseCall.request().url()+"");
        //Getting Courses List///
        courseResponseCall.enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                courseListener.courseListener(response);
            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                Utils.showDialog(context, Constants.FAILED,t.getMessage());
            }
        });
    }

    public void getCountCourse(Context context){
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CourseCountResponse> courseCountCall = academyApis.getCourseCount();
        Log.i(TAG,courseCountCall.request().url()+"");
        courseCountCall.enqueue(new Callback<CourseCountResponse>() {
            @Override
            public void onResponse(Call<CourseCountResponse> call, Response<CourseCountResponse> response) {
                courseListener.courseCountListener(response);
            }

            @Override
            public void onFailure(Call<CourseCountResponse> call, Throwable t) {
                Utils.showDialog(context, Constants.FAILED,t.getMessage());
            }
        });
    }

    public void setCourseListener(CourseListener courseListener){
        this.courseListener = courseListener;
    }

}
