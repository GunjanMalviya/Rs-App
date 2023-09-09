package com.rankerspoint.academy.Model;

public class GetAllTopicIndividual {

    public GetAllTopicIndividual(String videoId, String categoryId, String subCategoryId, String courseId, String subjectId, String chapterId, String topicId, String name, String details, String type, String pic, String videoLink1, String feeStatus,String startDate) {
        VideoId = videoId;
        CategoryId = categoryId;
        SubCategoryId = subCategoryId;
        CourseId = courseId;
        SubjectId = subjectId;
        ChapterId = chapterId;
        TopicId = topicId;
        Name = name;
        Details = details;
        Type = type;
        Pic = pic;
        VideoLink1 = videoLink1;
        FeeStatus = feeStatus;
        StartDate = startDate;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
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

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    public String getChapterId() {
        return ChapterId;
    }

    public void setChapterId(String chapterId) {
        ChapterId = chapterId;
    }

    public String getTopicId() {
        return TopicId;
    }

    public void setTopicId(String topicId) {
        TopicId = topicId;
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getVideoLink1() {
        return VideoLink1;
    }

    public void setVideoLink1(String videoLink1) {
        VideoLink1 = videoLink1;
    }

    public String getFeeStatus() {
        return FeeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        FeeStatus = feeStatus;
    }

    private  String VideoId;
    private  String CategoryId;
    private  String SubCategoryId;
    private  String CourseId;
    private  String SubjectId;
    private  String ChapterId;
    private  String TopicId;
    private  String Name;
    private  String Details;
    private  String Type;
    private  String Pic;
    private  String VideoLink1;
    private  String FeeStatus;

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    private  String StartDate;






}
