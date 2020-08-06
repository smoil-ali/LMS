package com.example.lms.Model;

import android.graphics.Path;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Optional;

public class CourseData {
    private String id;

    private String title;

    private String short_description;

    private String description;

    private String outcomes;

    private String language;

    private String category_id;

    private String sub_category_id;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Section getSection() {
        return Optional.ofNullable(section).orElse(new Section());
    }

    public void setSection(Section section) {
        this.section = section;
    }

    private Section section;

    private String requirements;

    private String price;

    private String discount_flag;

    private String discounted_price;

    private String level;

    private String user_id;

    private String thumbnail;

    private String video_url;

    private String date_added;

    private String last_modified;

    private String visibility;

    private String is_top_course;

    private String is_admin;

    private String status;

    private String course_overview_provider;

    private String meta_keywords;

    private String meta_description;

    private String is_free_course;

    private CategoryData category;

    private Sub_category sub_category;

    private Lesson lesson;

    private Enrollment enrollment;

    private Instructor instructor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(String outcomes) {
        this.outcomes = outcomes;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }



    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount_flag() {
        return discount_flag;
    }

    public void setDiscount_flag(String discount_flag) {
        this.discount_flag = discount_flag;
    }

    public String getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(String discounted_price) {
        this.discounted_price = discounted_price;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getIs_top_course() {
        return is_top_course;
    }

    public void setIs_top_course(String is_top_course) {
        this.is_top_course = is_top_course;
    }

    public String getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getStatus() {
        return Optional.ofNullable(status).orElse("CGIT");
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourse_overview_provider() {
        return course_overview_provider;
    }

    public void setCourse_overview_provider(String course_overview_provider) {
        this.course_overview_provider = course_overview_provider;
    }

    public String getMeta_keywords() {
        return meta_keywords;
    }

    public void setMeta_keywords(String meta_keywords) {
        this.meta_keywords = meta_keywords;
    }

    public String getMeta_description() {
        return meta_description;
    }

    public void setMeta_description(String meta_description) {
        this.meta_description = meta_description;
    }

    public String getIs_free_course() {
        return is_free_course;
    }

    public void setIs_free_course(String is_free_course) {
        this.is_free_course = is_free_course;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CategoryData getCategory() {
        return Optional.ofNullable(category).orElse(new CategoryData());
    }

    public void setCategory(CategoryData category) {
        this.category = category;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Sub_category getSub_category() {
        return Optional.ofNullable(sub_category).orElse(new Sub_category());
    }

    public void setSub_category(Sub_category sub_category) {
        this.sub_category = sub_category;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Lesson getLesson() {
        return Optional.ofNullable(lesson).orElse(new Lesson());
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Enrollment getEnrollment() {
        return Optional.ofNullable(enrollment).orElse(new Enrollment());
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Instructor getInstructor() {
        return Optional.ofNullable(instructor).orElse(new Instructor());
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}
