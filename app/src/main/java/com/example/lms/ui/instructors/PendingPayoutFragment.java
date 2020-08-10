package com.example.lms.ui.instructors;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lms.Adapters.InstructorPayoutAdapter;
import com.example.lms.Factories.EnrollHistoryFactory;
import com.example.lms.Factories.InstructorPayoutFactory;
import com.example.lms.Model.Instructor;
import com.example.lms.Model.InstructorPayout;
import com.example.lms.Model.PayoutResponse;
import com.example.lms.R;
import com.example.lms.ViewModels.PayoutViewModel;
import com.example.lms.databinding.FragmentPayoutPendingBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class PendingPayoutFragment extends Fragment {

    FragmentPayoutPendingBinding binding;
    PayoutViewModel payoutViewModel;
    List<InstructorPayout> datalist=new ArrayList<>();
    InstructorPayoutAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentPayoutPendingBinding.inflate(inflater,container,false);
        setUpRecyclerView();
        payoutViewModel=new ViewModelProvider(requireActivity(),new InstructorPayoutFactory(getContext(),binding.progressBar)).get(PayoutViewModel.class);
        payoutViewModel.getPayoutDataMutabeLiveData().observe(requireActivity(),payoutResponseResponse -> {
            datalist.clear();

            if (payoutResponseResponse.isSuccessful()){
                if (payoutResponseResponse.body().getCode().equals("200"))
                {
                    Toast.makeText(getContext(), ""+payoutResponseResponse.body().getData().size(), Toast.LENGTH_SHORT).show();
                    datalist.addAll(payoutResponseResponse.body().getData());
                }
                else
                    Toast.makeText(getContext(),payoutResponseResponse.body().getStatus(),Toast.LENGTH_LONG).show();
            }

            binding.progressBar.setVisibility(View.GONE);
        });
        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        binding.rvPending.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new InstructorPayoutAdapter(getContext(),datalist);
        binding.rvPending.setAdapter(adapter);
    }
}
