package com.rankerspoint.academy.Model;

public class GetAllResultsModel {
    public String getExamResultId() {
        return ExamResultId;
    }

    public void setExamResultId(String examResultId) {
        ExamResultId = examResultId;
    }

    public String getExamId() {
        return ExamId;
    }

    public void setExamId(String examId) {
        ExamId = examId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getExamName() {
        return ExamName;
    }

    public void setExamName(String examName) {
        ExamName = examName;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getCorrectAnsList() {
        return CorrectAnsList;
    }

    public void setCorrectAnsList(String correctAnsList) {
        CorrectAnsList = correctAnsList;
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

    public String getUnattemped() {
        return Unattemped;
    }

    public void setUnattemped(String unattemped) {
        Unattemped = unattemped;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getExtra1() {
        return Extra1;
    }

    public void setExtra1(String extra1) {
        Extra1 = extra1;
    }

    private  String ExamResultId;
    private  String ExamId;
    private  String UserId;
    private  String ExamName;
    private  String AddDate;

    public GetAllResultsModel(String examResultId, String examId, String userId, String examName, String addDate, String score, String rank, String correctAnsList, String correct, String wrong, String unattemped, String status, String extra1) {
        ExamResultId = examResultId;
        ExamId = examId;
        UserId = userId;
        ExamName = examName;
        AddDate = addDate;
        Score = score;
        Rank = rank;
        CorrectAnsList = correctAnsList;
        Correct = correct;
        Wrong = wrong;
        Unattemped = unattemped;
        Status = status;
        Extra1 = extra1;
    }

    private  String Score;
    private  String Rank;
    private  String CorrectAnsList;
    private  String Correct;
    private  String Wrong;
    private  String Unattemped;
    private  String Status;
    private  String Extra1;

}
