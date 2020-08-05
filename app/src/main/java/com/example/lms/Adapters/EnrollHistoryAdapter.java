package com.example.lms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.CourseData;
import com.example.lms.Model.EnrollHistoryUserData;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.databinding.CoursesItemBinding;
import com.example.lms.databinding.EnrolCourseBinding;
import com.example.lms.databinding.EnrollHistoryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<EnrollHistoryUserData> enrollmentHistoryData;
    public static final String TAG = EnrollHistoryAdapter.class.getSimpleName();
    private static final int COURSE_TYPE = 0;
    private static final int LIST_TYPE = 1;
    int viewType;
    SimpleDateFormat simpleDateFormat;
    deleteListener deleteListener;

    public EnrollHistoryAdapter(Context context, List<EnrollHistoryUserData> enrollmentHistoryData,int viewType) {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long date = Long.parseLong("1594011600");
        Log.i(TAG, String.valueOf(date));
        Log.i(TAG,simpleDateFormat.format(new Date(date)));
        this.context = context;
        this.enrollmentHistoryData = enrollmentHistoryData;
        System.out.println(enrollmentHistoryData);
        this.viewType = viewType;
    }

    public void setDeleteListener(deleteListener deleteListener){
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == COURSE_TYPE){
            EnrolCourseBinding binding = EnrolCourseBinding.inflate(layoutInflater,parent,false);
            return new EnrollCourseViewholder(binding);
        }else {
            EnrollHistoryBinding binding = EnrollHistoryBinding.inflate(layoutInflater,parent,false);
            return new EnrollHistoryViewHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG,enrollmentHistoryData.size()+"");
        if (holder instanceof EnrollHistoryViewHolder){
            ((EnrollHistoryViewHolder)holder).bindView(enrollmentHistoryData.get(position));
        }else {
            ((EnrollCourseViewholder)holder).bindView(enrollmentHistoryData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return enrollmentHistoryData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (viewType == COURSE_TYPE){
            return COURSE_TYPE;
        }else {
            return LIST_TYPE;
        }
    }

    public class EnrollHistoryViewHolder extends RecyclerView.ViewHolder{
        EnrollHistoryBinding binding;
        public EnrollHistoryViewHolder(EnrollHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(EnrollHistoryUserData enrollmentHistoryData){
            Log.i(TAG,enrollmentHistoryData.getDate_added());
            binding.setEnrollDate.setText(enrollmentHistoryData.getDate_added());
            binding.studentEmail.setText("Email here");
            binding.studentName.setText(enrollmentHistoryData.getUserName());
            binding.enrolCourse.setText(enrollmentHistoryData.getCourseName());
            binding.moreMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu menu=new PopupMenu(context,binding.moreMenu);
                    menu.getMenuInflater().inflate(R.menu.del_menu,menu.getMenu());
                    menu.setOnMenuItemClickListener(item -> {
                        if (item.getItemId() == R.id.delete)
                            new AlertDialog.Builder(context)
                                    .setTitle("Alert!!!")
                                    .setMessage("You want to delete?")
                                    .setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                                    .setPositiveButton("OK", (dialogInterface, i) -> DeleteHistory(enrollmentHistoryData.getId())).show();
                        return true;
                    });
                    menu.show();
                }
            });
        }

        private void DeleteHistory(String id){
            AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
            Call<EnrollmentHistoryResponse> EnrollmentHistoryResponseCall = academyApis.deleteEnrollHistory(id);
            Log.i(TAG,EnrollmentHistoryResponseCall.request().url()+"");
            EnrollmentHistoryResponseCall.enqueue(new Callback<EnrollmentHistoryResponse>() {
                @Override
                public void onResponse(Call<EnrollmentHistoryResponse> call, Response<EnrollmentHistoryResponse> response) {
                    if (response.isSuccessful()){
                        EnrollmentHistoryResponse EnrollmentHistoryResponse = response.body();
                        if (EnrollmentHistoryResponse.getCode().equals("200") && !EnrollmentHistoryResponse.getStatus().equals("FAILED")){
                            deleteListener.OnDelete(EnrollmentHistoryResponse.getStatus(),EnrollmentHistoryResponse.getMessage());
                        }else {
                            new AlertDialog.Builder(context)
                                    .setTitle(EnrollmentHistoryResponse.getStatus())
                                    .setMessage(EnrollmentHistoryResponse.getMessage())
                                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                            Log.i(TAG,EnrollmentHistoryResponse.getStatus()+" delete");
                        }
                    }else {
                        new AlertDialog.Builder(context)
                                .setTitle("Failed")
                                .setMessage(response.message())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                        Log.i(TAG,response.message()+" delete");
                    }
                }

                @Override
                public void onFailure(Call<EnrollmentHistoryResponse> call, Throwable t) {
                    new AlertDialog.Builder(context)
                            .setTitle("Failed")
                            .setMessage(t.getMessage())
                            .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                    Log.i(TAG,t.getMessage()+" delete");
                }
            });
        }

    }


    public class EnrollCourseViewholder extends RecyclerView.ViewHolder {
        EnrolCourseBinding binding;
        public EnrollCourseViewholder(EnrolCourseBinding binding) {
            super(binding.getRoot());
            Log.i(TAG,"in CourseViewHolder");
            this.binding = binding;
        }
        public void bindView(EnrollHistoryUserData enrollHistoryUserData){
            binding.textView2.setText(enrollHistoryUserData.getCourseName());
        }
    }


}
