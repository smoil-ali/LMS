package com.example.lms.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.Factories.CourseFactory;
import com.example.lms.Factories.ProfileFactory;
import com.example.lms.Model.LoginResponse;
import com.example.lms.Model.Utils;
import com.example.lms.Model.data;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.ViewModels.CourseViewModel;
import com.example.lms.ViewModels.ProfileViewModel;
import com.example.lms.databinding.FragmentManageProfileBinding;

import java.time.temporal.TemporalAccessor;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ManageProfileFragment extends Fragment {

    private static final String TAG = ManageProfileFragment.class.getSimpleName();
    FragmentManageProfileBinding binding;
    ProfileViewModel viewModel;
    String id;
    data Tempdata;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentManageProfileBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(getActivity(),new ProfileFactory(getContext())).get(ProfileViewModel.class);
        viewModel.getMutableLiveData().observe(getActivity(), new Observer<data>() {
            @Override
            public void onChanged(data data) {
                Tempdata = data;
                setData(data);
            }
        });

        binding.materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        return binding.getRoot();
    }


    public void setData(data data){
        binding.firstName.setText(data.getUser().getFirst_name());
        binding.lastName.setText(data.getUser().getLast_name());
        binding.email.setText(data.getUser().getEmail());
        binding.facebookLink.setText(data.getUser().getFacebook());
        binding.twitterLink.setText(data.getUser().getTwitter());
        binding.linkedinLink.setText(data.getUser().getLinkedin());
        binding.title.setText(data.getUser().getTitle());
        binding.biography.setText(data.getUser().getBiography());
        id = data.getUser().getId();
        Log.i(TAG,id);
    }

    public void validate(){
        if (binding.firstName.getText().toString().trim().matches("")){
            binding.firstName.setError("Field Empty");
        }else if (binding.lastName.getText().toString().trim().matches("")){
            binding.lastName.setError("Field Empty");
        }else if (binding.email.getText().toString().trim().matches("")){
            binding.email.setError("Field Empty");
        }else if (binding.twitterLink.getText().toString().trim().matches("")){
            binding.twitterLink.setError("Field Empty");
        }else if (binding.facebookLink.getText().toString().trim().matches("")){
            binding.facebookLink.setError("Field Empty");
        }else if (binding.linkedinLink.getText().toString().trim().matches("")){
            binding.linkedinLink.setError("Field Empty");
        }else if (binding.biography.getText().toString().trim().matches("")){
            binding.biography.setError("Field Empty");
        }else if (binding.title.getText().toString().trim().matches("")){
            binding.title.setError("Field Empty");
        }else {
            updateProfile();
        }
    }

    private void updateProfile() {
        AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
        String f_name = binding.firstName.getText().toString();
        String L_name = binding.lastName.getText().toString();
        String email = binding.email.getText().toString();
        String facebook = binding.facebookLink.getText().toString();
        String twitter = binding.twitterLink.getText().toString();
        String linkedin = binding.linkedinLink.getText().toString();
        String title  = binding.title.getText().toString();
        String bio = binding.biography.getText().toString();
        Observable<LoginResponse> observable = academyApis.updateProfile(binding.firstName.getText().toString(),
                binding.lastName.getText().toString(),binding.email.getText().toString(),binding.facebookLink.getText().toString(),
                binding.twitterLink.getText().toString(),binding.title.getText().toString(),binding.linkedinLink.getText().toString()
                ,binding.biography.getText().toString(),id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    binding.profileProgressBar.setVisibility(View.GONE);
                    binding.materialButton.setVisibility(View.VISIBLE);
                })
                .subscribe(new io.reactivex.Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG,"subscribed");
                        binding.profileProgressBar.setVisibility(View.VISIBLE);
                        binding.materialButton.setVisibility(View.GONE);
                    }
                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        if (loginResponse.getCode().equals("200") && loginResponse.getStatus().equals("success")){
                            new AlertDialog.Builder(getContext())
                                    .setTitle(loginResponse.getStatus())
                                    .setMessage(loginResponse.getMessage())
                                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                            Tempdata.getUser().setFirst_name(f_name);
                            Tempdata.getUser().setLast_name(L_name);
                            Tempdata.getUser().setEmail(email);
                            Tempdata.getUser().setFacebook(facebook);
                            Tempdata.getUser().setTwitter(twitter);
                            Tempdata.getUser().setLinkedin(linkedin);
                            Tempdata.getUser().setTitle(title);
                            Tempdata.getUser().setBiography(bio);
                            Utils.setProfileData(Tempdata,getContext(),false);
                        }else {
                            new AlertDialog.Builder(getContext())
                                    .setTitle(loginResponse.getStatus())
                                    .setMessage(loginResponse.getMessage())
                                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Error")
                                .setMessage(e.getMessage())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"Complete");
                    }
                });
    }
}
