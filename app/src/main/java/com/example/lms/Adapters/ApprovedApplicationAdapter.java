package com.example.lms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.ApplicationResponse;
import com.example.lms.Model.ApprovedApplication;
import com.example.lms.Model.Utils;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.databinding.ApplicationsItemBinding;
import com.example.lms.dialogs.DeleteDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.SUCCESS;

public class ApprovedApplicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<ApprovedApplication> approvedApplicationsList;
    int viewType;
    public static final int APPROVED=1;
    public static final int pending=0;
    DeleteDialog deleteDialog = new DeleteDialog();
    com.example.lms.Listener.deleteListener deleteListener;
    String TAG=ApprovedApplicationAdapter.class.getSimpleName();
    FragmentManager manager;


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

    public class ApprovedApplicationViewHoler extends RecyclerView.ViewHolder {
        ApplicationsItemBinding binding;

        public ApprovedApplicationViewHoler(ApplicationsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(ApprovedApplication approvedApplication, int position) {
            binding.appName.setText(approvedApplication.getUser_name());
            binding.detail.setText(approvedApplication.getMessage());
            if (approvedApplication.getStatus().equals("1"))
                binding.status.setText("Active");
            else if (approvedApplication.getStatus().equals("0")) {
                binding.status.setText("Pending");
                binding.popupMenu.setVisibility(View.VISIBLE);
                binding.status.setBackground(ContextCompat.getDrawable(context, R.drawable.status_de_bg));
                binding.status.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
            }

            binding.popupMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, binding.popupMenu);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(item -> {
                        if (item.getItemId()==R.id.delete){
                            new AlertDialog.Builder(context)
                                    .setTitle("Alert!!!")
                                    .setMessage("You want to delete?")
                                    .setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                                    .setPositiveButton("OK", (dialogInterface, i) -> {
                                        deleteApplication(approvedApplication.getId());

                                    }).show();
                        }else if (item.getItemId()==R.id.approved){

                        }

                        return true;
                    });
                    popupMenu.show();
                }

            });
        }
    }
        private void deleteApplication(String id){
        AcademyApis apis= RetrofitService.createService(AcademyApis.class);
        Call<ApplicationResponse> applicationResponseCall=apis.deleteApplication(id);
        applicationResponseCall.enqueue(new Callback<ApplicationResponse>() {
            @Override
            public void onResponse(Call<ApplicationResponse> call, Response<ApplicationResponse> response) {
                if (response.isSuccessful()){
                    ApplicationResponse applicationResponse=response.body();
                    if (applicationResponse.getCode().equals("200") && applicationResponse.getStatus().equals(SUCCESS)){
                        new androidx.appcompat.app.AlertDialog.Builder(context)
                                .setTitle("Deleted")
                                .setMessage(response.message())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                    }else{
                        deleteDialog.dismiss();
                        new androidx.appcompat.app.AlertDialog.Builder(context)
                                .setTitle(applicationResponse.getStatus())
                                .setMessage(applicationResponse.getMessage())
                                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                        Log.i(TAG,applicationResponse.getStatus()+" delete");
                    }
                }else {
                    deleteDialog.dismiss();
                    new androidx.appcompat.app.AlertDialog.Builder(context)
                            .setTitle("Failed")
                            .setMessage(response.message())
                            .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                    Log.i(TAG,response.message()+" delete");
                }
            }

            @Override
            public void onFailure(Call<ApplicationResponse> call, Throwable t) {
                deleteDialog.dismiss();
                new androidx.appcompat.app.AlertDialog.Builder(context)
                        .setTitle("Failed")
                        .setMessage(t.getMessage())
                        .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                Log.i(TAG,t.getMessage()+" delete");
            }
        });
    }
}
