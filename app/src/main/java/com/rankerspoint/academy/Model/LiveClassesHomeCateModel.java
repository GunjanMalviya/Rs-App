package com.rankerspoint.academy.Model;

public class LiveClassesHomeCateModel {

    public LiveClassesHomeCateModel(String livClassId, String subjectId, String courseId, String categoryId, String name, String message, String pic1, String liVideoLink1, String liVideoLink2, String pdf, String classLiveDate, String feeStatus, String type) {
        LivClassId = livClassId;
        SubjectId = subjectId;
        CourseId = courseId;
        CategoryId = categoryId;
        Name = name;
        Message = message;
        Pic1 = pic1;
        LiVideoLink1 = liVideoLink1;
        LiVideoLink2 = liVideoLink2;
        Pdf = pdf;
        ClassLiveDate = classLiveDate;
        FeeStatus = feeStatus;
        Type = type;
    }

    private String LivClassId;
    private String SubjectId;
    private String CourseId;
    private String CategoryId;
    private String Name;
    private String Message;
    private String Pic1;
    private String LiVideoLink1;
    private String LiVideoLink2;
    private String Pdf;
    private String ClassLiveDate;
    private String FeeStatus;
    private String Type;

    public String getLivClassId() {
        return LivClassId;
    }

    public void setLivClassId(String livClassId) {
        LivClassId = livClassId;
    }

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }

    public String getLiVideoLink1() {
        return LiVideoLink1;
    }

    public void setLiVideoLink1(String liVideoLink1) {
        LiVideoLink1 = liVideoLink1;
    }

    public String getLiVideoLink2() {
        return LiVideoLink2;
    }

    public void setLiVideoLink2(String liVideoLink2) {
        LiVideoLink2 = liVideoLink2;
    }

    public String getPdf() {
        return Pdf;
    }

    public void setPdf(String pdf) {
        Pdf = pdf;
    }

    public String getClassLiveDate() {
        return ClassLiveDate;
    }

    public void setClassLiveDate(String classLiveDate) {
        ClassLiveDate = classLiveDate;
    }

    public String getFeeStatus() {
        return FeeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        FeeStatus = feeStatus;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
