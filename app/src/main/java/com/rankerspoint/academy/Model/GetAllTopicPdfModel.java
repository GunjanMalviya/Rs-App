package com.rankerspoint.academy.Model;

public class GetAllTopicPdfModel {


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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNotesCategoryName() {
        return NotesCategoryName;
    }

    public void setNotesCategoryName(String notesCategoryName) {
        NotesCategoryName = notesCategoryName;
    }

    public String getPdfLang1() {
        return PdfLang1;
    }

    public void setPdfLang1(String pdfLang1) {
        PdfLang1 = pdfLang1;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public GetAllTopicPdfModel(String DCRecordId, String DCTopicId, String DCTopicName, String DCContentId, String title, String notesCategoryName, String pdfLang1,String pdfLag2, String addDate, String status,String pic1,String extra2) {
        this.DCRecordId = DCRecordId;
        this.DCTopicId = DCTopicId;
        this.DCTopicName = DCTopicName;
        this.DCContentId = DCContentId;
        Title = title;
        NotesCategoryName = notesCategoryName;
        PdfLang1 = pdfLang1;
        AddDate = addDate;
        Status = status;
        PdfLag2 = pdfLag2;
        Pic1=pic1;
        Extra2=extra2;
    }

    private  String DCRecordId;
    private  String DCTopicId;
    private  String DCTopicName;
    private  String DCContentId;

    private  String Title;
    private  String NotesCategoryName;
    private  String PdfLang1;

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }

    private  String Pic1;

    public String getPdfLag2() {
        return PdfLag2;
    }

    public void setPdfLag2(String pdfLag2) {
        PdfLag2 = pdfLag2;
    }

    private  String PdfLag2;
    private  String AddDate;
    private  String Status;

    public String getExtra2() {
        return Extra2;
    }

    public void setExtra2(String extra2) {
        Extra2 = extra2;
    }

    private  String Extra2;



}
