package com.example.lms.ui.updateCourse;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.lms.Adapters.SectionAdapter;
import com.example.lms.Adapters.StudentAdapter;
import com.example.lms.Factories.SectionFactory;
import com.example.lms.Factories.StudentFactory;
import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.Section;
import com.example.lms.Model.SectionData;
import com.example.lms.Model.SectionResponse;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Model.Utils;
import com.example.lms.R;
import com.example.lms.ViewModels.SectionViewModel;
import com.example.lms.ViewModels.StudentViewModel;
import com.example.lms.activity.UpdateCourse;
import com.example.lms.databinding.FragmentCurriculumBinding;
import com.example.lms.dialogs.AddLesson;
import com.example.lms.dialogs.AddQuiz;
import com.example.lms.dialogs.AddSection;
import com.example.lms.ui.students.StudentFragment;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.example.lms.Model.Constants.ADD;


public class Curriculum extends Fragment implements deleteListener {


    private static final String TAG = Curriculum.class.getSimpleName();
    SectionAdapter adapter;
    FragmentCurriculumBinding binding;
    List<SectionData> dataList = new ArrayList<>();
    AddLesson addLesson = new AddLesson();
    AddQuiz addQuiz = new AddQuiz();
    AddSection addSection = new AddSection();
    SectionViewModel viewModel;
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurriculumBinding.inflate(inflater,container,false);
        setUpRecyclerView();
        viewModel= new ViewModelProvider(getActivity(),new SectionFactory(getContext(),binding.progressBar,UpdateCourse.courseData.getId())).get(SectionViewModel.class);
        viewModel.getMutableLiveData().observe(requireActivity(), new Observer<Response<SectionResponse>>() {
            @Override
            public void onChanged(Response<SectionResponse> response) {
                if (response.isSuccessful()){
                    SectionResponse response1 = response.body();
                    if (response1.getCode().equals("200") && response1.getStatus().equals(Constants.SUCCESS)){
                        dataList.clear();
                        dataList.addAll(response1.getData());
                        Constants.sectionData = dataList;
                        adapter.notifyDataSetChanged();
                        adapter.setDeleteListener(Curriculum.this);
                        binding.sectionRv.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);
                        binding.alertMessage.setVisibility(View.GONE);
                    }else {
                        binding.sectionRv.setVisibility(View.GONE);
                        binding.progressBar.setVisibility(View.GONE);
                        binding.alertMessage.setVisibility(View.VISIBLE);
                        binding.alertMessage.setText(response1.getStatus()+" "+response1.getMessage());
                    }
                }else {
                    binding.sectionRv.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    binding.alertMessage.setVisibility(View.VISIBLE);
                    binding.alertMessage.setText(Constants.RESPONSE_FAILED+" "+response.message());
                }
            }
        });

        binding.addbtn.setOnClickListener(view -> {
            PopupMenu menu=new PopupMenu(getContext(),binding.addbtn);
            menu.getMenuInflater().inflate(R.menu.curriculum_menu,menu.getMenu());
            menu.setOnMenuItemClickListener(menuItem -> {
                if (R.id.add_section == menuItem.getItemId()){
                    addSection.setAction(ADD);
                    addSection.setDeleteListener(this);
                    Utils.openDialog(getParentFragmentManager(),addSection);
                }else if (R.id.add_lesson == menuItem.getItemId()){
                    addLesson.setAction(ADD);
                    Utils.openDialog(getParentFragmentManager(),addLesson);
                }else {
                    Utils.openDialog(getParentFragmentManager(),addQuiz);
                }
                return true;
            });
            menu.show();
        });
        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        binding.sectionRv.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.sectionRv.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.sectionRv.addItemDecoration(dividerItemDecoration);
        binding.sectionRv.setNestedScrollingEnabled(false);
        adapter=new SectionAdapter(getContext(),dataList,getParentFragmentManager());
        binding.sectionRv.setAdapter(adapter);
    }

    @Override
    public void OnDelete(String status, String message) {
        viewModel.update(getContext(),binding.progressBar,UpdateCourse.courseData.getId());
        new AlertDialog.Builder(getContext())
                .setTitle(status)
                .setMessage(message)
                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
    }
}