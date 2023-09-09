package com.rankerspoint.academy.Model;

public class GetSubCourseModel {

    public GetSubCourseModel(String courseId, String categoryId, String subCategoryId, String name, String details, String pic, String logo, String regLastDate, String expiryDate, String freeTrail, String price, String langauge, String teachers) {
        CourseId = courseId;
        CategoryId = categoryId;
        SubCategoryId = subCategoryId;
        Name = name;
        Details = details;
        Pic = pic;
        Logo = logo;
        RegLastDate = regLastDate;
        ExpiryDate = expiryDate;
        FreeTrail = freeTrail;
        Price = price;
        Langauge = langauge;
        Teachers = teachers;
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

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getRegLastDate() {
        return RegLastDate;
    }

    public void setRegLastDate(String regLastDate) {
        RegLastDate = regLastDate;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getFreeTrail() {
        return FreeTrail;
    }

    public void setFreeTrail(String freeTrail) {
        FreeTrail = freeTrail;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
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

    private String CourseId;
    private String CategoryId;
    private String SubCategoryId;
    private String Name;
    private String Details;
    private String Pic;
    private String Logo;
    private String RegLastDate;
    private String ExpiryDate;
    private String FreeTrail;
    private String Price;
    private String Langauge;
    private String Teachers;

}
