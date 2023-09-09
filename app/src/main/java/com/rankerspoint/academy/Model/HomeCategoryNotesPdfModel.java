package com.rankerspoint.academy.Model;

public class HomeCategoryNotesPdfModel {


    public String getNotesId() {
        return NotesId;
    }

    public void setNotesId(String notesId) {
        NotesId = notesId;
    }

    public String getNotesCategoryId() {
        return NotesCategoryId;
    }

    public void setNotesCategoryId(String notesCategoryId) {
        NotesCategoryId = notesCategoryId;
    }

    public String getCourseCategoryId() {
        return CourseCategoryId;
    }

    public void setCourseCategoryId(String courseCategoryId) {
        CourseCategoryId = courseCategoryId;
    }

    public String getNotesCategoryName() {
        return NotesCategoryName;
    }

    public void setNotesCategoryName(String notesCategoryName) {
        NotesCategoryName = notesCategoryName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPdfLang1() {
        return PdfLang1;
    }

    public void setPdfLang1(String pdfLang1) {
        PdfLang1 = pdfLang1;
    }

    public String getPdfLag2() {
        return PdfLag2;
    }

    public void setPdfLag2(String pdfLag2) {
        PdfLag2 = pdfLag2;
    }

    public String getUpVotes() {
        return UpVotes;
    }

    public void setUpVotes(String upVotes) {
        UpVotes = upVotes;
    }

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }

    public HomeCategoryNotesPdfModel(String notesId, String notesCategoryId, String courseCategoryId, String notesCategoryName, String title, String pdfLang1, String pdfLag2, String upVotes, String pic1,String extra1,String extra2) {
        NotesId = notesId;
        NotesCategoryId = notesCategoryId;
        CourseCategoryId = courseCategoryId;
        NotesCategoryName = notesCategoryName;
        Title = title;
        PdfLang1 = pdfLang1;
        PdfLag2 = pdfLag2;
        UpVotes = upVotes;
        Pic1 = pic1;
        Extra1=extra1;
        Extra2=extra2;
    }

    private String NotesId;
    private String NotesCategoryId;
    private String CourseCategoryId;
    private String NotesCategoryName;
    private String Title;
    private String PdfLang1;
    private String PdfLag2;
    private String UpVotes;
    private String Pic1;

    public String getExtra1() {
        return Extra1;
    }

    public void setExtra1(String extra1) {
        Extra1 = extra1;
    }

    private String Extra1;

    public String getExtra2() {
        return Extra2;
    }

    public void setExtra2(String extra2) {
        Extra2 = extra2;
    }

    private String Extra2;

}
