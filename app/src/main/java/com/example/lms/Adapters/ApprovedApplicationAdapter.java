package com.example.lms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.ApprovedApplication;
import com.example.lms.R;
import com.example.lms.databinding.ApplicationsItemBinding;

import java.util.List;

public class ApprovedApplicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<ApprovedApplication> approvedApplicationsList;
    int viewType;
    public static final int APPROVED=1;
    public static final int pending=0;


    public ApprovedApplicationAdapter(Context context, List<ApprovedApplication> approvedApplicationsList, int viewType) {
        this.context = context;
        this.approvedApplicationsList = approvedApplicationsList;
        this.viewType = viewType;
        System.out.println(approvedApplicationsList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        ApplicationsItemBinding binding=ApplicationsItemBinding.inflate(layoutInflater,parent,false);
        return new ApprovedApplicationViewHoler(binding);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ApprovedApplicationViewHoler)holder).bindView(approvedApplicationsList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return approvedApplicationsList.size();
    }

    public class ApprovedApplicationViewHoler extends RecyclerView.ViewHolder{
            ApplicationsItemBinding binding;
            public ApprovedApplicationViewHoler(ApplicationsItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        public void bindView(ApprovedApplication approvedApplication,int position){
         binding.appName.setText(approvedApplication.getUser_name());
         binding.detail.setText(approvedApplication.getMessage());
         if (approvedApplication.getStatus().equals("1"))
             binding.status.setText("Active");
         else if (approvedApplication.getStatus().equals("0")){
             binding.status.setText("Pending");
             binding.status.setBackground(ContextCompat.getDrawable(context,R.drawable.status_de_bg));
             binding.status.setTextColor(ContextCompat.getColor(context,R.color.colorRed));
         }
        }
    }
}
