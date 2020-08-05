package com.example.lms.ui.instructors;

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

import com.example.lms.Adapters.InstructorAdapter;
import com.example.lms.Adapters.StudentAdapter;
import com.example.lms.Factories.InstructorFactory;
import com.example.lms.Factories.StudentFactory;
import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.InstructorData;
import com.example.lms.Model.InstructorResponse;
import com.example.lms.Model.StudentData;
import com.example.lms.Model.StudentResponse;
import com.example.lms.R;
import com.example.lms.ViewModels.InstructorViewModel;
import com.example.lms.ViewModels.StudentViewModel;
import com.example.lms.databinding.FragmentInstructorListBinding;
import com.example.lms.ui.students.StudentFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class InstructorListFragment extends Fragment implements deleteListener {

    FragmentInstructorListBinding binding;
    InstructorViewModel viewModel;
    List<InstructorData> dataList = new ArrayList<>();
    InstructorAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInstructorListBinding.inflate(inflater,container,false);
        setUpRecyclerView();
        viewModel= new ViewModelProvider(getActivity(),new InstructorFactory(getContext(),binding.instructorProgressbar)).get(InstructorViewModel.class);
        viewModel.getMutableLiveData().observe(requireActivity(), new Observer<Response<InstructorResponse>>() {
            @Override
            public void onChanged(Response<InstructorResponse> response) {
                if (response.isSuccessful()){
                    InstructorResponse response1 = response.body();
                    if (response1.getCode().equals("200") && response1.getStatus().equals(Constants.SUCCESS)){
                        dataList.clear();
                        dataList.addAll(response1.getData());
                        adapter.notifyDataSetChanged();
                        adapter.setDeleteListener(InstructorListFragment.this);
                        binding.rvInstructor.setVisibility(View.VISIBLE);
                        binding.instructorProgressbar.setVisibility(View.GONE);
                        binding.instructorAlertMessage.setVisibility(View.GONE);
                    }else {

                        binding.rvInstructor.setVisibility(View.GONE);
                        binding.instructorProgressbar.setVisibility(View.GONE);
                        binding.instructorAlertMessage.setVisibility(View.VISIBLE);
                        binding.instructorAlertMessage.setText(response1.getStatus()+" "+response1.getMessage());
                    }
                }else {
                    binding.rvInstructor.setVisibility(View.GONE);
                    binding.instructorProgressbar.setVisibility(View.GONE);
                    binding.instructorAlertMessage.setVisibility(View.VISIBLE);
                    binding.instructorAlertMessage.setText(Constants.RESPONSE_FAILED+" "+response.message());
                }

            }
        });


        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        binding.rvInstructor.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvInstructor.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.rvInstructor.addItemDecoration(dividerItemDecoration);
        binding.rvInstructor.setNestedScrollingEnabled(false);
        adapter=new InstructorAdapter(getContext(),dataList);
        binding.rvInstructor.setAdapter(adapter);
    }

    @Override
    public void OnDelete(String status, String message) {
        viewModel.update(getContext(),binding.instructorProgressbar);
        new AlertDialog.Builder(getContext())
                .setTitle(status)
                .setMessage(message)
                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
    }
}
