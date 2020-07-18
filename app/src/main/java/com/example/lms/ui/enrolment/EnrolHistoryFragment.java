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

import com.example.lms.Factories.CourseFactory;
import com.example.lms.Factories.EnrollHistoryFactory;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.R;
import com.example.lms.ViewModels.CourseViewModel;
import com.example.lms.ViewModels.EnrollHistoryViewModel;
import com.example.lms.databinding.FragmentEnrolhistoryBinding;

import java.time.temporal.TemporalAccessor;

public class EnrolHistoryFragment extends Fragment {


    private static final String TAG = EnrolHistoryFragment.class.getSimpleName();
    FragmentEnrolhistoryBinding binding;
    EnrollHistoryViewModel enrollHistoryViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEnrolhistoryBinding.inflate(inflater,container,false);

        enrollHistoryViewModel= new ViewModelProvider(getActivity(),new EnrollHistoryFactory(getContext(),binding.enrollHistoryProgressbar)).get(EnrollHistoryViewModel.class);
        enrollHistoryViewModel.getHistoryDataMutableLiveData().observe(requireActivity(), new Observer<EnrollmentHistoryData>() {
            @Override
            public void onChanged(EnrollmentHistoryData enrollmentHistoryData) {
                Log.i(TAG,enrollmentHistoryData.getUserName());
                binding.enrollHistoryProgressbar.setVisibility(View.GONE);
            }
        });
        return binding.getRoot();
    }
}
