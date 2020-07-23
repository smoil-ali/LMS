package com.example.lms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.CategoryData;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.EnrollHistoryUserData;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.StudentData;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.databinding.StudentItemBinding;
import com.example.lms.databinding.SubcategoryItemBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<StudentData> dataList;
    List<EnrollHistoryUserData> enrollHistoryUserDataList=new ArrayList<>();
    EnrollHistoryAdapter adapter;
    final String TAG = StudentAdapter.class.getSimpleName();

    public StudentAdapter(Context context, List<StudentData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        StudentItemBinding binding = StudentItemBinding.inflate(layoutInflater,parent,false);
        return new StudentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG,dataList.size()+"");
        ((StudentViewHolder)holder).bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        StudentItemBinding binding;
        public StudentViewHolder(StudentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(StudentData studentData){
            binding.studentName.setText(studentData.getFirst_name());
            binding.studentEmail.setText(studentData.getEmail());
            binding.studentStatus.setText(studentData.getStatus());
            getEnrollHistoryById(studentData.getId());
        }

        private void setUpRecyclerView(){
            binding.coursesEnrol.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.coursesEnrol.getContext(),
                    DividerItemDecoration.HORIZONTAL);
            binding.coursesEnrol.addItemDecoration(dividerItemDecoration);
            adapter=new EnrollHistoryAdapter(context,enrollHistoryUserDataList,0);
            binding.coursesEnrol.setAdapter(adapter);
        }

        private void getEnrollHistoryById(String id){
            AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
            Call<EnrollmentHistoryResponse> ResponseCall = academyApis.getEnrollmentHistoryById(id);
            Log.i(TAG,ResponseCall.request().url()+"");
            ResponseCall.enqueue(new Callback<EnrollmentHistoryResponse>() {
                @Override
                public void onResponse(Call<EnrollmentHistoryResponse> call, Response<EnrollmentHistoryResponse> response) {
                    if (response.isSuccessful()){
                        EnrollmentHistoryResponse EnrollmentHistoryResponse = response.body();
                        if (EnrollmentHistoryResponse.getCode().equals("200")){
                            Log.i(TAG,EnrollmentHistoryResponse.getStatus());
                            enrollHistoryUserDataList.clear();
                            enrollHistoryUserDataList.addAll(EnrollmentHistoryResponse.getData());
                            Log.i(TAG,"list size "+enrollHistoryUserDataList.size());
                            binding.studentItemProgressbar.setVisibility(View.GONE);
                            setUpRecyclerView();
                        }else {
                            binding.studentItemProgressbar.setVisibility(View.GONE);
                            Log.i(TAG,EnrollmentHistoryResponse.getStatus());
                        }
                    }else {
                        binding.studentItemProgressbar.setVisibility(View.GONE);
                        Log.i(TAG,response.message());
                    }

                }

                @Override
                public void onFailure(Call<EnrollmentHistoryResponse> call, Throwable t) {
                    binding.studentItemProgressbar.setVisibility(View.GONE);
                    Log.i(TAG,t.getMessage());
                }
            });

        }

    }
}