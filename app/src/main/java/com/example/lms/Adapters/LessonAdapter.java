package com.example.lms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.LessonData;
import com.example.lms.Model.LessonResponse;
import com.example.lms.Model.Utils;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.databinding.LessonItemBinding;
import com.example.lms.dialogs.AddLesson;
import com.example.lms.dialogs.DeleteDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.EDIT;
import static com.example.lms.Model.Constants.FAILED;
import static com.example.lms.Model.Constants.RESPONSE_FAILED;
import static com.example.lms.Model.Constants.SUCCESS;
import static com.example.lms.Model.Constants.sectionData;

public class LessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements deleteListener {

    private static final String TAG = LessonAdapter.class.getSimpleName();
    Context context;
    List<LessonData> lessonData;
    FragmentManager fragmentManager;
    AddLesson addLesson = new AddLesson();
    DeleteDialog deleteDialog = new DeleteDialog();
    deleteListener deleteListener;
    public LessonAdapter(Context context, List<LessonData> lessonData, FragmentManager fragmentManager,deleteListener deleteListener) {
        this.context = context;
        this.lessonData = lessonData;
        this.fragmentManager = fragmentManager;
        this.deleteListener = deleteListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LessonItemBinding binding = LessonItemBinding.inflate(layoutInflater,parent,false);
        return new LessonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((LessonViewHolder)holder).bindView(lessonData.get(position),position);
    }

    @Override
    public int getItemCount() {
        return lessonData.size();
    }

    @Override
    public void OnDelete(String status, String message) {
        deleteListener.OnDelete(status,message);
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder {
        LessonItemBinding binding;
        public LessonViewHolder(LessonItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(LessonData lessonData,int pos){
            binding.lessonTitle.setText(lessonData.getTitle());
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
                                    DeleteLesson(lessonData.getId());
                                }).show();
                    }else {
                        addLesson.setAction(EDIT);
                        addLesson.setId(lessonData.getId());
                        addLesson.setSummary(lessonData.getSummary());
                        addLesson.setTitle(lessonData.getTitle());
                        addLesson.setDeleteListener(LessonAdapter.this);
                        addLesson.setSection(lessonData.getSection_id());
                        Utils.openDialog(fragmentManager,addLesson);
                    }
                    return true;
                });
                menu.show();
            });
        }


        public void DeleteLesson(String id)
        {
            Utils.openDialog(fragmentManager,deleteDialog);
            AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
            Call<LessonResponse> call = academyApis.deleteLesson(id);
            Log.i(TAG,call.request().url().toString());
            call.enqueue(new Callback<LessonResponse>() {
                @Override
                public void onResponse(Call<LessonResponse> call, Response<LessonResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body().getCode().equals("200") && response.body().getStatus().equals(SUCCESS)){
                            deleteDialog.dismiss();
                            deleteListener.OnDelete(response.body().getStatus(),response.body().getMessage());
                        }else {
                            deleteDialog.dismiss();
                            Utils.showDialog(context,response.body().getStatus(),response.body().getMessage());
                        }
                    }else {
                        deleteDialog.dismiss();
                        Utils.showDialog(context,RESPONSE_FAILED,response.message());
                    }
                }

                @Override
                public void onFailure(Call<LessonResponse> call, Throwable t) {
                    deleteDialog.dismiss();
                    Utils.showDialog(context,FAILED,t.getMessage());
                }
            });
        }

    }
}
