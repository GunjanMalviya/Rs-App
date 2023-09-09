package com.rankerspoint.academy.Model;

public class DashBoardFreeVideoModel {

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

    public String getDCContentType() {
        return DCContentType;
    }

    public void setDCContentType(String DCContentType) {
        this.DCContentType = DCContentType;
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

    private String DCRecordId;
    private String DCCourseId;
    private String DCCourseName;
    private String DCContentType;
    private String Name;
    private String Details;
    private String Type;
    private String Pic;
    private String VideoLink1;
    private String FeeStatus;

    public DashBoardFreeVideoModel(String DCRecordId, String DCCourseId, String DCCourseName, String DCContentType, String name, String details, String type, String pic, String videoLink1, String feeStatus) {
        this.DCRecordId = DCRecordId;
        this.DCCourseId = DCCourseId;
        this.DCCourseName = DCCourseName;
        this.DCContentType = DCContentType;
        Name = name;
        Details = details;
        Type = type;
        Pic = pic;
        VideoLink1 = videoLink1;
        FeeStatus = feeStatus;
    }
}
