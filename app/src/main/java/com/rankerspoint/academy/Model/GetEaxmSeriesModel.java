package com.rankerspoint.academy.Model;

public class GetEaxmSeriesModel {


    public GetEaxmSeriesModel(String categoryId, String seriesId, String courseId, String title, String feeStatus, String pic,String status) {
        CategoryId = categoryId;
        SeriesId = seriesId;
        CourseId = courseId;
        Title = title;
        FeeStatus = feeStatus;
        Pic = pic;
        Status=status;
    }

    private String CategoryId;
    private String SeriesId;
    private String CourseId;
    private String Title;
    private String FeeStatus;
    private String Pic;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String Status;

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getSeriesId() {
        return SeriesId;
    }

    public void setSeriesId(String seriesId) {
        SeriesId = seriesId;
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
}
