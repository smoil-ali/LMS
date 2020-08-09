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
import com.example.lms.Model.Constants;
import com.example.lms.Model.LessonData;
import com.example.lms.Model.LessonResponse;
import com.example.lms.Model.Section;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.SectionResponse;
import com.example.lms.Model.Section_LessonModel;
import com.example.lms.Model.Utils;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.activity.AddStudent;
import com.example.lms.databinding.SectionItemBinding;
import com.example.lms.dialogs.AddSection;
import com.example.lms.dialogs.DeleteDialog;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lms.Model.Constants.ACTION;
import static com.example.lms.Model.Constants.DATA;
import static com.example.lms.Model.Constants.EDIT;
import static com.example.lms.Model.Constants.FAILED;
import static com.example.lms.Model.Constants.FROM;
import static com.example.lms.Model.Constants.RESPONSE_FAILED;
import static com.example.lms.Model.Constants.STUDENT;
import static com.example.lms.Model.Constants.SUCCESS;

public class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements deleteListener {

    private static final String TAG = SectionAdapter.class.getSimpleName();
    Context context;
    List<Section_LessonModel> dataList;
    FragmentManager fragmentManager;
    LessonAdapter adapter;
    deleteListener deleteListener;
    AddSection addSection = new AddSection();


    DeleteDialog deleteDialog = new DeleteDialog();
    public SectionAdapter(Context context, List<Section_LessonModel> dataList, FragmentManager fragmentManager) {
        this.context = context;
        this.dataList = dataList;
        this.fragmentManager = fragmentManager;

    }

    public void setDeleteListener(com.example.lms.Listener.deleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SectionItemBinding binding=SectionItemBinding.inflate(layoutInflater,parent,false);
        return new Section(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Section)holder).bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void OnDelete(String status, String message) {
        deleteListener.OnDelete(status,message);
    }

    public class Section extends RecyclerView.ViewHolder {
        SectionItemBinding binding;
        public Section(SectionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(Section_LessonModel sectionData){
            binding.titleSection.setText(sectionData.getSectionData().getTitle());
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
                                    DeleteSection(sectionData.getSectionData().getId());
                                }).show();
                    }else {
                        addSection.setTitle(binding.titleSection.getText().toString());
                        addSection.setAction(EDIT);
                        addSection.setDeleteListener(SectionAdapter.this);
                        addSection.setId(sectionData.getSectionData().getId());
                        Utils.openDialog(fragmentManager,addSection);
                    }
                    return true;
                });
                menu.show();
            });

            setUpRecyclerView(sectionData.getLessonDataList());
        }

        private void setUpRecyclerView(List<LessonData> lessonData){
            binding.lessonRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.lessonRv.getContext(),
                    DividerItemDecoration.HORIZONTAL);
            binding.lessonRv.addItemDecoration(dividerItemDecoration);
            adapter=new LessonAdapter(context,lessonData,fragmentManager,deleteListener);
            binding.lessonRv.setAdapter(adapter);
        }


        public void DeleteSection(String id){
            Utils.openDialog(fragmentManager,deleteDialog);
            AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
            Call<SectionResponse> call = academyApis.deleteSection(id);
            Log.i(TAG,call.request().url().toString());
            call.enqueue(new Callback<SectionResponse>() {
                @Override
                public void onResponse(Call<SectionResponse> call, Response<SectionResponse> response) {
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
                public void onFailure(Call<SectionResponse> call, Throwable t) {
                    deleteDialog.dismiss();
                    Utils.showDialog(context,FAILED,t.getMessage());
                }
            });
        }
    }
}
