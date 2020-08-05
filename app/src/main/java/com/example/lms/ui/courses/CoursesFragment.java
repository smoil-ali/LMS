package com.example.lms.ui.courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lms.Adapters.CourserAdapter;
import com.example.lms.Factories.CourseFactory;
import com.example.lms.Model.Constants;
import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseCountResponse;
import com.example.lms.Model.CourseData;
import com.example.lms.Model.CourseResponse;
import com.example.lms.Model.InstructorResponse;
import com.example.lms.Model.Utils;
import com.example.lms.R;
import com.example.lms.ViewModels.CourseViewModel;
import com.example.lms.activity.Login;
import com.example.lms.databinding.FragmentCoursesBinding;
import com.example.lms.ui.instructors.InstructorListFragment;

import java.util.ArrayList;

import retrofit2.Response;

public class CoursesFragment extends Fragment {

    FragmentCoursesBinding binding;
    CourserAdapter courserAdapter;
    ArrayList<CourseData> courseDataArrayList=new ArrayList<>();
    CourseViewModel courseViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCoursesBinding.inflate(inflater,container,false);
        setUpRecyclerView();
        courseViewModel = new ViewModelProvider(getActivity(),new CourseFactory(getContext(),binding.courseProgressBar)).get(CourseViewModel.class);
        courseViewModel.getArrayListMutableLiveData().observe(requireActivity(), new Observer<Response<CourseResponse>>() {
            @Override
            public void onChanged(Response<CourseResponse> response) {
                if (response.isSuccessful()){
                    CourseResponse response1 = response.body();
                    if (response1.getCode().equals("200") && response1.getStatus().equals(Constants.SUCCESS)){
                        courseDataArrayList.clear();
                        courseDataArrayList.addAll(response1.getData());
                        courserAdapter.notifyDataSetChanged();
                        binding.courseProgressBar.setVisibility(View.GONE);
                    }else {
                        Utils.showDialog(getContext(),response1.getStatus(),response1.getMessage());
                    }
                }else {
                    Utils.showDialog(getContext(),Constants.RESPONSE_FAILED,response.message());
                }

            }
        });

        courseViewModel.getCourseCountMutableLiveData().observe(requireActivity(), new Observer<Response<CourseCountResponse>>() {
            @Override
            public void onChanged(Response<CourseCountResponse> response) {
                if (response.isSuccessful()){
                    CourseCountResponse response1 = response.body();
                    if (response1.getCode().equals("200") && response1.getStatus().equals(Constants.SUCCESS)){
                        binding.countActiveCourses.setText(response1.getCourse_count().getActive());
                        binding.countFreeCourses.setText(response1.getCourse_count().getFree());
                        binding.countPaidCourses.setText(response1.getCourse_count().getPaid());
                        binding.countPendingCourses.setText(response1.getCourse_count().getPending());
                    }else {
                        Utils.showDialog(getContext(),response1.getStatus(),response1.getMessage());
                    }
                }else {
                    Utils.showDialog(getContext(),Constants.RESPONSE_FAILED,response.message());
                }
            }
        });

        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        binding.rvCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvCourses.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.rvCourses.addItemDecoration(dividerItemDecoration);
     //   binding.rvCourses.setNestedScrollingEnabled(false);
        courserAdapter=new CourserAdapter(getContext(),courseDataArrayList);
        binding.rvCourses.setAdapter(courserAdapter);
    }
}
