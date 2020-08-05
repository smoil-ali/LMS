package com.example.lms.ui.enrolment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lms.Adapters.CourserAdapter;
import com.example.lms.Adapters.EnrollHistoryAdapter;
import com.example.lms.Factories.CourseFactory;
import com.example.lms.Factories.EnrollHistoryFactory;
import com.example.lms.Listener.deleteListener;
import com.example.lms.Model.Constants;
import com.example.lms.Model.EnrollHistoryUserData;
import com.example.lms.Model.EnrollmentHistoryData;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.Error;
import com.example.lms.Model.NetworkState;
import com.example.lms.Model.Utils;
import com.example.lms.R;
import com.example.lms.ViewModels.CourseViewModel;
import com.example.lms.ViewModels.EnrollHistoryViewModel;
import com.example.lms.databinding.FragmentEnrolhistoryBinding;

import java.text.SimpleDateFormat;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

public class EnrolHistoryFragment extends Fragment implements deleteListener {


    private static final String TAG = EnrolHistoryFragment.class.getSimpleName();
    EnrollHistoryAdapter adapter;
    FragmentEnrolhistoryBinding binding;
    EnrollHistoryViewModel enrollHistoryViewModel;
    List<EnrollHistoryUserData> dataList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEnrolhistoryBinding.inflate(inflater,container,false);


        if (savedInstanceState == null){
            setInitDate(binding.txtFrom);
            setInitDate(binding.txtTo);
        }else {
            binding.txtFrom.setText(savedInstanceState.getString("From"));
            binding.txtTo.setText(savedInstanceState.getString("To"));
        }

        binding.imgbtnFrom.setOnClickListener(view -> setPickedDate(binding.txtFrom));

        binding.imgbtnTo.setOnClickListener(view -> setPickedDate(binding.txtTo));

        binding.btnFilterResult.setOnClickListener(view -> getEnrollHistoryByDateRange());

        setUpRecyclerView();
        enrollHistoryViewModel= new ViewModelProvider(getActivity(),new EnrollHistoryFactory(getContext(),binding.enrollHistoryProgressbar,binding.txtFrom.getText().toString(),binding.txtTo.getText().toString()))
                .get(EnrollHistoryViewModel.class);

        enrollHistoryViewModel.getHistoryDataMutableLiveData().observe(requireActivity(), new Observer<Response<EnrollmentHistoryResponse>>() {
            @Override
            public void onChanged(Response<EnrollmentHistoryResponse> response) {
                if (response.isSuccessful()){
                    EnrollmentHistoryResponse response1 = response.body();
                    if (response1.getCode().equals("200") && response1.getStatus().equals(Constants.SUCCESS)){
                        dataList.clear();
                        dataList.addAll(response1.getData());
                        adapter.notifyDataSetChanged();
                        adapter.setDeleteListener(EnrolHistoryFragment.this);
                        binding.enrollHistoryRecyclerview.setVisibility(View.VISIBLE);
                        binding.enrollHistoryProgressbar.setVisibility(View.GONE);
                        binding.enrollHistoryAlertMessage.setVisibility(View.GONE);
                    }else {
                        binding.enrollHistoryRecyclerview.setVisibility(View.GONE);
                        binding.enrollHistoryProgressbar.setVisibility(View.GONE);
                        binding.enrollHistoryAlertMessage.setVisibility(View.VISIBLE);
                        binding.enrollHistoryAlertMessage.setText(response1.getStatus()+" "+response1.getMessage());
                    }
                }else {
                    binding.enrollHistoryRecyclerview.setVisibility(View.GONE);
                    binding.enrollHistoryProgressbar.setVisibility(View.GONE);
                    binding.enrollHistoryAlertMessage.setVisibility(View.VISIBLE);
                    binding.enrollHistoryAlertMessage.setText(Constants.RESPONSE_FAILED+" "+response.message());
                }
            }
        });

        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        binding.enrollHistoryRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.enrollHistoryRecyclerview.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.enrollHistoryRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.enrollHistoryRecyclerview.setNestedScrollingEnabled(false);
        adapter=new EnrollHistoryAdapter(getContext(),dataList,1);
        binding.enrollHistoryRecyclerview.setAdapter(adapter);
    }

    public void setPickedDate(TextView textView){
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {
            //2020-03-01
            String dateFormat="yyyy-MM-dd";
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.YEAR,year);
            SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.US);
            textView.setText(format.format(calendar.getTime()));
        };
        new DatePickerDialog(getContext(),listener,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void setInitDate(TextView textView){
        String dateFormat="yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        textView.setText(format.format(new Date()));
    }

    public void getEnrollHistoryByDateRange(){
        Log.i(TAG,binding.txtFrom.toString());
        enrollHistoryViewModel.update(getContext(),binding.enrollHistoryProgressbar,binding.txtFrom.getText().toString(),binding.txtTo.getText().toString());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("From",binding.txtFrom.getText().toString());
        outState.putString("To",binding.txtTo.getText().toString());
    }

    @Override
    public void OnDelete(String status, String message) {
        enrollHistoryViewModel.update(getContext(),binding.enrollHistoryProgressbar,binding.txtFrom.getText().toString(),binding.txtTo.getText().toString());
        new AlertDialog.Builder(getContext())
                .setTitle(status)
                .setMessage(message)
                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
    }
}
