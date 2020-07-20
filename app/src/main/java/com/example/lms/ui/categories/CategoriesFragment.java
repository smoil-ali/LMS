package com.example.lms.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lms.Adapters.CategoryAdapter;
import com.example.lms.Factories.CategoryFactory;
import com.example.lms.Factories.EnrollHistoryFactory;
import com.example.lms.Model.CategoryData;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.R;
import com.example.lms.ViewModels.CategoryViewModel;
import com.example.lms.databinding.FragmentCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    FragmentCategoryBinding binding;
    List<CategoryData> dataList =new ArrayList<>();
    CategoryViewModel viewModel;
    CategoryAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
        setUpRecyclerView();
        viewModel= new ViewModelProvider(getActivity(),new CategoryFactory(getContext(),binding.categoryProgressBar)).get(CategoryViewModel.class);
        viewModel.getCategoryLiveData().observe(requireActivity(), new Observer<List<CategoryData>>() {
            @Override
            public void onChanged(List<CategoryData> categoryDataList) {
                if (categoryDataList.size() > 0 ){
                    dataList.clear();
                    dataList.addAll(categoryDataList);
                    adapter.notifyDataSetChanged();
                    binding.categoryProgressBar.setVisibility(View.GONE);
                    binding.alertCategoryMessage.setVisibility(View.GONE);
                }else {
                    binding.categoryProgressBar.setVisibility(View.GONE);
                    binding.alertCategoryMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.errorMessage.observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.alertCategoryMessage.setText(s);
                binding.alertCategoryMessage.setVisibility(View.VISIBLE);
                binding.categoryProgressBar.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvCategories.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.rvCategories.addItemDecoration(dividerItemDecoration);
        binding.rvCategories.setNestedScrollingEnabled(false);
        adapter=new CategoryAdapter(getContext(),dataList);
        binding.rvCategories.setAdapter(adapter);
    }
}
