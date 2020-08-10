package com.example.lms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.InstructorPayout;
import com.example.lms.databinding.InstructorItemBinding;
import com.example.lms.databinding.InstructorPayoutItemBinding;

import java.util.ArrayList;
import java.util.List;

public class InstructorPayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<InstructorPayout> payoutList;

    public InstructorPayoutAdapter(Context context, List<InstructorPayout> payoutList) {
        this.context = context;
        this.payoutList = payoutList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());

        InstructorPayoutItemBinding binding= InstructorPayoutItemBinding.inflate(layoutInflater,parent,false);
        return new InstructorPayoutViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InstructorPayoutViewHolder){
            ((InstructorPayoutViewHolder)(holder)).bindView(payoutList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return payoutList.size();
    }

    public class InstructorPayoutViewHolder extends RecyclerView.ViewHolder{
        InstructorPayoutItemBinding binding;

        public InstructorPayoutViewHolder(InstructorPayoutItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void bindView(InstructorPayout instructorPayout){
            binding.courseInstructor.setText(instructorPayout.getUser_name());
            binding.payoutAmount.setText(instructorPayout.getAmount());
            binding.payoutType.setText(instructorPayout.getPayment_type());
            binding.payoutDate.setText(instructorPayout.getDate_added());
        }
    }
}
