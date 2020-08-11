package com.example.lms.ui.students;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lms.Adapters.StudentAdapter;
import com.example.lms.Factories.StudentFactory;
import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.UserData;
import com.example.lms.Model.StudentResponse;

import com.example.lms.Model.UserData_EnrollmentHistoryModel;
import com.example.lms.Student_finish;
import com.example.lms.ViewModels.StudentViewModel;
import com.example.lms.databinding.FragmentStudentsBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment implements deleteListener {

    private static final String TAG = StudentFragment.class.getSimpleName();
    StudentViewModel viewModel;
    FragmentStudentsBinding binding;
    List<UserData_EnrollmentHistoryModel> dataList = new ArrayList<>();
    StudentAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStudentsBinding.inflate(inflater,container,false);
        setUpRecyclerView();
        viewModel= new ViewModelProvider(getActivity(),new StudentFactory(getContext(),binding.studentProgressbar)).get(StudentViewModel.class);
        viewModel.getMutableLiveData().observe(requireActivity(), new Observer<List<UserData_EnrollmentHistoryModel>>() {
            @Override
            public void onChanged(List<UserData_EnrollmentHistoryModel> list) {
                Log.i(TAG, "student Fragment");
                dataList.clear();
                dataList.addAll(list);
                adapter.notifyDataSetChanged();
                adapter.setDeleteListener(StudentFragment.this);
                binding.rvStudent.setVisibility(View.VISIBLE);
                binding.swipeRefresher.setRefreshing(false);
                binding.studentProgressbar.setVisibility(View.GONE);
                binding.studentAlertMessage.setVisibility(View.GONE);
            }
        });




        binding.swipeRefresher.setOnRefreshListener(() -> {
            viewModel.update(StudentFragment.this.getContext(), binding.studentProgressbar);
        });


        
        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        binding.rvStudent.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvStudent.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.rvStudent.addItemDecoration(dividerItemDecoration);
        binding.rvStudent.setNestedScrollingEnabled(false);
        adapter=new StudentAdapter(getContext(),dataList,getParentFragmentManager());
        binding.rvStudent.setAdapter(adapter);
    }

    @Override
    public void OnDelete(String status, String message) {
        viewModel.update(getContext(),binding.studentProgressbar);
        new AlertDialog.Builder(getContext())
                .setTitle(status)
                .setMessage(message)
                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
    }
}
