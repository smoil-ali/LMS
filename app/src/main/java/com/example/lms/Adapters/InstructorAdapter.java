package com.example.lms.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.Instructor;
import com.example.lms.Model.InstructorData;
import com.example.lms.Model.InstructorResponse;
import com.example.lms.Model.StudentData;
import com.example.lms.R;
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

    public InstructorAdapter(Context context, List<InstructorData> dataList) {
        this.context = context;
        this.dataList = dataList;
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
        ((InstructorViewHolder)holder).bindView(dataList.get(position),position);
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

        public void bindView(InstructorData instructorData,int position){
            binding.studentName.setText(instructorData.getFirst_name());
            binding.instEmail.setText(instructorData.getEmail());
            binding.instActiveCourses.setText(instructorData.getStatus());

            binding.actionMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu=new PopupMenu(context,binding.actionMenu);

                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId()==R.id.delete)
                                new AlertDialog.Builder(context)
                                        .setTitle("Alert!!!")
                                        .setMessage("Are you sure?")
                                        .setNegativeButton("NO",(dialogInterface,i)->dialogInterface.dismiss())
                                        .setPositiveButton("OK",(dialogInterface,i)->deleteInstructor(instructorData.getId(),position)).show();
                            return true;
                        }
                    });
                    popupMenu.show();



                }
            });
        }

        private void deleteInstructor(String id,int position){

            AcademyApis academyApis= RetrofitService.createService(AcademyApis.class);
            Call<InstructorResponse> instructorResponseCall=academyApis.deleteInstructor(id);

            instructorResponseCall.enqueue(new Callback<InstructorResponse>() {
                @Override
                public void onResponse(Call<InstructorResponse> call, Response<InstructorResponse> response) {
                    if (response.isSuccessful()){
                        InstructorResponse instructorResponse=response.body();
                        if (instructorResponse.getCode().equals("200")){

                            new AlertDialog.Builder(context)
                                    .setTitle(instructorResponse.getStatus())
                                    .setMessage(instructorResponse.getMessage())
                                    .setPositiveButton("OK",(((dialog, which) -> dialog.dismiss()))).show();
                            dataList.remove(position);
                            notifyItemChanged(position);
                        }else {
                            new AlertDialog.Builder(context)
                                    .setTitle(instructorResponse.getStatus())
                                    .setMessage(instructorResponse.getMessage())
                                    .setPositiveButton("OK",(((dialog, which) -> dialog.dismiss()))).show();
                        }
                    }else {
                        new AlertDialog.Builder(context)
                                .setTitle("Failed")
                                .setMessage(response.message())
                                .setPositiveButton("OK",(((dialog, which) -> dialog.dismiss()))).show();
                    }
                }

                @Override
                public void onFailure(Call<InstructorResponse> call, Throwable t) {
                    new AlertDialog.Builder(context)
                            .setTitle("Failed")
                            .setMessage(t.getMessage())
                            .setPositiveButton("OK",(((dialog, which) -> dialog.dismiss()))).show();
                }
            });

        }
    }
}

