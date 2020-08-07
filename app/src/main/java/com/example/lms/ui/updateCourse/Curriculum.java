package com.example.lms.ui.updateCourse;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.lms.Adapters.SectionAdapter;
import com.example.lms.Adapters.StudentAdapter;
import com.example.lms.Model.Section;
import com.example.lms.Model.SectionData;
import com.example.lms.R;
import com.example.lms.activity.UpdateCourse;
import com.example.lms.databinding.FragmentCurriculumBinding;

import java.util.ArrayList;
import java.util.List;


public class Curriculum extends Fragment {


    SectionAdapter adapter;
    FragmentCurriculumBinding binding;
    List<SectionData> dataList = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurriculumBinding.inflate(inflater,container,false);
        dataList = UpdateCourse.courseData.getSection().getData();
        binding.addbtn.setOnClickListener(view -> {
            PopupMenu menu=new PopupMenu(getContext(),binding.addbtn);
            menu.getMenuInflater().inflate(R.menu.curriculum_menu,menu.getMenu());
            menu.setOnMenuItemClickListener(menuItem -> {
                if (R.id.add_section == menuItem.getItemId()){
                    Toast.makeText(getContext(), "add section", Toast.LENGTH_SHORT).show();
                }else if (R.id.add_lesson == menuItem.getItemId()){
                    Toast.makeText(getContext(), "add lesson", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "add quiz", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
            menu.show();
        });

        setUpRecyclerView();
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
}