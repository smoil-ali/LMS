package com.example.lms.Model;

public class AddLessonModel {
    String title;
    String section_id;
    String course_id;
    String provider_type;
    String lesson_provider;
    String web_video_url;
    String web_duration;
    String mob_video_url;
    String mob_duration;
    String summary;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getProvider_type() {
        return provider_type;
    }

    public void setProvider_type(String provider_type) {
        this.provider_type = provider_type;
    }

    public String getLesson_provider() {
        return lesson_provider;
    }

    public void setLesson_provider(String lesson_provider) {
        this.lesson_provider = lesson_provider;
    }

    public String getWeb_video_url() {
        return web_video_url;
    }

    public void setWeb_video_url(String web_video_url) {
        this.web_video_url = web_video_url;
    }

    public String getWeb_duration() {
        return web_duration;
    }

    public void setWeb_duration(String web_duration) {
        this.web_duration = web_duration;
    }

    public String getMob_video_url() {
        return mob_video_url;
    }

    public void setMob_video_url(String mob_video_url) {
        this.mob_video_url = mob_video_url;
    }

    public String getMob_duration() {
        return mob_duration;
    }

    public void setMob_duration(String mob_duration) {
        this.mob_duration = mob_duration;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
