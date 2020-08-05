package com.example.lms.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lms.Model.CategoryData;
import com.example.lms.Model.CourseData;
import com.example.lms.Model.CourseResponse;
import com.example.lms.R;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;
import com.example.lms.ViewModels.CourseViewModel;
import com.example.lms.activity.Login;
import com.example.lms.databinding.CoursesItemBinding;

import java.util.ArrayList;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<CourseData> courseDataArrayList ;
    String TAG = CourserAdapter.class.getSimpleName();

    public CourserAdapter(Context context, ArrayList<CourseData> courseDataArrayList) {
        this.context = context;
        this.courseDataArrayList = courseDataArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CoursesItemBinding binding = CoursesItemBinding.inflate(layoutInflater,parent,false);
        return new CourseViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG,courseDataArrayList.size()+"");
        ((CourseViewHolder)holder).bindView(courseDataArrayList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return courseDataArrayList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder{
        CoursesItemBinding binding;
        public CourseViewHolder(CoursesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bindView(CourseData courseData, int position){
            binding.courseCategory.setText(courseData.getCategory().getName());
            binding.totalSection.setText(String.valueOf(courseData.getSection().getCount()));
            binding.courseStatus.setText(courseData.getStatus());
            binding.courseTitle.setText(courseData.getTitle());
            binding.totalEnrol.setText(String.valueOf(courseData.getEnrollment().getCount()));
            Log.i(TAG,courseData.getPrice());
            binding.price.setText("$"+courseData.getPrice());
            binding.courseInstructor.setText("Instructor : "+(courseData.getInstructor().getFirst_name()));
            binding.totalLesson.setText(String.valueOf(courseData.getLesson().getCount()));
            binding.moreMenu.setOnClickListener(v -> {
                PopupMenu menu=new PopupMenu(context,binding.moreMenu);
                menu.getMenuInflater().inflate(R.menu.popup_menu,menu.getMenu());
                menu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId()==R.id.delete)
                    deleteCaourses(courseData.getId(),position);
                    return true;
                });
                menu.show();

            });

        }

        private void deleteCaourses(String id, int position){
            AcademyApis apis= RetrofitService.createService(AcademyApis.class);
           Call<CourseResponse> courseResponseCall=apis.deleteCourse(id);
           courseResponseCall.enqueue(new Callback<CourseResponse>() {
               @Override
               public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                   if (response.isSuccessful()){
                       CourseResponse courseResponse=response.body();
                       if (courseResponse.getCode().equals("200")){
                           new AlertDialog.Builder(context)
                                   .setTitle(courseResponse.getStatus())
                                   .setMessage(courseResponse.getMessage())
                                   .setPositiveButton("OK",(((dialog, which) -> dialog.dismiss()))).show();
                           courseDataArrayList.remove(position);
                           notifyItemChanged(position);
                       }else{
                           new androidx.appcompat.app.AlertDialog.Builder(context)
                                   .setTitle(courseResponse.getStatus())
                                   .setMessage(courseResponse.getMessage())
                                   .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                           Log.i(TAG,courseResponse.getStatus()+" delete");
                       }
                   }else {
                       new androidx.appcompat.app.AlertDialog.Builder(context)
                               .setTitle("Failed")
                               .setMessage(response.message())
                               .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                       Log.i(TAG,response.message()+" delete");
                   }
               }

               @Override
               public void onFailure(Call<CourseResponse> call, Throwable t) {
                   new androidx.appcompat.app.AlertDialog.Builder(context)
                           .setTitle("Failed")
                           .setMessage(t.getMessage())
                           .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
                   Log.i(TAG,t.getMessage()+" delete");
               }
           });
        }
    }
}
