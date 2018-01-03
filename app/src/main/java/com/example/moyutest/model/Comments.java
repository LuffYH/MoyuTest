package com.example.moyutest.model;

import java.io.Serializable;
import java.util.Date;

public class Comments implements Serializable {
    private int commentId;

    private int authorId;

    private String authorName;

    private String authorAvatar;

    private String commentContent;

    private Integer replyAmount;

    private String createTime;

    private int weiboId;

    public Comments(int authorId, String authorName, String authorAvatar, String commentContent,
                    String createTime, int weiboId) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.commentContent = commentContent;
        this.createTime = createTime;
        this.weiboId = weiboId;

    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar == null ? null : authorAvatar.trim();
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public Integer getReplyAmount() {
        return replyAmount;
    }

    public void setReplyAmount(Integer replyAmount) {
        this.replyAmount = replyAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

}