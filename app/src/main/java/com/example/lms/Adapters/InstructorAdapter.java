package com.example.lms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.Instructor;
import com.example.lms.Model.InstructorData;
import com.example.lms.Model.StudentData;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.databinding.InstructorItemBinding;
import com.example.lms.databinding.StudentItemBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstructorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<InstructorData> dataList;
    final String TAG = InstructorAdapter.class.getSimpleName();
    deleteListener deleteListener;

    public InstructorAdapter(Context context, List<InstructorData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setDeleteListener(deleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        InstructorItemBinding binding = InstructorItemBinding.inflate(layoutInflater,parent,false);
        return new InstructorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG,dataList.size()+"");
        ((InstructorViewHolder)holder).bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class InstructorViewHolder extends RecyclerView.ViewHolder {
        InstructorItemBinding binding;
        public InstructorViewHolder(InstructorItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        

        public void bindView(InstructorData instructorData){
            binding.studentName.setText(instructorData.getFirst_name());
            binding.instEmail.setText(instructorData.getEmail());
            binding.instActiveCourses.setText(instructorData.getStatus());
            binding.studentCard.setOnClickListener(view -> new AlertDialog.Builder(context)
                    .setTitle("Alert!!!")
                    .setMessage("You want to delete?")
                    .setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                    .setPositiveButton("OK", (dialogInterface, i) -> DeleteInstructor(instructorData.getId())).show());
        }

        private void DeleteInstructor(String id){
            binding.instructorProgressbar.setVisibility(View.VISIBLE);
            AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
            Call<StudentResponse> StudentResponseCall = academyApis.deleteStudent(id);
            Log.i(TAG,StudentResponseCall.request().url()+"");
            StudentResponseCall.enqueue(new Callback<StudentResponse>() {
                @Override
                public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                    if (response.isSuccessful()){
                        StudentResponse StudentResponse = response.body();
                        if (StudentResponse.getCode().equals("200") && !StudentResponse.getStatus().equals("FAILED")){
                            binding.instructorProgressbar.setVisibility(View.GONE);
                            deleteListener.OnDelete(StudentResponse.getStatus(),StudentResponse.getMessage());
                        }else {
                            binding.instructorProgressbar.setVisibility(View.GONE);
                            new AlertDialog.Builder(context)
                                    .setTitle(StudentResponse.getStatus())
                                    .setMessage(StudentResponse.getMessage())
                                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                            Log.i(TAG,StudentResponse.getStatus()+" delete");
                        }
                    }else {
                        binding.instructorProgressbar.setVisibility(View.GONE);
                        new AlertDialog.Builder(context)
                                .setTitle("Failed")
                                .setMessage(response.message())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                        Log.i(TAG,response.message()+" delete");
                    }
                }

                @Override
                public void onFailure(Call<StudentResponse> call, Throwable t) {
                    binding.instructorProgressbar.setVisibility(View.GONE);
                    new AlertDialog.Builder(context)
                            .setTitle("Failed")
                            .setMessage(t.getMessage())
                            .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                    Log.i(TAG,t.getMessage()+" delete");
                }
            });
        }

    }

}
