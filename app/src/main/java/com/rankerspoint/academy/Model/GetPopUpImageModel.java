package com.rankerspoint.academy.Model;

public class GetPopUpImageModel {
    public GetPopUpImageModel(String imageId, String imagePath, String imageCategory, String imageSubCategory, String heading, String details) {
        ImageId = imageId;
        ImagePath = imagePath;
        ImageCategory = imageCategory;
        ImageSubCategory = imageSubCategory;
        Heading = heading;
        Details = details;
    }

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getImageCategory() {
        return ImageCategory;
    }

    public void setImageCategory(String imageCategory) {
        ImageCategory = imageCategory;
    }

    public String getImageSubCategory() {
        return ImageSubCategory;
    }

    public void setImageSubCategory(String imageSubCategory) {
        ImageSubCategory = imageSubCategory;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    private String ImageId;
    private String ImagePath;
    private String ImageCategory;
    private String ImageSubCategory;
    private String Heading;
    private String Details;
}
