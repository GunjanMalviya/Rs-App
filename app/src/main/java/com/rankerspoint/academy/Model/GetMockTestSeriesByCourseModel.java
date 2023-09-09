package com.rankerspoint.academy.Model;

public class GetMockTestSeriesByCourseModel {


    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public GetMockTestSeriesByCourseModel(String courseId, String categoryId, String name) {
        CourseId = courseId;
        CategoryId = categoryId;
        Name = name;
    }

    private String CourseId;
    private String CategoryId;
    private String Name;



}
