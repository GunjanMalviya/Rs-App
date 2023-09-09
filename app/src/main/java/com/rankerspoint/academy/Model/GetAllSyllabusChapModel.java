package com.rankerspoint.academy.Model;

public class GetAllSyllabusChapModel {
    private  String SubjectId;

    public GetAllSyllabusChapModel(String subjectId, String chapterId, String courseId, String categoryId, String subCategoryId, String name, String details) {
        SubjectId = subjectId;
        ChapterId = chapterId;
        CourseId = courseId;
        CategoryId = categoryId;
        SubCategoryId = subCategoryId;
        Name = name;
        Details = details;
    }

    public String getChapterId() {
        return ChapterId;
    }

    public void setChapterId(String chapterId) {
        ChapterId = chapterId;
    }

    private  String ChapterId;


    private  String CourseId;
    private  String CategoryId;
    private  String SubCategoryId;
    private  String Name;
    private  String Details;

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

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

    public String getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        SubCategoryId = subCategoryId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }
}
