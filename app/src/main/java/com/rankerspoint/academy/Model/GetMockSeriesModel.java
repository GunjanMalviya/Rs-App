package com.rankerspoint.academy.Model;

public class GetMockSeriesModel {


    public GetMockSeriesModel(String seriesId, String title, String feeStatus, String status, String feeAmount, String pic, String totalExam) {
        SeriesId = seriesId;
        Title = title;
        FeeStatus = feeStatus;
        Status = status;
        FeeAmount = feeAmount;
        Pic = pic;
        TotalExam = totalExam;
    }

    private String SeriesId;
    private String Title;
    private String FeeStatus;
    private String Status;
    private String FeeAmount;
    private String Pic;
    private String TotalExam;

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

    public String getFeeStatus() {
        return FeeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        FeeStatus = feeStatus;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFeeAmount() {
        return FeeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        FeeAmount = feeAmount;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getTotalExam() {
        return TotalExam;
    }

    public void setTotalExam(String totalExam) {
        TotalExam = totalExam;
    }
}
