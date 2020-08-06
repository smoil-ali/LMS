package com.example.lms.ui.students;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lms.Adapters.StudentAdapter;
import com.example.lms.Factories.StudentFactory;
import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.UserData;
import com.example.lms.Model.StudentResponse;

import com.example.lms.ViewModels.StudentViewModel;
import com.example.lms.databinding.FragmentStudentsBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment implements deleteListener {

    StudentViewModel viewModel;
    FragmentStudentsBinding binding;
    List<UserData> dataList = new ArrayList<>();
    StudentAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStudentsBinding.inflate(inflater,container,false);

        setUpRecyclerView();
        viewModel= new ViewModelProvider(getActivity(),new StudentFactory(getContext(),binding.studentProgressbar)).get(StudentViewModel.class);
        viewModel.getMutableLiveData().observe(requireActivity(), response -> {
            if (response.isSuccessful()){
                StudentResponse response1 = response.body();
                if (response1.getCode().equals("200") && response1.getStatus().equals(Constants.SUCCESS)){
                    dataList.clear();
                    dataList.addAll(response1.getData());
                    adapter.notifyDataSetChanged();
                    adapter.setDeleteListener(StudentFragment.this);
                    binding.rvStudent.setVisibility(View.VISIBLE);
                    binding.studentProgressbar.setVisibility(View.GONE);
                    binding.studentAlertMessage.setVisibility(View.GONE);
                }else {
                    binding.rvStudent.setVisibility(View.GONE);
                    binding.studentProgressbar.setVisibility(View.GONE);
                    binding.studentAlertMessage.setVisibility(View.VISIBLE);
                    binding.studentAlertMessage.setText(response1.getStatus()+" "+response1.getMessage());
                }
            }else {
                binding.rvStudent.setVisibility(View.GONE);
                binding.studentProgressbar.setVisibility(View.GONE);
                binding.studentAlertMessage.setVisibility(View.VISIBLE);
                binding.studentAlertMessage.setText(Constants.RESPONSE_FAILED+" "+response.message());
            }

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
