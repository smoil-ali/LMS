package com.example.lms.dialogs;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.lms.Model.AddLessonModel;
import com.example.lms.Model.Constants;
import com.example.lms.Model.LessonResponse;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.AddStudent;
import com.example.lms.databinding.AddLessonBinding;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

import static com.example.lms.Model.Constants.ADD;
import static com.example.lms.Model.Constants.COURSE_ID;
import static com.example.lms.Model.Constants.FAILED;
import static com.example.lms.Model.Constants.FIELD_MISSING;
import static com.example.lms.Model.Constants.RESPONSE_FAILED;
import static com.example.lms.Model.Constants.SUCCESS;

public class AddLesson extends DialogFragment {

    final String TAG = AddLesson.class.getSimpleName();
    AddLessonBinding binding;
    List<String> sectionNames = new ArrayList<>();
    int sectionIndex,lessonTypeIndex,lessonProviderIndex = -1;
    String action,id,summary,title;

    public void setAction(String action) {
        this.action = action;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AddLesson() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddLessonBinding.inflate(inflater,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        for (SectionData sectionData:Constants.sectionData){
            sectionNames.add(sectionData.getTitle());
        }
        setupSectionSpinner();
        if (action.equals(ADD)){
            setupLessonTypeSpinner();
            setupLessonProviderSpinner();

            binding.spinnerSectionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    sectionIndex = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            binding.spinnerLessonType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    lessonTypeIndex = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            binding.spinnerLessonProviderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    lessonProviderIndex = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            binding.submit.setOnClickListener(view -> {
                Validate();
            });
        }else {
            binding.title.setText(title);
            binding.summary.setText(summary);
            binding.spinnerLessonType.setVisibility(View.GONE);
            binding.spinnerLessonProviderType.setVisibility(View.GONE);
            binding.videoUrl.setVisibility(View.GONE);
            binding.duration.setVisibility(View.GONE);
            binding.mobileVideoUrl.setVisibility(View.GONE);
            binding.durationMobile.setVisibility(View.GONE);
            binding.submit.setText("Update Lesson");
            binding.submit.setOnClickListener(view -> updateValidate());
        }


        return binding.getRoot();
    }


    public void Validate(){
        if (binding.title.getText().toString().trim().matches("")){
            Utils.showDialog(getContext(),FIELD_MISSING,"Lesson Name Needed!!!");
        }else if (sectionIndex == -1){
            Utils.showDialog(getContext(),FIELD_MISSING,"Please Select Section!!!");
        }else if (lessonTypeIndex == -1){
            Utils.showDialog(getContext(),FIELD_MISSING,"Please Select Lesson Type");
        }else if (lessonProviderIndex == -1){
            Utils.showDialog(getContext(),FIELD_MISSING,"Please Select Lesson Provider!!!");
        }else if (binding.videoUrl.getText().toString().trim().matches("")){
            Utils.showDialog(getContext(),FIELD_MISSING,"Web Video Url Needed");
        }else if (binding.duration.getText().toString().trim().matches("")){
            Utils.showDialog(getContext(),FIELD_MISSING,"Web Duration Needed!!!");
        }else if (binding.durationMobile.getText().toString().trim().matches("")){
            Utils.showDialog(getContext(),FIELD_MISSING,"Mobile Duration Needed!!!");
        }else if (binding.mobileVideoUrl.getText().toString().trim().matches("")){
            Utils.showDialog(getContext(),FIELD_MISSING,"Mobile Video Url Needed!!!");
        }else if (binding.summary.getText().toString().trim().matches("")){
            Utils.showDialog(getContext(),FIELD_MISSING,"Summary is Needed");
        }else {
            binding.submit.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            AddLessonModel model = new AddLessonModel();
            model.setCourse_id(COURSE_ID);
            model.setSection_id(Constants.sectionData.get(sectionIndex).getId());
            model.setProvider_type(Constants.listOfProvider.get(lessonProviderIndex));
            model.setLesson_provider(Constants.listOfLessonsType.get(lessonTypeIndex));
            model.setWeb_video_url(binding.videoUrl.getText().toString());
            model.setWeb_duration(binding.duration.getText().toString());
            model.setMob_video_url(binding.mobileVideoUrl.getText().toString());
            model.setMob_duration(binding.durationMobile.getText().toString());
            model.setSummary(binding.summary.getText().toString());
            model.setTitle(binding.title.getText().toString());
            AddLesson(model);
        }
    }

    public void updateValidate(){
        if (binding.title.getText().toString().trim().matches("")){
            Utils.showDialog(getContext(),FIELD_MISSING,"Lesson Name Needed!!!");
        }else if (sectionIndex == -1){
            Utils.showDialog(getContext(),FIELD_MISSING,"Please Select Section!!!");
        }else if (binding.summary.getText().toString().trim().matches("")){
            Utils.showDialog(getContext(),FIELD_MISSING,"Summary is Needed");
        }else {
            AddLessonModel model = new AddLessonModel();
            model.setTitle(binding.title.getText().toString());
            model.setSection_id(Constants.sectionData.get(sectionIndex).getId());
            model.setSummary(binding.summary.getText().toString());
            model.setCourse_id(COURSE_ID);
            updateLesson(model);
        }
    }

    public void AddLesson(AddLessonModel model){
        Observable<Response<LessonResponse>> observable = Observable.create(new ObservableOnSubscribe<Response<LessonResponse>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<LessonResponse>> emitter) throws Exception {
                AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
                Call<LessonResponse> call = academyApis.addLesson(model.getTitle(),model.getWeb_duration(),model.getCourse_id(),
                        model.getSection_id(),model.getWeb_video_url(),model.getLesson_provider(),model.getProvider_type(),model.getSummary(),
                        "",model.getMob_video_url(),model.getMob_duration(),"");
                Log.i(TAG,call.request().url().toString());
                call.enqueue(new Callback<LessonResponse>() {
                    @Override
                    public void onResponse(Call<LessonResponse> call, Response<LessonResponse> response) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(Call<LessonResponse> call, Throwable t) {
                        emitter.onError(t);
                    }
                });

            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<LessonResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<LessonResponse> responseResponse) {
                        if (responseResponse.isSuccessful()){
                            LessonResponse LessonResponse = responseResponse.body();
                            if (LessonResponse.getCode().equals("200") && LessonResponse.getStatus().equals(SUCCESS)){
                                binding.submit.setVisibility(View.VISIBLE);
                                binding.progressBar.setVisibility(View.GONE);
                                Utils.showDialog(getContext(),LessonResponse.getStatus(),LessonResponse.getMessage());
                            }else {
                                binding.submit.setVisibility(View.VISIBLE);
                                binding.progressBar.setVisibility(View.GONE);
                                Utils.showDialog(getContext(),LessonResponse.getStatus(),LessonResponse.getMessage());
                            }
                        }else {
                            binding.submit.setVisibility(View.VISIBLE);
                            binding.progressBar.setVisibility(View.GONE);
                            Utils.showDialog(getContext(),RESPONSE_FAILED,responseResponse.message());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        binding.submit.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);
                        Utils.showDialog(getContext(),FAILED,e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateLesson(AddLessonModel model){
        Observable<Response<LessonResponse>> observable = Observable.create(new ObservableOnSubscribe<Response<LessonResponse>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<LessonResponse>> emitter) throws Exception {
                AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
                Call<LessonResponse> call = academyApis.updateLesson(id,model.getTitle(),model.getCourse_id(),model.getSection_id(),model.getSummary());
                Log.i(TAG,call.request().url().toString());
                call.enqueue(new Callback<LessonResponse>() {
                    @Override
                    public void onResponse(Call<LessonResponse> call, Response<LessonResponse> response) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(Call<LessonResponse> call, Throwable t) {
                        emitter.onError(t);
                    }
                });

            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<LessonResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<LessonResponse> responseResponse) {
                        if (responseResponse.isSuccessful()){
                            LessonResponse LessonResponse = responseResponse.body();
                            if (LessonResponse.getCode().equals("200") && LessonResponse.getStatus().equals(SUCCESS)){
                                binding.submit.setVisibility(View.VISIBLE);
                                binding.progressBar.setVisibility(View.GONE);
                                Utils.showDialog(getContext(),LessonResponse.getStatus(),LessonResponse.getMessage());
                            }else {
                                binding.submit.setVisibility(View.VISIBLE);
                                binding.progressBar.setVisibility(View.GONE);
                                Utils.showDialog(getContext(),LessonResponse.getStatus(),LessonResponse.getMessage());
                            }
                        }else {
                            binding.submit.setVisibility(View.VISIBLE);
                            binding.progressBar.setVisibility(View.GONE);
                            Utils.showDialog(getContext(),RESPONSE_FAILED,responseResponse.message());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        binding.submit.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);
                        Utils.showDialog(getContext(),FAILED,e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    public void setupLessonTypeSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Constants.listOfLessonsType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerLessonType.setAdapter(dataAdapter);
    }

    public void setupSectionSpinner(){

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sectionNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSectionType.setAdapter(dataAdapter);
    }

    public void setupLessonProviderSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Constants.listOfProvider);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerLessonProviderType.setAdapter(dataAdapter);
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();

        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        // Set the width of the dialog proportional to 90% of the screen width
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        // Call super onResume after sizing
        super.onResume();
    }


}
