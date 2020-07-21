package com.example.lms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.lms.Listener.ResetListener;
import com.example.lms.Model.SharedPref;
import com.example.lms.Model.Utils;
import com.example.lms.activity.AddCategory;
import com.example.lms.activity.Login;
import com.example.lms.databinding.ActivityMainBinding;
import com.example.lms.databinding.AppBarBinding;
import com.example.lms.dialogs.ResetDialog;
import com.example.lms.ui.HomeFragment;
import com.example.lms.ui.addons.AddonManagerFragment;
import com.example.lms.ui.addons.AvailableAddonsFragment;
import com.example.lms.ui.categories.CategoriesFragment;
import com.example.lms.ui.courses.CoursesFragment;
import com.example.lms.ui.dashboard.DashboardFragment;
import com.example.lms.ui.enrolment.EnrolHistoryFragment;
import com.example.lms.ui.instructors.InstructorApplicationFragment;
import com.example.lms.ui.instructors.InstructorListFragment;
import com.example.lms.ui.instructors.InstructorPayoutFragment;
import com.example.lms.ui.instructors.InstructorSettingsFragment;
import com.example.lms.ui.message.MessageFragment;
import com.example.lms.ui.report.AdminRevenueFragment;
import com.example.lms.ui.report.InstructorRevenueFragment;
import com.example.lms.ui.settings.AboutFragment;
import com.example.lms.ui.settings.LanguageSettingsFragment;
import com.example.lms.ui.settings.PaymentSettingsFragment;
import com.example.lms.ui.settings.SmtpSettingsFragment;
import com.example.lms.ui.settings.SystemSettingsFragment;
import com.example.lms.ui.settings.ThemeSettingsFragment;
import com.example.lms.ui.settings.WebsiteSettingFragment;
import com.example.lms.ui.students.StudentFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements ResetListener {

    private ActivityMainBinding binding;
    private AppBarBinding appBarBinding;
    int flag =0;
    Toolbar toolbar;
    ResetDialog resetDialog = new ResetDialog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        appBarBinding=AppBarBinding.inflate(getLayoutInflater());
        setSupportActionBar(appBarBinding.toolbar);
        getSupportActionBar().setTitle("LMS");
        View view=binding.getRoot();
        setContentView(view);
        resetDialog.setResetListener(this);
        setBottomNavigation();
        sideNavigation();


    }

    private void sideNavigation(){
        binding.floatingNavigationView.setCheckedItem(R.id.nav_home);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.hostFragment,new HomeFragment()).commit();
        binding.floatingNavigationView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.floatingNavigationView.open();
            }
        });
        binding.floatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment fragment=new Fragment();
                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment=new HomeFragment();
                        break;
                    case R.id.nav_dashboard:
                        fragment=new DashboardFragment();
                        break;
                    case R.id.nav_viewCategories:
                        fragment=new CategoriesFragment();
                        break;
                    case R.id.add:

                        break;
                    case R.id.nav_viewCourses:
                        fragment=new CoursesFragment();
                        break;
                    case R.id.nav_addCourses:

                        break;
                    case R.id.nav_instructorList:
                        fragment=new InstructorListFragment();
                        break;
                    case R.id.nav_instructorPayout:
                        fragment=new InstructorPayoutFragment();
                        break;
                    case R.id.nav_instructorSettings:
                        fragment=new InstructorSettingsFragment();
                        break;
                    case R.id.nav_InstructorApplication:
                        fragment=new InstructorApplicationFragment();
                        break;
                    case R.id.nav_student:
                        fragment=new StudentFragment();
                        break;
                    case R.id.nav_enrolHistory:
                        fragment=new EnrolHistoryFragment();
                        break;
                    case R.id.nav_enrolStudent:

                        break;
                    case R.id.nav_adminRevenue:
                        fragment=new AdminRevenueFragment();
                        break;
                    case R.id.nav_instructorRevenue:
                        fragment=new InstructorRevenueFragment();
                        break;
                    case R.id.nav_message:
                        fragment=new MessageFragment();
                        break;
                    case R.id.nav_addonManager:
                        fragment=new AddonManagerFragment();
                        break;
                    case R.id.nav_availableAddons:
                        fragment=new AvailableAddonsFragment();
                        break;
                    case R.id.nav_systemSetting:
                        fragment=new SystemSettingsFragment();
                        break;
                    case R.id.nav_websiteSettings:
                        fragment=new WebsiteSettingFragment();
                        break;
                    case R.id.nav_paymentSettings:
                        fragment=new PaymentSettingsFragment();
                        break;
                    case R.id.nav_languageSettings:
                        fragment=new LanguageSettingsFragment();
                        break;
                    case R.id.nav_smtpSettings:
                        fragment=new SmtpSettingsFragment();
                        break;
                    case R.id.nav_themeSettings:
                        fragment=new ThemeSettingsFragment();
                        break;
                    case R.id.nav_about:
                        Utils.openDialog(getSupportFragmentManager(),resetDialog);
                        //fragment=new AboutFragment();
                        break;
                    case R.id.nav_logout:
                        String id = Utils.getSharedPref(MainActivity.this).getId();
                        SharedPref pref = new SharedPref(id,false);
                        Utils.setSharedPref(MainActivity.this,pref);
                        startActivity(new Intent(MainActivity.this, Login.class));
                        finish();
                        break;
                }
                if (flag==0){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.hostFragment,fragment).commit();
                }

                binding.floatingNavigationView.close();
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.hostFragment);
        if (binding.floatingNavigationView.isOpened()) {
            binding.floatingNavigationView.close();
        }else if (currentFragment instanceof HomeFragment){
           super.onBackPressed();
        }else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.hostFragment,new HomeFragment()).commit();
        }
    }
    private void setBottomNavigation(){

        binding.bottomNavigation.show(2,true);
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.profile_vector));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.home));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.setting_vector));

        binding.bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 1:

                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.hostFragment,new HomeFragment()).commit();
                        break;
                    case 3:

                        break;
                }

            }

        });

        binding.bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

            }
        });

        binding.bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
    }

    @Override
    public void onReset(String msg, String msg2) {
        resetDialog.dismiss();
        new AlertDialog.Builder(this)
                .setTitle(msg2)
                .setMessage(msg)
                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss())).show();
    }
}