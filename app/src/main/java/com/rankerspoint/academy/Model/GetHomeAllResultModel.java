package com.rankerspoint.academy.Model;

public class GetHomeAllResultModel {

    private String MockExamResultId;
    private String MExamId;
    private String UserId;
    private String Correct;
    private String Wrong;
    private String Score;
    private String Skipped;
    private String TotalQuestion;
    private String PassedStatus;
    private String SeriesId;
    private String CategoryId;
    private String CourseId;
    private String Title;

    public String getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(String accuracy) {
        Accuracy = accuracy;
    }

    private String Accuracy;

    public String getTotalMarks() {
        return TotalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        TotalMarks = totalMarks;
    }

    private String TotalMarks;

    public String getMAddDate() {
        return MAddDate;
    }

    public void setMAddDate(String MAddDate) {
        this.MAddDate = MAddDate;
    }

    private String MAddDate;

    public GetHomeAllResultModel(String mockExamResultId, String MExamId, String userId, String correct, String wrong, String score, String skipped, String totalQuestion, String passedStatus, String seriesId, String categoryId, String courseId, String title,String maAddDate,String totalMarks,String accuracy) {
        MockExamResultId = mockExamResultId;
        this.MExamId = MExamId;
        UserId = userId;
        Correct = correct;
        Wrong = wrong;
        Score = score;
        Skipped = skipped;
        TotalQuestion = totalQuestion;
        PassedStatus = passedStatus;
        SeriesId = seriesId;
        CategoryId = categoryId;
        CourseId = courseId;
        Title = title;
        MAddDate=maAddDate;
        TotalMarks=totalMarks;
        Accuracy=accuracy;
    }

    public String getMockExamResultId() {
        return MockExamResultId;
    }

    public void setMockExamResultId(String mockExamResultId) {
        MockExamResultId = mockExamResultId;
    }

    public String getMExamId() {
        return MExamId;
    }

    public void setMExamId(String MExamId) {
        this.MExamId = MExamId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCorrect() {
        return Correct;
    }

    public void setCorrect(String correct) {
        Correct = correct;
    }

    public String getWrong() {
        return Wrong;
    }

    public void setWrong(String wrong) {
        Wrong = wrong;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getSkipped() {
        return Skipped;
    }

    public void setSkipped(String skipped) {
        Skipped = skipped;
    }

    public String getTotalQuestion() {
        return TotalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        TotalQuestion = totalQuestion;
    }

    public String getPassedStatus() {
        return PassedStatus;
    }

    public void setPassedStatus(String passedStatus) {
        PassedStatus = passedStatus;
    }

    public String getSeriesId() {
        return SeriesId;
    }

    public void setSeriesId(String seriesId) {
        SeriesId = seriesId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
