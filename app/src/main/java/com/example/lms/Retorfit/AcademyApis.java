package com.example.lms.Retorfit;

import android.app.Application;
import android.os.ConditionVariable;
import android.util.Log;

import com.example.lms.Model.AllCurrencies;
import com.example.lms.Model.ApplicationResponse;
import com.example.lms.Model.ApprovedApplication;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Model.CourseCount;
import com.example.lms.Model.CourseCountResponse;
import com.example.lms.Model.CourseResponse;
import com.example.lms.Model.CourseUpdateResponse;
import com.example.lms.Model.EnrollmentHistoryResponse;
import com.example.lms.Model.DashboardResponse;
import com.example.lms.Model.InstructorResponse;
import com.example.lms.Model.LessonResponse;
import com.example.lms.Model.LoginResponse;
import com.example.lms.Model.ResetPassword;
import com.example.lms.Model.SectionResponse;
import com.example.lms.Model.SettingsResponse;
import com.example.lms.Model.StudentResponse;
import com.example.lms.Model.WebsiteResponse;

import java.util.List;

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



    //Add Course
    //http://lms.amnaikhlaq.com/api/index.php?action=addCourse%20&title=%22course%20xxx%22%20&short_description=%22it%20is%20f**king%20awesome%22%20&description=%22faklsjdfklasdklf%22%20&outcomes%20(list)=%22list%22%20&language=%22english%22%20&level=%22xxx%22%20&is_top_course=%22true%22%20&category_id=%221%22%20&requirements%20(list)=%22list,list2%22%20&is_free_course=%22true%22%20&discount_flag=%22true%22%20&price=%22200%22%20&discount_price=%2210%%22%20&course_overview_provider=%22sklfasjf%22%20&video_url=%22sdjfklasjdf%22%20&meta_keywords%20(list)=%22lsit%20,lisjt,lis%22%20&meta_description=%22jflkasjkfjas%22
    @GET("?action=addCourse")
    Call<CourseUpdateResponse>  addCourse(@Query("title")String title,
                                          @Query("short_description")String short_description,
                                          @Query("description")String description,
                                          @Query("outcomes")List<String> outcomes,
                                          @Query("language")String language,
                                          @Query("level")String level,
                                          @Query("is_top_course")String is_top_course,
                                          @Query("category_id")String category_id,
                                          @Query("requirements")List<String> requirements,
                                          @Query("is_free_course")String is_free_course,
                                          @Query("discount_flag")String discount_flag,
                                          @Query("price")String price,
                                          @Query("discounted_price")String discounted_price,
                                          @Query("course_overview_provider")String course_overview_provider,
                                          @Query("video_url")String video_url,
                                          @Query("meta_keywords")List<String> meta_keywords,
                                          @Query("meta_description")String meta_description);

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


    //User Profile Update
    //http://lms.amnaikhlaq.com/api/index.php?action=updateProfile&first_name=Shoaib&last_name=Akmal&email=amnaikhlaq1@gmail.com&facebook=facebook.com&twitter=twitter.com&title=Mr&biography=this%20is%20bio&id=1
    @GET("?action=updateProfile")
    Call<LoginResponse> updateUserProfile(@Query("first_name")String first_name,
                                          @Query("last_name")String last_name,
                                          @Query("email")String email,
                                          @Query("facebook")String facebook,
                                          @Query("twitter")String twitter,
                                          @Query("title")String title,
                                          @Query("biography")String biography,
                                          @Query("id")String id);

    //Fetch User Data base on ID and email

    //http://lms.amnaikhlaq.com/api/index.php?action=fetchUser&id=atamuhiuldin@gmail.com

    //Fetch All courses and course by id
    //http://lms.amnaikhlaq.com/api/index.php?action=courseList
    @GET("?action=courseList")
    Call<CourseResponse> getCourseResponse();

    //Student List
    //http://lms.amnaikhlaq.com/api/index.php?action=allUser&role=student
    @GET("?action=userList")
    Call<StudentResponse> getStudentList(@Query("role") String role);



    //Instructor List
    //http://lms.amnaikhlaq.com/api/index.php?action=allUser&role=instructor
    @GET("?action=userList")
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

    //Update Profile
    //http://lms.amnaikhlaq.com/api/index.php?action=updateProfile&first_name=Shoaib&last_name=Akmal&email=amnaikhlaq1@gmail.com&facebook=facebook.com&twitter=twitter.com&title=Mr&linkedin=link&biography=this%20is%20bio&id=1
    @GET("?action=updateProfile")
    Observable<LoginResponse> updateProfile(@Query("first_name")String first_name,
                                       @Query("last_name")String last_name,
                                       @Query("email")String email,
                                       @Query("facebook")String facebook,
                                       @Query("twitter")String twitter,
                                       @Query("title")String title,
                                       @Query("linkedin")String linkedin,
                                       @Query("biography")String biography,
                                       @Query("id")String id);
    //DELETE USER
    //http://lms.amnaikhlaq.com/api/index.php?action=delete&id=5
    @GET("?action=delete")
    Call<StudentResponse> deleteStudent(@Query("id")String id);


    //ADD STUDENT
    //Stripe_keys : public_key and secret key
    //Paypal keys proclient_id and prosecret_key

    //http://lms.amnaikhlaq.com/api/index.php?action=addUser&type=student&first_name=waleed&last_name=usama&biography=vavasvasav&email=emai&password=12314&proclient_id=13123&prosecret_key=121312&secret_key=121231221&public_key=213213&date_added=1596100613&title=Mr&facebook=sadasd&twitter=twitter&linkedin=sadasd
    @GET("?action=addUser")
    Observable<StudentResponse> AddUser(@Query("type")String type,
                                           @Query("first_name")String first_name,
                                           @Query("last_name")String last_name,
                                           @Query("biography")String biography,
                                           @Query("email")String email,
                                           @Query("password")String password,
                                           @Query("proclient_id")String proclient_id,
                                           @Query("prosecret_key")String prosecret_key,
                                           @Query("secret_key")String secret_key,
                                           @Query("public_key")String public_key,
                                           @Query("date_added")String date_added,
                                           @Query("title")String title,
                                           @Query("facebook")String facebook,
                                           @Query("twitter")String twitter,
                                           @Query("linkedin")String linkedin);




    //http://lms.amnaikhlaq.com/api/index.php?action=addUser&type=student&first_name=waleed&last_name=usama&biography=vavasvasav&email=emai&password=12314&proclient_id=13123&prosecret_key=121312&secret_key=121231221&public_key=213213&date_added=1596100613&title=Mr&facebook=sadasd&twitter=twitter&linkedin=sadasd
    @GET("?action=addUser")
    Call<StudentResponse> callUser(@Query("type")String type,
                                        @Query("first_name")String first_name,
                                        @Query("last_name")String last_name,
                                        @Query("biography")String biography,
                                        @Query("email")String email,
                                        @Query("password")String password,
                                        @Query("proclient_id")String proclient_id,
                                        @Query("prosecret_key")String prosecret_key,
                                        @Query("secret_key")String secret_key,
                                        @Query("public_key")String public_key,
                                        @Query("date_added")String date_added,
                                        @Query("title")String title,
                                        @Query("facebook")String facebook,
                                        @Query("twitter")String twitter,
                                        @Query("linkedin")String linkedin);

    //http://lms.amnaikhlaq.com/api/index.php?action=delete&id=5

    @GET("?action=delete")
    Call<InstructorResponse> deleteInstructor(@Query("id") String id);


    //update system settings
    //http://lms.amnaikhlaq.com/api/index.php?action=settings&type=update&key=system_email&value=email.com

    @GET("?action=settings&type=update")
    Call<SettingsResponse> updateSettings(@Query("key") String key,
                                          @Query("value") String value);

    //website settings
    //http://lms.amnaikhlaq.com/api/index.php?action=websettings&type=update&key=banner_title&value=banner

    @GET("?action=websettings&type=update")
    Call<SettingsResponse> updateWebSettings(@Query("key") String key,
                                          @Query("value") String value);

    //window settings fetach
    //http://lms.amnaikhlaq.com/api/index.php?action=websettings&type=fetch

    @GET("?action=websettings&type=fetch")
    Call<WebsiteResponse> getWebsiteData();

    //Fetch settings data
    //http://lms.amnaikhlaq.com/api/index.php?action=settings&type=fetch
    @GET("?action=settings&type=fetch")
    Call<SettingsResponse> getSettingsData();

    //all currency
    // http://lms.amnaikhlaq.com/api/index.php?action=settings&currency=all
    @GET("?action=settings&currency=all")
    Call<AllCurrencies> getAllCurrencies();

    //paypal currency
    //http://lms.amnaikhlaq.com/api/index.php?action=settings&currency=paypal
    @GET("?action=settings&currency=paypal")
    Call<AllCurrencies> getPaypalCurrencies();

    //stripe Currency
    //http://lms.amnaikhlaq.com/api/index.php?action=settings&currency=stripe
    @GET("?action=settings&currency=stripe")
    Call<AllCurrencies> getStripeCurrencies();

    //update all currency
    //http://lms.amnaikhlaq.com/api/index.php?action=settings&type=update&key=system_email&value=email.com



    //update paypal currency
    //http://lms.amnaikhlaq.com/api/index.php?action=settings&type=update&key=paypal&active=act&mode=1&sandbox_client_id=null&sandbox_secret_key=null&production_client_id=null&production_secret_key=null

    @GET("?action=settings&type=update&key=paypal")
    Call<AllCurrencies> updatePaypalSettings(@Query("active") String active,
                                               @Query("mode") String mode,
                                               @Query("sandbox_client_id") String sandbox_client_id,
                                               @Query("sandbox_secret_key") String sandbox_secret_key,
                                               @Query("production_client_id") String production_client_id,
                                               @Query("production_secret_key") String production_secret_key
                                               );

    //update stripe keys
    //http://lms.amnaikhlaq.com/api/index.php?action=settings&type=update&key=stripe_key&active=1&testmode=on&public_key=null&secret_key=null&public_live_key=null&secret_live_key=null

    @GET("?action=settings&type=update&key=stripe_keys")
    Call<AllCurrencies> updateStripeSettings(@Query("active") String active,
                                             @Query("testmode") String testmode,
                                             @Query("public_key") String public_key,
                                             @Query("secret_key") String secret_key,
                                             @Query("public_live_key") String public_live_key,
                                             @Query("secret_live_key") String secret_live_key
                                             );




    //approved application instructor
    //http://lms.amnaikhlaq.com/api/index.php?action=applications&type=fetch&fetch=approved

    @GET("?action=applications&type=fetch&fetch=approved")
    Call<ApplicationResponse> getApprovedApplication();

    //pending application instructor
    //http://lms.amnaikhlaq.com/api/index.php?action=applications&type=fetch&fetch=pending

    @GET("?action=applications&type=fetch&fetch=pending")
    Call<ApplicationResponse> getPendingApplication();

    //Enroll Student
    //http://lms.amnaikhlaq.com/api/index.php?action=enrolStudent&type=insert&userid=2&courseid=4
    @GET("?action=enrolStudent")
    Call<StudentResponse> enrollStudent(@Query("type")String type,
                                        @Query("userid")String userid,
                                        @Query("courseid")String courseid);

    //Enroll History by date range
    //http://lms.amnaikhlaq.com/api/index.php?action=enrolbydate&from=2020-08-04&to=2020-08-04
    @GET("?action=enrolbydate")
    Call<EnrollmentHistoryResponse> getEnrollHistoryByDateRange(@Query("from")String from,
                                                                @Query("to")String to);
    //Delete History
    //http://lms.amnaikhlaq.com/api/index.php?action=enrolStudent&type=delete&id=2
    @GET("?action=enrolStudent&type=delete")
    Call<EnrollmentHistoryResponse> deleteEnrollHistory(@Query("id")String id);

    //USER UPDATE
    //http://lms.amnaikhlaq.com/api/index.php?action=updateUser&id=100&type=instructor&first_name=hammid&
    // last_name=usama&biography=vavasvasav&email=emai&proclient_id=13123&prosecret_key=121312&secret_key=121231221
    // &public_key=213213&title=Mr&facebook=sadasd&twitter=twitter&linkedin=sadasd
    @GET("?action=updateUser")
    Call<StudentResponse> updateUser(@Query("id")String id,
                                     @Query("type")String type,
                                     @Query("first_name")String first_name,
                                     @Query("last_name")String last_name,
                                     @Query("biography")String biography,
                                     @Query("email")String email,
                                     @Query("proclient_id")String proclient_id,
                                     @Query("prosecret_key")String prosecret_key,
                                     @Query("secret_key")String secret_key,
                                     @Query("public_key")String public_key,
                                     @Query("title")String title,
                                     @Query("facebook")String facebook,
                                     @Query("twitter")String twitter,
                                     @Query("linkedin")String linkedin);

    //Add Lesson
    //http://lms.amnaikhlaq.com/api/index.php?action=lesson&type=insert&title=testing&duration=03:03:02&course_id=2&
    // section_id=3&video_url=www.da.com&lesson_type=video&attachment_type=url&summary=summary&
    // video_type_for_mobile_application=html5&video_url_for_mobile_application=https://www.youtube.com/watch?v=WhR2SnhNbGI&t=153s
    // &duration_for_mobile_application=02:35:45&video_type=””
    @GET("?action=lesson&type=insert")
    Call<LessonResponse> addLesson(@Query("title") String title,
                                   @Query("duration") String duration,
                                   @Query("course_id") String course_id,
                                   @Query("section_id") String section_id,
                                   @Query("video_url") String video_url,
                                   @Query("lesson_type") String lesson_type,
                                   @Query("attachment_type") String attachment_type,
                                   @Query("summary") String summary,
                                   @Query("video_type_for_mobile_application") String video_type_for_mobile_application,
                                   @Query("video_url_for_mobile_application") String video_url_for_mobile_application,
                                   @Query("duration_for_mobile_application") String duration_for_mobile_application,
                                   @Query("video_type") String video_type);
    //Add Section
    //http://lms.amnaikhlaq.com/api/index.php?action=section&type=insert&title=by%20api&course_id=11
    @GET("?action=section&type=insert")
    Call<SectionResponse> addSection(@Query("title") String title,
                                     @Query("course_id") String course_id);

    //Retrive Lesson  by Section_Id
    //http://lms.amnaikhlaq.com/api/index.php?action=lesson&type=fetchById&section_id=7
    @GET("?action=lesson&type=fetchById")
    Call<LessonResponse> getLessonById(@Query("section_id") String section_id);

    //Delete Section
    //http://lms.amnaikhlaq.com/api/index.php?action=section&type=delete&id=9
    @GET("?action=section&type=delete")
    Call<SectionResponse> deleteSection(@Query("id") String id);

    //Update Section
    //http://lms.amnaikhlaq.com/api/index.php?action=section&type=update&id=9&title=update%20by%20api’
    @GET("?action=section&type=update")
    Call<SectionResponse> updateSection(@Query("id") String id,
                                        @Query("title") String title);

    //Update Lesson by id
    //http://lms.amnaikhlaq.com/api/index.php?action=lesson&type=update&id=3&title=title&course_id=1&section_id=2&summary=summary
    @GET("?action=lesson&type=update")
    Call<LessonResponse> updateLesson(@Query("id") String id,
                                      @Query("title") String title,
                                      @Query("course_id") String course_id,
                                      @Query("section_id") String section_id,
                                      @Query("summary") String summary);

    //Delete Lesson by id
    //http://lms.amnaikhlaq.com/api/index.php?action=lesson&type=delete&id=4
    @GET("?action=lesson&type=delete")
    Call<LessonResponse> deleteLesson(@Query("id") String id);


    //Update Course
    //http://lms.amnaikhlaq.com/api/index.php?action=updateCourse&id=4&title=java&short_description=java&description=freee
    // &outcomes=aa&language=english&level=beginner&is_top_course=0&category_id=1&requirements=sddd&is_free_course=null
    // &discount_flag=1&price=100&discounted_price=11&course_overview_provider=name&video_url=url&meta_keywords=list&meta_description=sadkas
    @GET("?action=updateCourse")
    Call<CourseUpdateResponse>  updateCourse(@Query("id") String id,
                                             @Query("title")String title,
                                             @Query("short_description")String short_description,
                                             @Query("description")String description,
                                             @Query("outcomes")List<String> outcomes,
                                             @Query("language")String language,
                                             @Query("level")String level,
                                             @Query("is_top_course")String is_top_course,
                                             @Query("category_id")String category_id,
                                             @Query("requirements")List<String> requirements,
                                             @Query("is_free_course")String is_free_course,
                                             @Query("discount_flag")String discount_flag,
                                             @Query("price")String price,
                                             @Query("discounted_price")String discounted_price,
                                             @Query("course_overview_provider")String course_overview_provider,
                                             @Query("video_url")String video_url,
                                             @Query("meta_keywords")List<String> meta_keywords,
                                             @Query("meta_description")String meta_description);

    //Retrive Section DAta
    //http://lms.amnaikhlaq.com/api/index.php?action=section&type=fetchAll&course_id=11
    @GET("?action=section&type=fetchAll")
    Call<SectionResponse> getSectionData(@Query("course_id") String course_id);

}
