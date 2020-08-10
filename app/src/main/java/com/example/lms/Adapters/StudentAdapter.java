package com.example.lms.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.EnrollHistoryUserData;
import com.example.lms.Model.UserData;
import com.example.lms.Model.UserData_EnrollmentHistoryModel;
import com.example.lms.Model.Utils;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.AddStudent;
import com.example.lms.databinding.StudentItemBinding;
import com.example.lms.dialogs.DeleteDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.ACTION;
import static com.example.lms.Model.Constants.DATA;
import static com.example.lms.Model.Constants.EDIT;
import static com.example.lms.Model.Constants.FROM;
import static com.example.lms.Model.Constants.STUDENT;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<UserData_EnrollmentHistoryModel> dataList;
    EnrollHistoryAdapter adapter;
    final String TAG = StudentAdapter.class.getSimpleName();
    deleteListener deleteListener;
    DeleteDialog deleteDialog  = new DeleteDialog();
    FragmentManager fragmentManager;

    public StudentAdapter(Context context, List<UserData_EnrollmentHistoryModel> dataList,FragmentManager fragmentManager) {
        this.context = context;
        this.dataList = dataList;
        this.fragmentManager = fragmentManager;
    }

    public void setDeleteListener(deleteListener deleteListener){
        this.deleteListener = deleteListener;
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

        public void bindView(UserData_EnrollmentHistoryModel userData){
            binding.studentName.setText(userData.getUserData().getFirst_name());
            binding.studentEmail.setText(userData.getUserData().getEmail());
            binding.studentStatus.setText(userData.getUserData().getStatus());
            binding.moreMenu.setOnClickListener(view -> {
                PopupMenu menu=new PopupMenu(context,binding.moreMenu);
                menu.getMenuInflater().inflate(R.menu.popup_menu,menu.getMenu());
                menu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.delete){
                        new AlertDialog.Builder(context)
                                .setTitle("Alert!!!")
                                .setMessage("You want to delete?")
                                .setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                                .setPositiveButton("OK", (dialogInterface, i) -> {
                                    Utils.openDialog(fragmentManager,deleteDialog);
                                    DeleteStudent(userData.getUserData().getId());
                                }).show();
                    }else {
                        Intent intent = new Intent(context, AddStudent.class);
                        intent.putExtra(FROM,STUDENT);
                        intent.putExtra(ACTION,EDIT);
                        intent.putExtra(DATA,(Serializable) userData);
                        context.startActivity(intent);
                    }
                    return true;
                });
                menu.show();
            });

            setUpRecyclerView(userData.getList());
        }

        private void setUpRecyclerView(List<EnrollHistoryUserData> enrollHistoryUserDataList){
            binding.coursesEnrol.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.coursesEnrol.getContext(),
                    DividerItemDecoration.HORIZONTAL);
            binding.coursesEnrol.addItemDecoration(dividerItemDecoration);
            adapter=new EnrollHistoryAdapter(context,enrollHistoryUserDataList,0,fragmentManager);
            binding.coursesEnrol.setAdapter(adapter);
        }


        private void DeleteStudent(String id){
            AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
            Call<StudentResponse> StudentResponseCall = academyApis.deleteStudent(id);
            Log.i(TAG,StudentResponseCall.request().url()+"");
            StudentResponseCall.enqueue(new Callback<StudentResponse>() {
                @Override
                public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                    if (response.isSuccessful()){
                        StudentResponse StudentResponse = response.body();
                        if (StudentResponse.getCode().equals("200") && !StudentResponse.getStatus().equals("FAILED")){
                            deleteDialog.dismiss();
                            deleteListener.OnDelete(StudentResponse.getStatus(),StudentResponse.getMessage());
                        }else {
                            deleteDialog.dismiss();
                            new AlertDialog.Builder(context)
                                    .setTitle(StudentResponse.getStatus())
                                    .setMessage(StudentResponse.getMessage())
                                    .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                            Log.i(TAG,StudentResponse.getStatus()+" delete");
                        }
                    }else {
                        deleteDialog.dismiss();
                        new AlertDialog.Builder(context)
                                .setTitle("Failed")
                                .setMessage(response.message())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                        Log.i(TAG,response.message()+" delete");
                    }
                }

                @Override
                public void onFailure(Call<StudentResponse> call, Throwable t) {
                    deleteDialog.dismiss();
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
