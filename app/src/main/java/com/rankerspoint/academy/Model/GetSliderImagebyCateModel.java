package com.rankerspoint.academy.Model;

public class GetSliderImagebyCateModel {

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

    public GetSliderImagebyCateModel(String imageId, String imagePath, String imageCategory, String imageSubCategory, String heading, String details, String imageType, String imagePriority, String linkType, String linkId) {
        ImageId = imageId;
        ImagePath = imagePath;
        ImageCategory = imageCategory;
        ImageSubCategory = imageSubCategory;
        Heading = heading;
        Details = details;
        ImageType = imageType;
        ImagePriority = imagePriority;
        LinkType = linkType;
        LinkId = linkId;
    }

    private String ImageId;
    private String ImagePath;
    private String ImageCategory;
    private String ImageSubCategory;
    private String Heading;
    private String Details;

    public String getImageType() {
        return ImageType;
    }

    public void setImageType(String imageType) {
        ImageType = imageType;
    }

    public String getImagePriority() {
        return ImagePriority;
    }

    public void setImagePriority(String imagePriority) {
        ImagePriority = imagePriority;
    }

    public String getLinkType() {
        return LinkType;
    }

    public void setLinkType(String linkType) {
        LinkType = linkType;
    }

    public String getLinkId() {
        return LinkId;
    }

    public void setLinkId(String linkId) {
        LinkId = linkId;
    }

    private String ImageType;
    private String ImagePriority;
    private String LinkType;
    private String LinkId;



}
