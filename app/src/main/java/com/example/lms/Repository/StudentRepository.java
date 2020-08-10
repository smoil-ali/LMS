package com.example.lms.Repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lms.Listener.EnrollListener;
import com.example.lms.Listener.EnrollmentCourseListener;
import com.example.lms.Listener.StudentListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.EnrollHistoryUserData;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.Section_LessonModel;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Model.UserData;
import com.example.lms.Model.UserData_EnrollmentHistoryModel;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.SUCCESS;

public class StudentRepository implements EnrollmentCourseListener {
    Context context;
    String TAG = StudentRepository.class.getSimpleName();
    StudentListener  studentListener;
    EnrollCourseRepository repository;
    AcademyApis academyApis ;
    List<UserData_EnrollmentHistoryModel> list;
    int listSize = 0;

    public StudentRepository(Context context, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        this.context = context;
        repository = new EnrollCourseRepository(context);
        repository.setListener(this);
        list = new ArrayList<>();
        academyApis = RetrofitService.createService(AcademyApis.class);
        Call<StudentResponse> StudentResponseCall = academyApis.getStudentList("student");
        Log.i(TAG,StudentResponseCall.request().url()+"");
        StudentResponseCall.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equals("200") && response.body().getStatus().equals(SUCCESS)){
                        listSize = response.body().getData().size();
                        for (UserData userData:response.body().getData()){
                            repository.getEnrollCourse(userData);
                        }
                    }else {
                        Utils.showDialog(context,response.body().getStatus()+" In Student Repo",response.body().getMessage());
                    }
                }else {
                    Utils.showDialog(context, Constants.RESPONSE_FAILED+" In Student Repo",response.message());
                }

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

    @Override
    public void onSuccess(List<EnrollHistoryUserData> list, UserData userData) {
        UserData_EnrollmentHistoryModel model = new UserData_EnrollmentHistoryModel();
        model.setUserData(userData);
        model.setList(list);
        this.list.add(model);
        if (listSize == this.list.size()){
            studentListener.onSuccess(this.list);
        }
    }
}
