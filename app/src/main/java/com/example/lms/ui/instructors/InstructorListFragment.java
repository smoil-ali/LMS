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
import com.example.lms.Model.InstructorData;
import com.example.lms.Model.StudentData;
import com.example.lms.R;
import com.example.lms.ViewModels.InstructorViewModel;
import com.example.lms.ViewModels.StudentViewModel;
import com.example.lms.databinding.FragmentInstructorListBinding;

import java.util.ArrayList;
import java.util.List;

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
        viewModel.getMutableLiveData().observe(requireActivity(), instructorData -> {
            if (instructorData.size() > 0 ){
                dataList.clear();
                dataList.addAll(instructorData);
                adapter.notifyDataSetChanged();
                adapter.setDeleteListener(InstructorListFragment.this);
                binding.instructorProgressbar.setVisibility(View.GONE);
                binding.instructorAlertMessage.setVisibility(View.GONE);
            }else {
                binding.instructorProgressbar.setVisibility(View.GONE);
                binding.instructorAlertMessage.setVisibility(View.VISIBLE);
            }
        });


        viewModel.getErrorMessage().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.instructorAlertMessage.setText(s);
                binding.instructorAlertMessage.setVisibility(View.VISIBLE);
                binding.instructorProgressbar.setVisibility(View.GONE);
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
