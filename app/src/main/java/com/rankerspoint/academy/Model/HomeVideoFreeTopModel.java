package com.rankerspoint.academy.Model;

public class HomeVideoFreeTopModel {

    private String ImageId;
    private String ImagePath;
    private String Heading;
    private String Details;
    private String LinkType;
    private String LinkId;

    public String getExtra5() {
        return Extra5;
    }

    public void setExtra5(String extra5) {
        Extra5 = extra5;
    }

    private String Extra5;



    public HomeVideoFreeTopModel(String imageId, String imagePath, String heading, String details, String linkType, String linkId,String extra5) {
        ImageId = imageId;
        ImagePath = imagePath;
        Heading = heading;
        Details = details;
        LinkType = linkType;
        LinkId = linkId;
        Extra5=extra5;
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
