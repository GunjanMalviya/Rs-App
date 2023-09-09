package com.rankerspoint.academy.Model;

public class HomeNotesPdfModel {


    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCourseCategoryId() {
        return CourseCategoryId;
    }

    public void setCourseCategoryId(String courseCategoryId) {
        CourseCategoryId = courseCategoryId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFeeStatus() {
        return FeeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        FeeStatus = feeStatus;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }

    public HomeNotesPdfModel(String categoryId, String courseCategoryId, String name, String feeStatus, String courseId, String pic1) {
        CategoryId = categoryId;
        CourseCategoryId = courseCategoryId;
        Name = name;
        FeeStatus = feeStatus;
        CourseId = courseId;
        Pic1 = pic1;
    }

    private String CategoryId;
    private String CourseCategoryId;
    private String Name;

    private String FeeStatus;
    private String CourseId;
    private String Pic1;

}
