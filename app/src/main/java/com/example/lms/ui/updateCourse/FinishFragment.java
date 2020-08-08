package com.example.lms.ui.updateCourse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.lms.Model.BasicFragmentModel;
import com.example.lms.Model.Container;
import com.example.lms.Model.CourseUpdateResponse;
import com.example.lms.Model.MediaFragmentModel;
import com.example.lms.Model.PriceFragmentModel;
import com.example.lms.Model.SeoModelClass;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.databinding.FragmentFinishCourseBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinishFragment extends Fragment {

    FragmentFinishCourseBinding binding;
    final String TAG = FinishFragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= FragmentFinishCourseBinding.inflate(inflater,container,false);

        binding.submit.setOnClickListener(view -> validate());
        return binding.getRoot();
    }

    public void validate(){
        if (!Container.getModel().getCategory_id().matches("") && !Container.getModel().getCourseTitle().matches("")){
            submit();
        }else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Error!!!")
                    .setMessage("Category and Course Title must be required.")
                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
        }
    }

    public void submit(){
        AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CourseUpdateResponse> CourseUpdateResponseCall = academyApis.addCourse(Container.getModel().getCourseTitle(),
                Container.getModel().getShortDescription(),Container.getModel().getDescription(),
                Container.getListOfOutcomes(),Container.getModel().getLanguage(),Container.getModel().getLevel(),
                Container.getModel().getIsTopCourse(),Container.getModel().getCategory_id(),Container.getListOfRequirements(),
                Container.getPriceFragmentModel().getIfFreeCourse(),Container.getPriceFragmentModel().getIfDiscount(),
                Container.getPriceFragmentModel().getCoursePrice(),Container.getPriceFragmentModel().getDiscountPrice(),
                Container.getMediaFragmentModel().getProvider(),Container.getMediaFragmentModel().getUrl(),
                Container.getSeoModelClass().getMeta_list(),Container.getSeoModelClass().getMetaDiscription());

        Log.i(TAG,CourseUpdateResponseCall.request().url()+"");

        CourseUpdateResponseCall.enqueue(new Callback<CourseUpdateResponse>() {
            @Override
            public void onResponse(Call<CourseUpdateResponse> call, Response<CourseUpdateResponse> response) {
                if (response.isSuccessful()){
                    CourseUpdateResponse CourseUpdateResponse = response.body();
                    if (CourseUpdateResponse.getCode().equals("200")){
                        new AlertDialog.Builder(getContext())
                                .setTitle(CourseUpdateResponse.getStatus())
                                .setMessage(CourseUpdateResponse.getMessage())
                                .setPositiveButton("OK",((dialog, which) -> getActivity().finish())).show();
                        makeEmpty();
                    }else {
                        new AlertDialog.Builder(getContext())
                                .setTitle(CourseUpdateResponse.getStatus())
                                .setMessage(CourseUpdateResponse.getMessage())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                    }
                }else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Failed")
                            .setMessage(response.message())
                            .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                }

            }

            @Override
            public void onFailure(Call<CourseUpdateResponse> call, Throwable t) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Failed")
                        .setMessage(t.getMessage())
                        .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
            }
        });
    }

    public void makeEmpty(){
        Container.setModel(new BasicFragmentModel());
        Container.setListOfRequirements(new ArrayList<>());
        Container.setListOfOutcomes(new ArrayList<>());
        Container.setSeoModelClass(new SeoModelClass());
        Container.setMediaFragmentModel(new MediaFragmentModel());
        Container.setPriceFragmentModel(new PriceFragmentModel());
    }
}
