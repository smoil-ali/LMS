package com.example.lms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.lms.Model.Constants;
import com.example.lms.Model.UserData;
import com.example.lms.R;
import com.example.lms.StudentLoginCredentials;
import com.example.lms.StudentSocialInformation;
import com.example.lms.Student_Basic_Info;
import com.example.lms.Student_Payment_info;
import com.example.lms.Student_finish;
import com.example.lms.databinding.ActivityAddStudentBinding;
import com.google.android.material.tabs.TabLayout;

import static com.example.lms.Model.Constants.DATA;
import static com.example.lms.Model.Constants.EDIT;
import static com.example.lms.Model.Constants.FROM;
import static com.example.lms.Model.Constants.STUDENT;

public class AddStudent extends AppCompatActivity {

    ActivityAddStudentBinding binding;
    Fragment fragment;
    public int position = 0;
    Bundle bundle;
    public static String ROLE,ACTION;
    public static UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bundle = getIntent().getExtras();
        ROLE = bundle.getString(FROM);
        ACTION = bundle.getString(Constants.ACTION);
        if (ACTION.equals(EDIT)){
            userData = (UserData) bundle.getSerializable(DATA);
            setData();
        }

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.hostFragment,new Student_Basic_Info()).commit();
        }else {
            position = savedInstanceState.getInt("pos");
            binding.tabAddCourses.getTabAt(position).select();
        }
        binding.nextBtn.setOnClickListener(v -> {
            int position=binding.tabAddCourses.getSelectedTabPosition();
            Log.i("position",String.valueOf(position));
            binding.tabAddCourses.getTabAt(++position).select();
        });
        binding.backBtn.setOnClickListener(v -> {
            int position=binding.tabAddCourses.getSelectedTabPosition();
            Log.i("position",String.valueOf(position));
            binding.tabAddCourses.getTabAt(--position).select();
        });
        setTabLayout();
    }

    private void setTabLayout()
    {
        binding.tabAddCourses.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (binding.tabAddCourses.getSelectedTabPosition()){
                    case 0:
                        position = 0;
                        fragment=new Student_Basic_Info();
                        break;
                    case 1:
                        position = 1;
                        fragment=new StudentLoginCredentials();
                        break;
                    case 2:
                        position = 2;
                        fragment=new StudentSocialInformation();
                        break;
                    case 3:
                        position = 3;
                        fragment=new Student_Payment_info();
                        break;
                    case 4:
                        position = 4;
                        binding.frwBckContainer.setVisibility(View.GONE);
                        fragment=new Student_finish();
                        break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.hostFragment,fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos",position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddStudent.this, MainActivity.class));
        finishAffinity();
    }

    public void setData(){
        setBasicData();
        setLoginData();
        setSocialData();
        setPaymentData();
    }

    public void setBasicData(){
        AddBasicUserModel model= new AddBasicUserModel();
        model.setFirstName(userData.getFirst_name());
        model.setLastName(userData.getLast_name());
        model.setBiography(userData.getBiography());
        addContainer.setModel(model);
    }

    public void setLoginData(){
        AddUserLogindata model = new AddUserLogindata();
        model.setEmail(userData.getEmail());
        model.setPassword(userData.getPassword());
        addContainer.setAddUserLogindata(model);
    }

    public void setSocialData(){
        addUserSocialData model = new addUserSocialData();
        String json = AddStudent.userData.getSocial_links();
        SocialLinks socialLinks = new Gson().fromJson(json,SocialLinks.class);
        model.setFacebook(socialLinks.getFacebook());
        model.setTwitter(socialLinks.getTwitter());
        model.setLinkedin(socialLinks.getLinkedin());
        addContainer.setAddUserSocialData(model);
    }

    public void setPaymentData(){
        addUserPaymentData model = new addUserPaymentData();
        String json = AddStudent.userData.getPaypal_keys();
        String json2 = AddStudent.userData.getStripe_keys();
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONArray jsonArray1 = new JSONArray(json2);
            for (int i=0;i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                model.setPaypalSecretId(jsonObject.getString("production_secret_key"));
                model.setPaypalClientId(jsonObject.getString("production_client_id"));
            }
            for (int i=0;i<jsonArray1.length();i++){
                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                model.setStripeSecretKey(jsonObject.getString("secret_live_key"));
                model.setStripePublicKey(jsonObject.getString("public_live_key"));
            }
            addContainer.setAddUserPaymentData(model);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}