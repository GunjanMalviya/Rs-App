package com.rankerspoint.academy.Model;

public class DashBoardFreeQuizModel {


    public String getDCRecordId() {
        return DCRecordId;
    }

    public void setDCRecordId(String DCRecordId) {
        this.DCRecordId = DCRecordId;
    }

    public String getDCCourseId() {
        return DCCourseId;
    }

    public void setDCCourseId(String DCCourseId) {
        this.DCCourseId = DCCourseId;
    }

    public String getDCCourseName() {
        return DCCourseName;
    }

    public void setDCCourseName(String DCCourseName) {
        this.DCCourseName = DCCourseName;
    }

    public String getDCTopicId() {
        return DCTopicId;
    }

    public void setDCTopicId(String DCTopicId) {
        this.DCTopicId = DCTopicId;
    }

    public String getExamId() {
        return ExamId;
    }

    public void setExamId(String examId) {
        ExamId = examId;
    }

    public String getDCContentType() {
        return DCContentType;
    }

    public void setDCContentType(String DCContentType) {
        this.DCContentType = DCContentType;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getFeeStatus() {
        return FeeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        FeeStatus = feeStatus;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    private String DCRecordId;
    private String DCCourseId;
    private String DCCourseName;
    private String DCTopicId;
    private String ExamId;
    private String DCContentType;
    private String CategoryId;
    private String Title;
    private String Duration;
    private String FeeStatus;
    private String Pic;
    private String Description;

    public DashBoardFreeQuizModel(String DCRecordId, String DCCourseId, String DCCourseName, String DCTopicId, String examId, String DCContentType, String categoryId, String title, String duration, String feeStatus, String pic, String description) {
        this.DCRecordId = DCRecordId;
        this.DCCourseId = DCCourseId;
        this.DCCourseName = DCCourseName;
        this.DCTopicId = DCTopicId;
        ExamId = examId;
        this.DCContentType = DCContentType;
        CategoryId = categoryId;
        Title = title;
        Duration = duration;
        FeeStatus = feeStatus;
        Pic = pic;
        Description = description;
    }
}
