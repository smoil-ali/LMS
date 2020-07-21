package com.example.lms.Retorfit;

import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseCountResponse;
import com.example.lms.Model.CourseResponse;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.DashboardResponse;
import com.example.lms.Model.LoginResponse;
import com.example.lms.Model.ResetPassword;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AcademyApis {
    //Login
    //http://developers.cgitsoft.com/lms/index.php?action=login&email=amnaikhlaq1@gmail.com&password=123
    @GET("?action=login")
    Call<LoginResponse> getLoginResponse(@Query("email") String email,@Query("password") String password);

    //Course Count Status: (Active, Pending, Free, Paid)
    //http://developers.cgitsoft.com/lms/index.php?action=course_count
    @GET("?action=course_count")
    Call<CourseCountResponse> getCourseCount();

    //Reset Password
    //http://developers.cgitsoft.com/lms/index.php?action=resetpassword&id=1&password=12345
    @GET("?action=resetpassword")
    Call<ResetPassword> resetPassword(@Query("id") String id,@Query("password") String password);

    //Enrollment History
    //http://developers.cgitsoft.com/lms/index.php?action=enrolhistory
    @GET("?action=enrolhistory")
    Call<EnrollmentHistoryResponse> getEnrollmentHistory();

    //Category List
    //http://developers.cgitsoft.com/lms/index.php?action=category
    @GET("?action=category")
    Call<CategoryResponse> getCategories();


    //Category List using RxJava
    //http://developers.cgitsoft.com/lms/index.php?action=category
    @GET("?action=category")
    Observable<CategoryResponse> getCategoriesUsingRxJava();

    //Fetch User Data base on ID and email

    //http://developers.cgitsoft.com/lms/index.php?action=fetchUser&id=atamuhiuldin@gmail.com

    //Fetch All courses and course by id
    //http://developers.cgitsoft.com/lms/index.php?action=courseList
    @GET("?action=courseList")
    Call<CourseResponse> getCourseResponse();


    //By Course ID
    //http://developers.cgitsoft.com/lms/index.php?action=courseList&id=123


    //Count All Api for dashboard
    //http://developers.cgitsoft.com/lms/index.php?action=studentList

    @GET("?action=count_all")
    Call<DashboardResponse> getDashBoardResponse();

}
