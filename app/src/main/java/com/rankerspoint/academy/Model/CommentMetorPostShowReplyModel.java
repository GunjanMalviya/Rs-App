package com.rankerspoint.academy.Model;

public class CommentMetorPostShowReplyModel {


    public CommentMetorPostShowReplyModel(String commentId, String replyCmntId, String userId, String userName, String userPic, String commentType, String postId, String text, String addDate, String pic1) {
        CommentId = commentId;
        ReplyCmntId = replyCmntId;
        UserId = userId;
        UserName = userName;
        UserPic = userPic;
        CommentType = commentType;
        PostId = postId;
        Text = text;
        AddDate = addDate;
        Pic1 = pic1;
    }

    private String CommentId;
    private String ReplyCmntId;
    private String UserId;
    private String UserName;
    private String UserPic;
    private String CommentType;
    private String PostId;
    private String Text;
    private String AddDate;
    private String Pic1;

    public String getCommentId() {
        return CommentId;
    }

    public void setCommentId(String commentId) {
        CommentId = commentId;
    }

    public String getReplyCmntId() {
        return ReplyCmntId;
    }

    public void setReplyCmntId(String replyCmntId) {
        ReplyCmntId = replyCmntId;
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

    public String getCommentType() {
        return CommentType;
    }

    public void setCommentType(String commentType) {
        CommentType = commentType;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }
}
