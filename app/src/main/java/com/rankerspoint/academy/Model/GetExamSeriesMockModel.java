package com.rankerspoint.academy.Model;

public class GetExamSeriesMockModel {
    public GetExamSeriesMockModel(String examId, String seriesId, String title, String duration, String totalMarks, String endDate, String feeStatus) {
        ExamId = examId;
        SeriesId = seriesId;
        Title = title;
        Duration = duration;
        TotalMarks = totalMarks;
        EndDate = endDate;
        FeeStatus = feeStatus;
    }

    private String ExamId;

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
}
