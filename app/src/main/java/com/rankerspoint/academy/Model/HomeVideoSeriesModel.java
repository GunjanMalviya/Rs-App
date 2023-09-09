package com.rankerspoint.academy.Model;

public class HomeVideoSeriesModel {

    private String ImageId;
    private String ImagePath;
    private String Heading;
    private String Details;
    private String LinkType;
    private String LinkId;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String Status;

    public HomeVideoSeriesModel(String imageId, String imagePath, String heading, String details, String linkType, String linkId,String status) {
        ImageId = imageId;
        ImagePath = imagePath;
        Heading = heading;
        Details = details;
        LinkType = linkType;
        LinkId = linkId;
        Status=status;
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
}
