package com.example.lms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.Section;
import com.example.lms.Model.SectionData;
import com.example.lms.databinding.SectionItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<SectionData> dataList = new ArrayList<>();
    FragmentManager fragmentManager;

    public SectionAdapter(Context context, List<SectionData> dataList, FragmentManager fragmentManager) {
        this.context = context;
        this.dataList = dataList;
        this.fragmentManager = fragmentManager;
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

    public class Section extends RecyclerView.ViewHolder {
        SectionItemBinding binding;
        public Section(SectionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(SectionData sectionData){
            binding.titleSection.setText(sectionData.getTitle());
        }
    }
}
