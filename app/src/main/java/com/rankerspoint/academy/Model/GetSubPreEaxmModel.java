package com.rankerspoint.academy.Model;

public class GetSubPreEaxmModel {

    public GetSubPreEaxmModel(String categoryId,String subCategoryId, String name, String details, String pic, String logo) {
        CategoryId = categoryId;
        Name = name;
        Details = details;
        Pic = pic;
        SubCategoryId=subCategoryId;
        Logo = logo;
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

    private String CategoryId;
    private String Name;
    private String Details;
    private String Pic;

    public String getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        SubCategoryId = subCategoryId;
    }

    private String SubCategoryId;
    private String Logo;
}
