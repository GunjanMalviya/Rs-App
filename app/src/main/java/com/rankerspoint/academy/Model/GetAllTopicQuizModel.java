package com.rankerspoint.academy.Model;

public class GetAllTopicQuizModel {

    public GetAllTopicQuizModel(String DCRecordId, String DCTopicId, String DCTopicName, String DCContentId, String examId, String title, String duration, String totalMarks, String feeStatus) {
        this.DCRecordId = DCRecordId;
        this.DCTopicId = DCTopicId;
        this.DCTopicName = DCTopicName;
        this.DCContentId = DCContentId;
        ExamId = examId;
        Title = title;
        Duration = duration;
        TotalMarks = totalMarks;
        FeeStatus = feeStatus;
    }

    private  String DCRecordId;
    private  String DCTopicId;
    private  String DCTopicName;
    private  String DCContentId;
    private  String ExamId;

    private  String Title;
    private  String Duration;
    private  String TotalMarks;
    private  String FeeStatus;

    public String getDCRecordId() {
        return DCRecordId;
    }

    public void setDCRecordId(String DCRecordId) {
        this.DCRecordId = DCRecordId;
    }

    public String getDCTopicId() {
        return DCTopicId;
    }

    public void setDCTopicId(String DCTopicId) {
        this.DCTopicId = DCTopicId;
    }

    public String getDCTopicName() {
        return DCTopicName;
    }

    public void setDCTopicName(String DCTopicName) {
        this.DCTopicName = DCTopicName;
    }

    public String getDCContentId() {
        return DCContentId;
    }

    public void setDCContentId(String DCContentId) {
        this.DCContentId = DCContentId;
    }

    public String getExamId() {
        return ExamId;
    }

    public void setExamId(String examId) {
        ExamId = examId;
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

    public String getTotalMarks() {
        return TotalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        TotalMarks = totalMarks;
    }

    public String getFeeStatus() {
        return FeeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        FeeStatus = feeStatus;
    }
}
