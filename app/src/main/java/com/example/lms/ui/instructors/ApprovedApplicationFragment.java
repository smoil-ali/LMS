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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lms.Adapters.ApprovedApplicationAdapter;
import com.example.lms.Factories.ApplicationFactory;
import com.example.lms.Model.ApprovedApplication;
import com.example.lms.R;
import com.example.lms.ViewModels.ApplicationViewModel;
import com.example.lms.databinding.FragmentApprovedApplicationBinding;

import java.util.ArrayList;

public class ApprovedApplicationFragment extends Fragment {

    FragmentApprovedApplicationBinding binding;
    ApplicationViewModel applicationViewModel;
    ApprovedApplicationAdapter adapter;
    ArrayList<ApprovedApplication> approvedApplicationArrayList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentApprovedApplicationBinding.inflate(inflater,container,false);
        setUpRecyclerView();
        applicationViewModel=new ViewModelProvider(requireActivity(),new ApplicationFactory(binding.progressBar,1)).get(ApplicationViewModel.class);
        applicationViewModel.getApplicationMutableLiveData().observe(requireActivity(),approvedApplications -> {
            if (approvedApplications.size()>0){
                approvedApplicationArrayList.clear();
                approvedApplicationArrayList.addAll(approvedApplications);
                adapter.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
            }else{
                new AlertDialog.Builder(getContext())
                        .setTitle("Data Not Found")
                        .setMessage("data not found")
                        .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
            }
        });

        applicationViewModel.errorMessage.observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.progressBar.setVisibility(View.GONE);
                new AlertDialog.Builder(getContext())
                        .setTitle("Data Not Found")
                        .setMessage(s)
                        .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
            }
        });
        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        binding.rvApplication.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new ApprovedApplicationAdapter(getContext(),approvedApplicationArrayList,0);
        binding.rvApplication.setAdapter(adapter);
    }
}
