package com.rankerspoint.academy.Model;

public class GetAllTopicSyllabusChapModel {
    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    public String getTopicId() {
        return TopicId;
    }

    public void setTopicId(String topicId) {
        TopicId = topicId;
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



    public String getChapterId() {
        return ChapterId;
    }

    public void setChapterId(String chapterId) {
        ChapterId = chapterId;
    }
    private  String SubjectId;
    private  String TopicId;
    private  String ChapterId;
    private  String CourseId;
    private  String CategoryId;
    private  String SubCategoryId;
    private  String Name;

    public GetAllTopicSyllabusChapModel(String subjectId, String topicId,String chapterId, String courseId, String categoryId, String subCategoryId, String name) {
        SubjectId = subjectId;
        TopicId = topicId;
        ChapterId = chapterId;
        CourseId = courseId;
        CategoryId = categoryId;
        SubCategoryId = subCategoryId;
        Name = name;
    }





}
