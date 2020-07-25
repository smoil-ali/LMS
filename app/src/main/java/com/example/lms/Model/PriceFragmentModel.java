package com.example.lms.Model;

public class PriceFragmentModel {
    String ifFreeCourse = "false";
    String CoursePrice = "";
    String ifDiscount = "false";
    String DiscountPrice = "";

    public PriceFragmentModel() {
    }

    public String getIfFreeCourse() {
        return ifFreeCourse;
    }

    public void setIfFreeCourse(String ifFreeCourse) {
        this.ifFreeCourse = ifFreeCourse;
    }

    public String getCoursePrice() {
        return CoursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        CoursePrice = coursePrice;
    }

    public String getIfDiscount() {
        return ifDiscount;
    }

    public void setIfDiscount(String ifDiscount) {
        this.ifDiscount = ifDiscount;
    }

    public String getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        DiscountPrice = discountPrice;
    }
}
