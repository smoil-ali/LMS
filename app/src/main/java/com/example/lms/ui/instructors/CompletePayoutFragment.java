package com.example.lms.ui.instructors;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lms.Adapters.EnrollHistoryAdapter;
import com.example.lms.Adapters.InstructorPayoutAdapter;
import com.example.lms.Factories.EnrollHistoryFactory;
import com.example.lms.Factories.PayoutFilterFactory;
import com.example.lms.Model.Constants;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.InstructorPayout;
import com.example.lms.Model.PayoutResponse;
import com.example.lms.R;
import com.example.lms.ViewModels.EnrollHistoryViewModel;
import com.example.lms.ViewModels.PayoutFilterViewModel;
import com.example.lms.databinding.FragmentInstructorPayoutBinding;
import com.example.lms.databinding.FragmentPayoutCompletedBinding;
import com.example.lms.ui.enrolment.EnrolHistoryFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

public class CompletePayoutFragment extends Fragment {

    FragmentPayoutCompletedBinding binding;
    final String TAG = CompletePayoutFragment.class.getSimpleName();
    InstructorPayoutAdapter adapter;
    PayoutFilterViewModel viewModel;
    List<InstructorPayout> dataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentPayoutCompletedBinding.inflate(inflater,container,false);

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

        viewModel= new ViewModelProvider(requireActivity(),new PayoutFilterFactory(getContext(),binding.Progressbar,binding.txtFrom.getText().toString(),binding.txtTo.getText().toString()))
                .get(PayoutFilterViewModel.class);

        viewModel.getPayoutDataMutabeLiveData().observe(requireActivity(), new Observer<Response<PayoutResponse>>() {
            @Override
            public void onChanged(Response<PayoutResponse> response) {
                if (response.isSuccessful()){
                    PayoutResponse response1 = response.body();
                    if (response1.getCode().equals("200") && response1.getStatus().equals(Constants.SUCCESS)){
                        dataList.clear();
                        dataList.addAll(response1.getData());
                        adapter.notifyDataSetChanged();
                        binding.completePayoutRv.setVisibility(View.VISIBLE);
                        binding.Progressbar.setVisibility(View.GONE);
                        binding.AlertMessage.setVisibility(View.GONE);
                    }else {
                        binding.completePayoutRv.setVisibility(View.GONE);
                        binding.Progressbar.setVisibility(View.GONE);
                        binding.AlertMessage.setVisibility(View.VISIBLE);
                        binding.AlertMessage.setText(response1.getStatus()+" "+response1.getMessage());
                    }
                }else {
                    binding.completePayoutRv.setVisibility(View.GONE);
                    binding.Progressbar.setVisibility(View.GONE);
                    binding.AlertMessage.setVisibility(View.VISIBLE);
                    binding.AlertMessage.setText(Constants.RESPONSE_FAILED+" "+response.message());
                }
            }
        });

        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        binding.completePayoutRv.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.completePayoutRv.getContext(),
                DividerItemDecoration.HORIZONTAL);
        binding.completePayoutRv.addItemDecoration(dividerItemDecoration);
        binding.completePayoutRv.setNestedScrollingEnabled(false);
        adapter=new InstructorPayoutAdapter(getContext(),dataList);
        binding.completePayoutRv.setAdapter(adapter);
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
        viewModel.update(getContext(),binding.Progressbar,binding.txtFrom.getText().toString(),binding.txtTo.getText().toString());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("From",binding.txtFrom.getText().toString());
        outState.putString("To",binding.txtTo.getText().toString());
    }
}
