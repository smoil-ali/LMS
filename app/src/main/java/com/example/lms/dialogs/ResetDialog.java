package com.example.lms.dialogs;


import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.lms.Listener.ResetListener;
import com.example.lms.Model.ResetPassword;
import com.example.lms.Model.Utils;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.databinding.ResetPasswordBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetDialog extends DialogFragment {

    private String TAG = ResetDialog.class.getSimpleName();
    ResetPasswordBinding binding;
    ResetListener resetListener;

    public ResetDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ResetPasswordBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        binding.resetBtn.setOnClickListener(view -> {

            if (!binding.newPass.getText().toString().trim().matches("") || !binding.confirmPass.getText().toString().trim().matches("")) {
                if (binding.newPass.getText().toString().equals(binding.confirmPass.getText().toString())) {
                    binding.errorMessage.setVisibility(View.VISIBLE);
                    binding.errorMessage.setText("Resetting...");
                    if (true) {
                        AcademyApis academyApis = RetrofitService.createService(AcademyApis.class);
                        Call<ResetPassword> resetPasswordCall = academyApis.resetPassword(Utils.getSharedPref(getContext()).getId(), binding.confirmPass.getText().toString());
                        Log.i(TAG, resetPasswordCall.request().url() + "");
                        resetPasswordCall.enqueue(new Callback<ResetPassword>() {
                            @Override
                            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                                ResetPassword resetPassword = response.body();
                                if (resetPassword.getCode().equals("200")) {
                                    empty();
                                    resetListener.onReset(resetPassword.getMessage(), resetPassword.getStatus());
                                } else {
                                    binding.errorMessage.setText(resetPassword.getMessage());
                                }

                            }

                            @Override
                            public void onFailure(Call<ResetPassword> call, Throwable t) {
                                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        binding.errorMessage.setText("Internet is not connected!!!");
                    }
                } else {
                    binding.errorMessage.setVisibility(View.VISIBLE);
                    binding.errorMessage.setText("Password does not match!!!");
                    empty();
                }
            } else {
                binding.errorMessage.setVisibility(View.VISIBLE);
                binding.errorMessage.setText("Empty fields!!!");
            }

        });
        return v;
    }

    public void setResetListener(ResetListener resetListener) {
        this.resetListener = resetListener;
    }

    public void empty() {
        binding.newPass.setText("");
        binding.confirmPass.setText("");
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();

        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        // Set the width of the dialog proportional to 90% of the screen width
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        // Call super onResume after sizing
        super.onResume();
    }

}