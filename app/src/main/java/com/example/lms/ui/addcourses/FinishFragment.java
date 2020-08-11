package com.example.lms.ui.addcourses;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.lms.Model.BasicFragmentModel;
import com.example.lms.Model.Constants;
import com.example.lms.Model.Container;
import com.example.lms.Model.CourseUpdateResponse;
import com.example.lms.Model.CourseUpdateResponse;
import com.example.lms.Model.MediaFragmentModel;
import com.example.lms.Model.PriceFragmentModel;
import com.example.lms.Model.SeoModelClass;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.AddCategory;
import com.example.lms.databinding.FragmentFinishCourseBinding;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.SUCCESS;

public class FinishFragment extends Fragment {

    FragmentFinishCourseBinding binding;
    final String TAG = FinishFragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= FragmentFinishCourseBinding.inflate(inflater,container,false);
        if (!getActivity().getClass().getSimpleName().equals(Constants.AddCourse)){
            binding.submit.setText("Update Course");
        }
        binding.submit.setOnClickListener(view -> validate());
        return binding.getRoot();
    }

    public void validate(){
        if (!Container.getModel().getCategory_id().matches("") && !Container.getModel().getCourseTitle().matches("")){
            if (!getActivity().getClass().getSimpleName().equals(Constants.AddCourse)){
                updateCourse();
            }else {
                submit();
            }
        }else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Error!!!")
                    .setMessage("Category and Course Title must be required.")
                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
        }
    }

    public void submit(){
        binding.submit.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
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
                    if (CourseUpdateResponse.getCode().equals("200") && CourseUpdateResponse.getStatus().equals(SUCCESS)){
                        binding.submit.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);
                        new AlertDialog.Builder(getContext())
                                .setTitle(CourseUpdateResponse.getStatus())
                                .setMessage(CourseUpdateResponse.getMessage())
                                .setPositiveButton("OK",((dialog, which) -> getActivity().finish())).show();
                        makeEmpty();
                    }else {
                        binding.submit.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);
                        new AlertDialog.Builder(getContext())
                                .setTitle(CourseUpdateResponse.getStatus())
                                .setMessage(CourseUpdateResponse.getMessage())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                    }
                }else {
                    binding.submit.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                    new AlertDialog.Builder(getContext())
                            .setTitle("Failed")
                            .setMessage(response.message())
                            .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                }

            }

            @Override
            public void onFailure(Call<CourseUpdateResponse> call, Throwable t) {
                binding.submit.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                new AlertDialog.Builder(getContext())
                        .setTitle("Failed")
                        .setMessage(t.getMessage())
                        .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
            }
        });
    }

    public void updateCourse(){
        binding.submit.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
        AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
        Call<CourseUpdateResponse> CourseUpdateResponseCall = academyApis.updateCourse(Constants.COURSE_ID,Container.getModel().getCourseTitle(),
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
                    if (CourseUpdateResponse.getCode().equals("200") && CourseUpdateResponse.getStatus().equals(SUCCESS)){
                        binding.submit.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);
                        new AlertDialog.Builder(getContext())
                                .setTitle(CourseUpdateResponse.getStatus())
                                .setMessage(CourseUpdateResponse.getMessage())
                                .setPositiveButton("OK",((dialog, which) -> getActivity().finish())).show();
                        makeEmpty();
                    }else {
                        binding.submit.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);
                        new AlertDialog.Builder(getContext())
                                .setTitle(CourseUpdateResponse.getStatus())
                                .setMessage(CourseUpdateResponse.getMessage())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                    }
                }else {
                    binding.submit.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                    new AlertDialog.Builder(getContext())
                            .setTitle("Failed")
                            .setMessage(response.message())
                            .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                }

            }

            @Override
            public void onFailure(Call<CourseUpdateResponse> call, Throwable t) {
                binding.submit.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                new AlertDialog.Builder(getContext())
                        .setTitle("Failed")
                        .setMessage(t.getMessage())
                        .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
            }
        });
    }

    public void makeEmpty(){
        Container.setModel(new BasicFragmentModel());
        Container.setListOfRequirements("");
        Container.setListOfOutcomes("");
        Container.setSeoModelClass(new SeoModelClass());
        Container.setMediaFragmentModel(new MediaFragmentModel());
        Container.setPriceFragmentModel(new PriceFragmentModel());
    }
}
