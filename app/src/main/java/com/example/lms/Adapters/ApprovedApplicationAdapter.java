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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.FAILED;
import static com.example.lms.Model.Constants.RESPONSE_FAILED;
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
    Observable<Response<ApplicationResponse>> observable;


    public void setDeleteListener(com.example.lms.Listener.deleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public ApprovedApplicationAdapter(Context context, List<ApprovedApplication> approvedApplicationsList, int viewType,FragmentManager fragmentManager) {
        manager = fragmentManager;
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
                                        deleteDialog.setText("Deleting...");
                                        Utils.openDialog(manager,deleteDialog);
                                        deleteApplication(approvedApplication.getId());

                                    }).show();
                        }else {
                            deleteDialog.setText("Approving...");
                            Utils.openDialog(manager,deleteDialog);
                            observable = Observable.create(emitter -> {
                                AcademyApis apis= RetrofitService.createService(AcademyApis.class);
                                Call<ApplicationResponse> call = apis.approvedApp(approvedApplication.getId());
                                Log.i(TAG,call.request().url().toString());
                                call.enqueue(new Callback<ApplicationResponse>() {
                                    @Override
                                    public void onResponse(Call<ApplicationResponse> call, Response<ApplicationResponse> response) {
                                        emitter.onNext(response);
                                    }

                                    @Override
                                    public void onFailure(Call<ApplicationResponse> call, Throwable t) {
                                        emitter.onError(t);
                                    }
                                });


                            });
                            observable.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<Response<ApplicationResponse>>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }
                                        @Override
                                        public void onNext(Response<ApplicationResponse> applicationResponseResponse) {
                                            if (applicationResponseResponse.isSuccessful()){
                                                if (applicationResponseResponse.body().getCode().equals("200") &&
                                                applicationResponseResponse.body().getStatus().equals(SUCCESS)){
                                                    deleteListener.OnDelete(applicationResponseResponse.body().getStatus(),
                                                            applicationResponseResponse.body().getMessage());
                                                }else {
                                                    Utils.showDialog(context,applicationResponseResponse.body().getStatus(),
                                                            applicationResponseResponse.body().getMessage());
                                                }
                                            }else {
                                                Utils.showDialog(context,RESPONSE_FAILED,applicationResponseResponse.message());
                                            }
                                            deleteDialog.dismiss();
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            deleteDialog.dismiss();
                                            Utils.showDialog(context,FAILED,e.getMessage());
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
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
                        deleteListener.OnDelete(applicationResponse.getStatus(),applicationResponse.getMessage());
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
