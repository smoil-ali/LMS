package com.example.lms.ui.enrolment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lms.Adapters.CourserAdapter;
import com.example.lms.Adapters.EnrollHistoryAdapter;
import com.example.lms.Factories.CourseFactory;
import com.example.lms.Factories.EnrollHistoryFactory;
import com.example.lms.Model.EnrollHistoryUserData;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.R;
import com.example.lms.ViewModels.CourseViewModel;
import com.example.lms.ViewModels.EnrollHistoryViewModel;
import com.example.lms.databinding.FragmentEnrolhistoryBinding;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

public class EnrolHistoryFragment extends Fragment {


    private static final String TAG = EnrolHistoryFragment.class.getSimpleName();
    EnrollHistoryAdapter adapter;
    FragmentEnrolhistoryBinding binding;
    EnrollHistoryViewModel enrollHistoryViewModel;
    List<EnrollHistoryUserData> dataList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEnrolhistoryBinding.inflate(inflater,container,false);

        setUpRecyclerView();
        enrollHistoryViewModel= new ViewModelProvider(getActivity(),new EnrollHistoryFactory(getContext(),binding.enrollHistoryProgressbar)).get(EnrollHistoryViewModel.class);
        enrollHistoryViewModel.getHistoryDataMutableLiveData().observe(requireActivity(), new Observer<List<EnrollHistoryUserData>>() {
            @Override
            public void onChanged(List<EnrollHistoryUserData> enrollHistoryUserData) {
                if (enrollHistoryUserData.size() > 0 ){
                    dataList.clear();
                    dataList.addAll(enrollHistoryUserData);
                    adapter.notifyDataSetChanged();
                    binding.enrollHistoryProgressbar.setVisibility(View.GONE);
                    binding.enrollHistoryAlertMessage.setVisibility(View.GONE);
                }else {
                    binding.enrollHistoryProgressbar.setVisibility(View.GONE);
                    binding.enrollHistoryAlertMessage.setVisibility(View.VISIBLE);
                }
            }
        });


        enrollHistoryViewModel.getErrorMessage().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.enrollHistoryAlertMessage.setText(s);
                binding.enrollHistoryAlertMessage.setVisibility(View.VISIBLE);
                binding.enrollHistoryProgressbar.setVisibility(View.GONE);
            }
        });
        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        binding.enrollHistoryRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.enrollHistoryRecyclerview.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.enrollHistoryRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.enrollHistoryRecyclerview.setNestedScrollingEnabled(false);
        adapter=new EnrollHistoryAdapter(getContext(),dataList);
        binding.enrollHistoryRecyclerview.setAdapter(adapter);
    }
}
