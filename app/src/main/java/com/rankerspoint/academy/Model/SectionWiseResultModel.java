package com.rankerspoint.academy.Model;

public class SectionWiseResultModel {

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

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getTotalMark() {
        return TotalMark;
    }

    public void setTotalMark(String totalMark) {
        TotalMark = totalMark;
    }

    public String getPassedStatus() {
        return PassedStatus;
    }

    public void setPassedStatus(String passedStatus) {
        PassedStatus = passedStatus;
    }

    public String getCuttOff() {
        return CuttOff;
    }

    public void setCuttOff(String cuttOff) {
        CuttOff = cuttOff;
    }

    public String getTimeTake() {
        return TimeTake;
    }

    public void setTimeTake(String timeTake) {
        TimeTake = timeTake;
    }

    public String getPercentile() {
        return Percentile;
    }

    public void setPercentile(String percentile) {
        Percentile = percentile;
    }

    public String getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(String accuracy) {
        Accuracy = accuracy;
    }

    public SectionWiseResultModel(String correct, String wrong, String skipped, String totalQuestion, String score, String totalMark, String passedStatus, String cuttOff, String timeTake, String percentile, String accuracy) {
        Correct = correct;
        Wrong = wrong;
        Skipped = skipped;
        TotalQuestion = totalQuestion;
        Score = score;
        TotalMark = totalMark;
        PassedStatus = passedStatus;
        CuttOff = cuttOff;
        TimeTake = timeTake;
        Percentile = percentile;
        Accuracy = accuracy;
    }

    private String Correct;
    private String Wrong;
    private String Skipped;
    private String TotalQuestion;
    private String Score;
    private String TotalMark;
    private String PassedStatus;
    private String CuttOff;
    private String TimeTake;
    private String Percentile;
    private String Accuracy;

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    private String SubjectName;


}
