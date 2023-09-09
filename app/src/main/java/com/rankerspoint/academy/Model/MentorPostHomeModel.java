package com.rankerspoint.academy.Model;

public class MentorPostHomeModel {

    public MentorPostHomeModel(String postId, String categoryId, String subCategoryId, String courseId, String userId, String userName, String userPic, String title, String postType, String pic1, String link1, String link2, String addDate, String upVotes, String downVotes, String comments, String feeStatus, String href1, String href2) {
        PostId = postId;
        CategoryId = categoryId;
        SubCategoryId = subCategoryId;
        CourseId = courseId;
        UserId = userId;
        UserName = userName;
        UserPic = userPic;
        Title = title;
        PostType = postType;
        Pic1 = pic1;
        Link1 = link1;
        Link2 = link2;
        AddDate = addDate;
        UpVotes = upVotes;
        DownVotes = downVotes;
        Comments = comments;
        FeeStatus = feeStatus;
        Href1 = href1;
        Href2 = href2;
    }

    private String PostId;
    private String CategoryId;
    private String SubCategoryId;
    private String CourseId;
    private String UserId;
    private String UserName;
    private String UserPic;
    private String Title;
    private String PostType;
    private String Pic1;
    private String Link1;
    private String Link2;
    private String AddDate;
    private String UpVotes;
    private String DownVotes;
    private String Comments;
    private String FeeStatus;
    private String Href1;
    private String Href2;

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
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

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPic() {
        return UserPic;
    }

    public void setUserPic(String userPic) {
        UserPic = userPic;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPostType() {
        return PostType;
    }

    public void setPostType(String postType) {
        PostType = postType;
    }

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }

    public String getLink1() {
        return Link1;
    }

    public void setLink1(String link1) {
        Link1 = link1;
    }

    public String getLink2() {
        return Link2;
    }

    public void setLink2(String link2) {
        Link2 = link2;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public String getUpVotes() {
        return UpVotes;
    }

    public void setUpVotes(String upVotes) {
        UpVotes = upVotes;
    }

    public String getDownVotes() {
        return DownVotes;
    }

    public void setDownVotes(String downVotes) {
        DownVotes = downVotes;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getFeeStatus() {
        return FeeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        FeeStatus = feeStatus;
    }

    public String getHref1() {
        return Href1;
    }

    public void setHref1(String href1) {
        Href1 = href1;
    }

    public String getHref2() {
        return Href2;
    }

    public void setHref2(String href2) {
        Href2 = href2;
    }
}
