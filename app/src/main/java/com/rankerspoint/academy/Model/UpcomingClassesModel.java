package com.rankerspoint.academy.Model;

public class UpcomingClassesModel {

    private String CourseId;
    private String CategoryId;
    private String SubCategoryId;
    private String Name;
    private String Details;
    private String Pic;
    private String AddDate;
    private String FreeTrail;
    private String Langauge;
    private String Teachers;
    private String Price;

    public UpcomingClassesModel(String courseId, String categoryId, String subCategoryId, String name, String details, String pic, String addDate, String freeTrail, String langauge, String teachers, String price) {
        CourseId = courseId;
        CategoryId = categoryId;
        SubCategoryId = subCategoryId;
        Name = name;
        Details = details;
        Pic = pic;
        AddDate = addDate;
        FreeTrail = freeTrail;
        Langauge = langauge;
        Teachers = teachers;
        Price = price;
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

    public String getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        SubCategoryId = subCategoryId;
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

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public String getFreeTrail() {
        return FreeTrail;
    }

    public void setFreeTrail(String freeTrail) {
        FreeTrail = freeTrail;
    }

    public String getLangauge() {
        return Langauge;
    }

    public void setLangauge(String langauge) {
        Langauge = langauge;
    }

    public String getTeachers() {
        return Teachers;
    }

    public void setTeachers(String teachers) {
        Teachers = teachers;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
