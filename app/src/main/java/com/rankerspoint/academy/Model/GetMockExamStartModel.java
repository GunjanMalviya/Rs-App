package com.rankerspoint.academy.Model;

public class GetMockExamStartModel {
    public GetMockExamStartModel(String examId,String subjectId, String seriesId, String title, String duration, String totalMarks, String endDate, String feeStatus,String feeAmount,String courseId) {
        ExamId = examId;
        SeriesId = seriesId;
        Title = title;
        Duration = duration;
        TotalMarks = totalMarks;
        EndDate = endDate;
        FeeStatus = feeStatus;
        SubjectId = subjectId;
        FeeAmount=feeAmount;
        CourseId=courseId;
    }

    private String ExamId;

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    private String SubjectId;

    public String getExamId() {
        return ExamId;
    }

    public void setExamId(String examId) {
        ExamId = examId;
    }

    public String getSeriesId() {
        return SeriesId;
    }

    public void setSeriesId(String seriesId) {
        SeriesId = seriesId;
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

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getFeeStatus() {
        return FeeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        FeeStatus = feeStatus;
    }

    private String SeriesId;
    private String Title;
    private String Duration;
    private String TotalMarks;
    private String EndDate;
    private String FeeStatus;

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    private String CourseId;

    public String getFeeAmount() {
        return FeeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        FeeAmount = feeAmount;
    }

    private String FeeAmount;
}
