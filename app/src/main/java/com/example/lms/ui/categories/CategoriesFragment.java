package com.example.lms.ui.categories;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lms.Adapters.CategoryAdapter;
import com.example.lms.Factories.CategoryFactory;
import com.example.lms.Factories.EnrollHistoryFactory;
import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.CategoryData;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.Constants;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.Model.InstructorResponse;
import com.example.lms.R;
import com.example.lms.ViewModels.CategoryViewModel;
import com.example.lms.databinding.FragmentCategoryBinding;
import com.example.lms.ui.instructors.InstructorListFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class CategoriesFragment extends Fragment implements deleteListener {

    final String TAG = CategoriesFragment.class.getSimpleName();
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

        viewModel.getCategoryLiveData().observe(requireActivity(), new Observer<Response<CategoryResponse>>() {
            @Override
            public void onChanged(Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    CategoryResponse response1 = response.body();
                    if (response1.getCode().equals("200") && response1.getStatus().equals(Constants.SUCCESS)){
                        dataList.clear();
                        dataList.addAll(response1.getData());
                        adapter.notifyDataSetChanged();
                        adapter.setDeleteListener(CategoriesFragment.this);
                        binding.rvCategories.setVisibility(View.VISIBLE);
                        binding.categoryProgressBar.setVisibility(View.GONE);
                        binding.alertCategoryMessage.setVisibility(View.GONE);
                    }else {
                        binding.rvCategories.setVisibility(View.GONE);
                        binding.categoryProgressBar.setVisibility(View.GONE);
                        binding.alertCategoryMessage.setVisibility(View.VISIBLE);
                        binding.alertCategoryMessage.setText(response1.getStatus()+" "+response1.getMessage());
                    }
                }else {
                    binding.rvCategories.setVisibility(View.GONE);
                    binding.categoryProgressBar.setVisibility(View.GONE);
                    binding.alertCategoryMessage.setVisibility(View.VISIBLE);
                    binding.alertCategoryMessage.setText(Constants.RESPONSE_FAILED+" "+response.message());
                }

            }
        });


        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvCategories.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.rvCategories.addItemDecoration(dividerItemDecoration);
        //binding.rvCategories.setNestedScrollingEnabled(false);
        adapter=new CategoryAdapter(getContext(),dataList);
        binding.rvCategories.setAdapter(adapter);
    }

    @Override
    public void OnDelete(String status, String message) {
        viewModel.update(getContext(),binding.categoryProgressBar);
        new AlertDialog.Builder(getContext())
                .setTitle(status)
                .setMessage(message)
                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
    }
}
