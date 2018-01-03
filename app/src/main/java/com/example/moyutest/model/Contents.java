package com.example.moyutest.model;

/**
 * Created by Administrator on 2017/8/18.
 */

public class Contents {

    private int microBlogId;
    private int authorId;
    private String authorName;
    private String authorAvatar;
    private String content;
    private int imageAmount;
    private int commentAmount;
    private int weiboLike;
    private String createTime;
    private boolean myLike;

    public Contents(int mweiboId, int mauthorId, String mauthorName, String mauthorAvatar, String mcontent, int mimageAmount, int mcommentAmount, int mweiboLike, String mcreateTime, boolean mmylike) {
        this.microBlogId = mweiboId;
        this.authorId = mauthorId;
        this.createTime = mcreateTime;
        this.weiboLike = mweiboLike;
        this.authorAvatar = mauthorAvatar;
        this.authorName = mauthorName;
        this.commentAmount = mcommentAmount;
        this.content = mcontent;
        this.imageAmount = mimageAmount;
        this.myLike = mmylike;
    }

    public boolean isMyLike() {
        return myLike;
    }

    public void setMyLike(boolean myLike) {
        this.myLike = myLike;
    }

    public int getMicroBlogId() {
        return microBlogId;
    }

    public void setMicroBlogId(int microBlogId) {
        this.microBlogId = microBlogId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getImageAmount() {
        return imageAmount;
    }

    public void setImageAmount(Integer imageAmount) {
        this.imageAmount = imageAmount;
    }

    public Integer getCommentAmount() {
        return commentAmount;
    }

    public void setCommentAmount(Integer commentAmount) {
        this.commentAmount = commentAmount;
    }

    public Integer getWeiboLike() {
        return weiboLike;
    }

    public void setWeiboLike(Integer weiboLike) {
        this.weiboLike = weiboLike;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setImageAmount(int imageAmount) {
        this.imageAmount = imageAmount;
    }

    public void setCommentAmount(int commentAmount) {
        this.commentAmount = commentAmount;
    }

    public void setWeiboLike(int weiboLike) {
        this.weiboLike = weiboLike;
    }


}
