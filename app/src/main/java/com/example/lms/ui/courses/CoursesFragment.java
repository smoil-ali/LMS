package com.example.lms.ui.courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lms.Adapters.CourseAdapter;
import com.example.lms.Factories.CourseFactory;
import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.CourseCountResponse;
import com.example.lms.Model.CourseData;
import com.example.lms.Model.CourseResponse;
import com.example.lms.Model.Utils;
import com.example.lms.ViewModels.CourseViewModel;
import com.example.lms.databinding.FragmentCoursesBinding;

import java.util.ArrayList;

import retrofit2.Response;

public class CoursesFragment extends Fragment implements deleteListener {

    FragmentCoursesBinding binding;
    CourseAdapter courseAdapter;
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
                        courseAdapter.notifyDataSetChanged();
                        courseAdapter.setDeleteListener(CoursesFragment.this);
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

        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                courseViewModel.update(getContext(),binding.courseProgressBar);
                binding.swipeToRefresh.setRefreshing(false);
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
        courseAdapter =new CourseAdapter(getContext(),courseDataArrayList,getParentFragmentManager());
        binding.rvCourses.setAdapter(courseAdapter);
    }

    @Override
    public void OnDelete(String status, String message) {
        courseViewModel.update(getContext(),binding.courseProgressBar);
        Utils.showDialog(getContext(),status,message);
    }
}
