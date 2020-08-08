package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.EnrollListener;
import com.example.lms.Listener.StudentListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRepository {
    Context context;
    String TAG = StudentRepository.class.getSimpleName();
    StudentListener  studentListener;
    AcademyApis academyApis ;

    public StudentRepository(Context context, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        this.context = context;
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<StudentResponse> StudentResponseCall = academyApis.getStudentList("student");
        Log.i(TAG,StudentResponseCall.request().url()+"");
        StudentResponseCall.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                studentListener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable t) {
                Log.i(TAG,"in response failed");
                Utils.showDialog(context, Constants.FAILED,t.getMessage());
            }
        });
    }

    public void setStudentListener(StudentListener studentListener){
        this.studentListener = studentListener;
    }
}
