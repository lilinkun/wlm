package com.wlm.wlm.entity;

/**
 * Created by LG on 2019/9/25.
 */
public class OpinionBean {
    private int MessageID;
    private int Type;
    private String TypeName;
    private int UserId;
    private String UserName;
    private String Title;
    private String Question;
    private String CreateDate;
    private String CreateIP;
    private String ReplyContent;
    private String ReplyDate;
    private String ReplyID;
    private String ReplyName;
    private int IsDelete;
    private String NickName;
    private String Mobile;
    private String SurName;
    private int Status;

    public int getMessageID() {
        return MessageID;
    }

    public void setMessageID(int messageID) {
        MessageID = messageID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCreateIP() {
        return CreateIP;
    }

    public void setCreateIP(String createIP) {
        CreateIP = createIP;
    }

    public String getReplyContent() {
        return ReplyContent;
    }

    public void setReplyContent(String replyContent) {
        ReplyContent = replyContent;
    }

    public String getReplyDate() {
        return ReplyDate;
    }

    public void setReplyDate(String replyDate) {
        ReplyDate = replyDate;
    }

    public String getReplyID() {
        return ReplyID;
    }

    public void setReplyID(String replyID) {
        ReplyID = replyID;
    }

    public String getReplyName() {
        return ReplyName;
    }

    public void setReplyName(String replyName) {
        ReplyName = replyName;
    }

    public int getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(int isDelete) {
        IsDelete = isDelete;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
