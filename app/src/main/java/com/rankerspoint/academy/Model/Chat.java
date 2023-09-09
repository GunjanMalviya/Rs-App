package com.rankerspoint.academy.Model;

public class Chat {
    private String sender;
    private String receiver;
    private String message;
    private String timechat;
    private String username;

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    private String userPic;


    public Chat(String sender, String receiver, String message, String timechat, String username,String userPic) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timechat=timechat;
        this.username=username;
        this.userPic=userPic;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.sender = sender;

    }

    public String gettimechat() {
        return timechat;
    }

    public void setSender(String sender) {
        this.sender = sender;

    }

    public void settimechat(String timechat) {
        this.timechat = timechat;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
