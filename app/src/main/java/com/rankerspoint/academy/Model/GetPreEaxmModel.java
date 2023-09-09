package com.rankerspoint.academy.Model;

public class GetPreEaxmModel {

    public GetPreEaxmModel(String categoryId, String name, String details, String pic, String logo) {
        CategoryId = categoryId;
        Name = name;
        Details = details;
        Pic = pic;
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
    private String Logo;
}
