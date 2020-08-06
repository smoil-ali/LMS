package com.example.lms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.lms.Model.CourseData;
import com.example.lms.Model.CourseResponse;
import com.example.lms.Model.UserData;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.databinding.FragmentEnrollStudentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Enroll_Student extends Fragment {



    final String TAG = Enroll_Student.class.getSimpleName();
    final String ROLE =  "student";
    FragmentEnrollStudentBinding binding;
    List<UserData> dataList = new ArrayList<>();
    List<String> stringList = new ArrayList<>();

    List<CourseData> courseData = new ArrayList<>();
    List<String> courseNames = new ArrayList<>();

    int userIndex,courseIndex = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEnrollStudentBinding.inflate(inflater,container,false);
        binding.userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userIndex = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                courseIndex = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.submit.setOnClickListener(view -> {
            if (userIndex>=0 && courseIndex>=0){
                binding.submitProgressBar.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.GONE);
                enrollStudent();
            }else {
                Utils.showDialog(getContext(),"Data Not Selected","Please select student and course!!!");
            }
        });
        getStudentList();
        getCourseList();
        return binding.getRoot();
    }

    public void getStudentList(){
        Observable<Response<StudentResponse>> observable = Observable.create(new ObservableOnSubscribe<Response<StudentResponse>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<StudentResponse>> emitter) throws Exception {
                AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
                Call<StudentResponse> studentResponseCall = academyApis.getStudentList(ROLE);
                Log.i(TAG,studentResponseCall.request().url()+"");
                studentResponseCall.enqueue(new Callback<StudentResponse>() {
                    @Override
                    public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    }
                    @Override
                    public void onFailure(Call<StudentResponse> call, Throwable t) {
                        emitter.onError(t);
                    }
                });
            }
        });

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Response<StudentResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG,"subscribed!!!");
            }

            @Override
            public void onNext(Response<StudentResponse> studentResponseResponse) {
                Log.i(TAG,"On Next()");
                if (studentResponseResponse.isSuccessful()){
                    StudentResponse studentResponse = studentResponseResponse.body();
                    if (studentResponse.getCode().equals("200") && studentResponse.getStatus().equals("success")){
                        dataList = studentResponse.getData();
                        for (UserData data:dataList){
                            stringList.add(data.getFirst_name()+" "+data.getLast_name());
                        }
                        setupUserSpinner();
                    }else {
                        Utils.showDialog(getContext(),studentResponse.getStatus(),studentResponse.getMessage());
                    }
                }else {
                    Utils.showDialog(getContext(),"Response Failed",studentResponseResponse.message());
                }
            }

            @Override
            public void onError(Throwable e) {
                binding.userProgressBar.setVisibility(View.GONE);
                Utils.showDialog(getContext(),"Call Failed",e.getMessage());
            }

            @Override
            public void onComplete() {
                binding.userProgressBar.setVisibility(View.GONE);
                Log.i(TAG,"Complete!!!");
            }
        });

    }

    public void getCourseList(){
        Observable<Response<CourseResponse>> observable = Observable.create(new ObservableOnSubscribe<Response<CourseResponse>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<CourseResponse>> emitter) throws Exception {
                AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
                Call<CourseResponse> courseResponseCall = academyApis.getCourseResponse();
                Log.i(TAG,courseResponseCall.request().url().toString());
                courseResponseCall.enqueue(new Callback<CourseResponse>() {
                    @Override
                    public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(Call<CourseResponse> call, Throwable t) {
                        emitter.onError(t);
                    }
                });
            }
        });

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Response<CourseResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<CourseResponse> courseResponseResponse) {
                if (courseResponseResponse.isSuccessful()){
                    CourseResponse courseResponse = courseResponseResponse.body();
                    if (courseResponse.getCode().equals("200") && courseResponse.getStatus().equals("success")){
                        courseData = courseResponse.getData();
                        for (CourseData courseData:courseData){
                            courseNames.add(courseData.getTitle());
                        }
                        setupCourseSpinner();
                    }else {
                        Utils.showDialog(getContext(),courseResponse.getStatus(),courseResponse.getMessage());
                    }
                }else {
                    Utils.showDialog(getContext(),"Response Failed",courseResponseResponse.message());
                }
            }

            @Override
            public void onError(Throwable e) {
                binding.courseProgressBar.setVisibility(View.GONE);
                Utils.showDialog(getContext(),"Call Failed",e.getMessage());
            }

            @Override
            public void onComplete() {
                binding.courseProgressBar.setVisibility(View.GONE);
                Log.i(TAG,"complete");
            }
        });
    }

    public void setupUserSpinner(){
        Log.i(TAG,stringList.size()+"");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item,stringList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.userSpinner.setAdapter(dataAdapter);
    }

    public void setupCourseSpinner(){
        Log.i(TAG,stringList.size()+"");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item,courseNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.courseSpinner.setAdapter(dataAdapter);
    }

    public void enrollStudent(){
        Observable<Response<StudentResponse>> observable = Observable.create(new ObservableOnSubscribe<Response<StudentResponse>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<StudentResponse>> emitter) throws Exception {
                AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
                Call<StudentResponse> call = academyApis.enrollStudent("insert",dataList.get(userIndex).getId(),courseData.get(courseIndex).getId());
                Log.i(TAG,call.request().url().toString());
                call.enqueue(new Callback<StudentResponse>() {
                    @Override
                    public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(Call<StudentResponse> call, Throwable t) {
                        emitter.onError(t);
                    }
                });

            }
        });

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Response<StudentResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<StudentResponse> studentResponseResponse) {
                if (studentResponseResponse.isSuccessful()){
                    StudentResponse studentResponse = studentResponseResponse.body();
                    if (studentResponse.getCode().equals("200") && studentResponse.getStatus().equals("success")){
                        Utils.showDialog(getContext(),studentResponse.getStatus(),studentResponse.getMessage());
                    }else {
                        Utils.showDialog(getContext(),studentResponse.getStatus(),studentResponse.getMessage());
                    }
                }else {
                    Utils.showDialog(getContext(),"Response Failed",studentResponseResponse.message());
                }
            }

            @Override
            public void onError(Throwable e) {
                binding.submitProgressBar.setVisibility(View.GONE);
                binding.submit.setVisibility(View.VISIBLE);
                Utils.showDialog(getContext(),"Call Failed",e.getMessage());
            }

            @Override
            public void onComplete() {
                binding.submit.setVisibility(View.VISIBLE);
                binding.submitProgressBar.setVisibility(View.GONE);
            }
        });
    }
}