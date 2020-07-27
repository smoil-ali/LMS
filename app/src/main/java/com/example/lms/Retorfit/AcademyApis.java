package com.example.lms.Retorfit;

import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseCountResponse;
import com.example.lms.Model.CourseResponse;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.DashboardResponse;
import com.example.lms.Model.InstructorResponse;
import com.example.lms.Model.LoginResponse;
import com.example.lms.Model.ResetPassword;
import com.example.lms.Model.StudentResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AcademyApis {
    //Login
    //http://lms.amnaikhlaq.com/api/index.php?action=login&email=amnaikhlaq1@gmail.com&password=123
    @GET("?action=login")
    Call<LoginResponse> getLoginResponse(@Query("email") String email,@Query("password") String password);

    //Course Count Status: (Active, Pending, Free, Paid)
    //http://lms.amnaikhlaq.com/api/index.php?action=course_count
    @GET("?action=course_count")
    Call<CourseCountResponse> getCourseCount();

    //Reset Password
    //http://lms.amnaikhlaq.com/api/index.php?action=resetpassword&id=1&password=12345
    @GET("?action=resetpassword")
    Call<ResetPassword> resetPassword(@Query("id") String id,@Query("password") String password);

    //Enrollment History
    //http://lms.amnaikhlaq.com/api/index.php?action=stdEnrolHistoryById
    @GET("?action=stdEnrolHistoryById")
    Call<EnrollmentHistoryResponse> getEnrollmentHistory();


    //Enroll history By id
    //http://lms.amnaikhlaq.com/api/index.php?action=stdEnrolHistoryById&id=2
    @GET("?action=stdEnrolHistoryById")
    Call<EnrollmentHistoryResponse> getEnrollmentHistoryById(@Query("id")String id);

    //Category List
    //http://lms.amnaikhlaq.com/api/index.php/?action=categoryParent
    @GET("?action=categoryParent")
    Call<CategoryResponse> getCategories();

    //Add Parent Category
    //http://lms.amnaikhlaq.com/api/index.php?action=addCategory&name=Webdesign&slug=webdevolpment&code=w2oii292&date_added=21341412
    @GET("?action=addCategory")
    Call<CategoryResponse> addParentCategory(@Query("name") String name,
                                             @Query("slug") String slug,
                                             @Query("code") String code,
                                             @Query("date_added") String date_added);

    // Add sub Category
    //http://lms.amnaikhlaq.com/api/index.php?action=addCategory&name=Webdesign&slug=webdevolpment&code=w2oii292&date_added=21341412&parentid=
    @GET("?action=addCategory")
    Call<CategoryResponse> addSubCategory(@Query("name") String name,
                                             @Query("slug") String slug,
                                             @Query("code") String code,
                                             @Query("date_added") String date_added,
                                                @Query("parentid")String parentid);


    //Category List using RxJava
    //http://lms.amnaikhlaq.com/api/index.php/?action=categoryParent
    @GET("?action=categoryParent")
    Observable<CategoryResponse> getCategoriesUsingRxJava();


    //SubCategory list by id
    //http://lms.amnaikhlaq.com/api/index.php?action=subCategoryByParentId&id=1
    @GET("?action=subCategoryByParentId")
    Call<CategoryResponse> getSubCategories(@Query("id") String id);

    //Delete By ID or Code
    //http://lms.amnaikhlaq.com/api/index.php?action=deleteCategory&id=3
    @GET("?action=deleteCategory")
    Call<CategoryResponse> deleteCategory(@Query("id")String id);

    //Category Update By ID only
    //http://lms.amnaikhlaq.com/api/index.php?action=updateCategory&name=moiz&parent=0&slug=moixx&id=1
    @GET("?action=updateCategory")
    Call<CategoryResponse> updateCategory(@Query("name")String name,
                                          @Query("parent")String parent,
                                          @Query("slug")String slug,
                                          @Query("id")String id);

    //Fetch User Data base on ID and email

    //http://lms.amnaikhlaq.com/api/index.php?action=fetchUser&id=atamuhiuldin@gmail.com

    //Fetch All courses and course by id
    //http://lms.amnaikhlaq.com/api/index.php?action=courseList
    @GET("?action=courseList")
    Call<CourseResponse> getCourseResponse();

    //Student List
    //http://lms.amnaikhlaq.com/api/index.php?action=allUser&role=student
    @GET("?action=allUser")
    Call<StudentResponse> getStudentList(@Query("role") String role);



    //Instructor List
    //http://lms.amnaikhlaq.com/api/index.php?action=allUser&role=instructor
    @GET("?action=allUser")
    Call<InstructorResponse> getInstructorList(@Query("role") String role);


    //By Course ID
    //http://lms.amnaikhlaq.com/api/index.php?action=courseList&id=123


    //Count All Api for dashboard
    //http://lms.amnaikhlaq.com/api/index.php?action=studentList

    @GET("?action=count_all")
    Call<DashboardResponse> getDashBoardResponse();

    //Delete course by id
    //http://lms.amnaikhlaq.com/api/index.php?action=deleteCourse&id=3

    @GET("?action=deleteCourse")
    Call<CourseResponse> deleteCourse(@Query("id") String id);



}
