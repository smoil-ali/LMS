package com.example.lms.ui.instructors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lms.Adapters.ApprovedApplicationAdapter;
import com.example.lms.Factories.PendingApplicationFactory;
import com.example.lms.Model.ApprovedApplication;
import com.example.lms.R;
import com.example.lms.ViewModels.PendingApplicationViewModel;
import com.example.lms.databinding.FragmentPendingApplicationBinding;

import java.util.ArrayList;
import java.util.List;

public class PendingApplicationFragment extends Fragment {

    FragmentPendingApplicationBinding binding;
    ApprovedApplicationAdapter adapter;
    ArrayList<ApprovedApplication> pendingList=new ArrayList<>();
    PendingApplicationViewModel applicationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentPendingApplicationBinding.inflate(inflater,container,false);
        setUpRecyclerView();
        applicationViewModel=new ViewModelProvider(requireActivity(),new PendingApplicationFactory(binding.progressBar,0)).get(PendingApplicationViewModel.class);
        applicationViewModel.getApplicationMutableLiveData().observe(requireActivity(),approvedApplications -> {
            if (approvedApplications != null){
                if (approvedApplications.size() > 0) {
                    pendingList.clear();
                    pendingList.addAll(approvedApplications);
                    adapter.notifyDataSetChanged();
                    binding.progressBar.setVisibility(View.GONE);
                }

        }else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Data Not Found")
                        .setMessage("data not found")
                        .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                binding.progressBar.setVisibility(View.GONE);
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
        binding.rvPending.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new ApprovedApplicationAdapter(getContext(),pendingList,0);
        binding.rvPending.setAdapter(adapter);
    }
}
