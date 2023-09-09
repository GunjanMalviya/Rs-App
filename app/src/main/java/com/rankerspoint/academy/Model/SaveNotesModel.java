package com.rankerspoint.academy.Model;

public class SaveNotesModel {


    String SaveNoteId;
    String CategoryId;
    String UserId;
    String Title;
    String ContentType;
    String ContentId;
    String VideoType;
    String Pic1;
    String Extra1;
    String Extra2;
    String Extra3;

    public SaveNotesModel(String saveNoteId, String categoryId, String userId, String title, String contentType, String contentId, String videoType, String pic1, String extra1, String extra2, String extra3) {
        SaveNoteId = saveNoteId;
        CategoryId = categoryId;
        UserId = userId;
        Title = title;
        ContentType = contentType;
        ContentId = contentId;
        VideoType = videoType;
        Pic1 = pic1;
        Extra1 = extra1;
        Extra2 = extra2;
        Extra3 = extra3;
    }

    public String getSaveNoteId() {
        return SaveNoteId;
    }

    public void setSaveNoteId(String saveNoteId) {
        SaveNoteId = saveNoteId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    public String getContentId() {
        return ContentId;
    }

    public void setContentId(String contentId) {
        ContentId = contentId;
    }

    public String getVideoType() {
        return VideoType;
    }

    public void setVideoType(String videoType) {
        VideoType = videoType;
    }

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }

    public String getExtra1() {
        return Extra1;
    }

    public void setExtra1(String extra1) {
        Extra1 = extra1;
    }

    public String getExtra2() {
        return Extra2;
    }

    public void setExtra2(String extra2) {
        Extra2 = extra2;
    }

    public String getExtra3() {
        return Extra3;
    }

    public void setExtra3(String extra3) {
        Extra3 = extra3;
    }
}
