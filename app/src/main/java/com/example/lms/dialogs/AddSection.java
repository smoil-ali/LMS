package com.example.lms.dialogs;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.SectionResponse;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.AddStudent;
import com.example.lms.databinding.AddSectionBinding;

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

import static com.example.lms.Model.Constants.ACTION;
import static com.example.lms.Model.Constants.ADD;
import static com.example.lms.Model.Constants.EDIT;
import static com.example.lms.Model.Constants.FAILED;
import static com.example.lms.Model.Constants.FIELD_MISSING;
import static com.example.lms.Model.Constants.RESPONSE_FAILED;
import static com.example.lms.Model.Constants.SUCCESS;

public class AddSection extends DialogFragment {

    final String TAG= AddSection.class.getSimpleName();
    AddSectionBinding binding;
    String title,action,id;
    deleteListener deleteListener;

    public void setDeleteListener(com.example.lms.Listener.deleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public AddSection() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddSectionBinding.inflate(inflater,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.submit.setOnClickListener(view -> {
            validate();
        });

        if (action.equals(EDIT)){
            binding.submit.setText("Update Section");
            binding.title.setText(title);
        }

        return binding.getRoot();
    }

    public void validate(){
        if (binding.title.getText().toString().trim().matches("")){
            Utils.showDialog(getContext(),FIELD_MISSING,"Section Name Required!!!!");
        }else {
            binding.submit.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            if (title != null){
                updateSection();
            }else {
                addSections();
            }
        }
    }

    public void addSections(){
        Observable<Response<SectionResponse>> observable = Observable.create(new ObservableOnSubscribe<Response<SectionResponse>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<SectionResponse>> emitter) throws Exception {
                AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
                Call<SectionResponse> call = academyApis.addSection(binding.title.getText().toString(), Constants.COURSE_ID);
                Log.i(TAG,call.request().url().toString());
                call.enqueue(new Callback<SectionResponse>() {
                    @Override
                    public void onResponse(Call<SectionResponse> call, Response<SectionResponse> response) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(Call<SectionResponse> call, Throwable t) {
                        emitter.onError(t);
                    }
                });

            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<SectionResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<SectionResponse> sectionResponseResponse) {
                        if (sectionResponseResponse.isSuccessful()){
                            SectionResponse sectionResponse = sectionResponseResponse.body();
                            if (sectionResponse.getCode().equals("200") && sectionResponse.getStatus().equals(SUCCESS)){
                                dismiss();
                                binding.submit.setVisibility(View.VISIBLE);
                                binding.progressBar.setVisibility(View.GONE);
                                deleteListener.OnDelete(sectionResponse.getStatus(),sectionResponse.getMessage());
                            }else {
                                binding.submit.setVisibility(View.VISIBLE);
                                binding.progressBar.setVisibility(View.GONE);
                                Utils.showDialog(getContext(),sectionResponse.getStatus(),sectionResponse.getMessage());
                            }
                        }else {
                            binding.submit.setVisibility(View.VISIBLE);
                            binding.progressBar.setVisibility(View.GONE);
                            Utils.showDialog(getContext(),RESPONSE_FAILED,sectionResponseResponse.message());
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

    public void updateSection(){
        Observable<Response<SectionResponse>> observable = Observable.create(new ObservableOnSubscribe<Response<SectionResponse>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<SectionResponse>> emitter) throws Exception {
                AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
                Call<SectionResponse> call = academyApis.updateSection(id,binding.title.getText().toString());
                Log.i(TAG,call.request().url().toString());
                call.enqueue(new Callback<SectionResponse>() {
                    @Override
                    public void onResponse(Call<SectionResponse> call, Response<SectionResponse> response) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(Call<SectionResponse> call, Throwable t) {
                        emitter.onError(t);
                    }
                });

            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<SectionResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<SectionResponse> sectionResponseResponse) {
                        if (sectionResponseResponse.isSuccessful()){
                            SectionResponse sectionResponse = sectionResponseResponse.body();
                            if (sectionResponse.getCode().equals("200") && sectionResponse.getStatus().equals(SUCCESS)){
                                binding.submit.setVisibility(View.VISIBLE);
                                binding.progressBar.setVisibility(View.GONE);
                                dismiss();
                                deleteListener.OnDelete(sectionResponse.getStatus(),sectionResponse.getMessage());
                            }else {
                                binding.submit.setVisibility(View.VISIBLE);
                                binding.progressBar.setVisibility(View.GONE);
                                Utils.showDialog(getContext(),sectionResponse.getStatus(),sectionResponse.getMessage());
                            }
                        }else {
                            binding.submit.setVisibility(View.VISIBLE);
                            binding.progressBar.setVisibility(View.GONE);
                            Utils.showDialog(getContext(),RESPONSE_FAILED,sectionResponseResponse.message());
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
