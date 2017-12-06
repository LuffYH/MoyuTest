package com.example.moyutest.db;

/**
 * Created by Administrator on 2017/8/18.
 */

public class Contents {

    private int weiboId;
    private int authorId;
    private String authorName;
    private String authorAvatar;
    private String weiboContent;
    private int imageAmount;
    private int commentAmount;
    private int weiboLike;
    private String createTime;


    public Contents(int mweiboId,int mauthorId,String mauthorName, String mauthorAvatar, String mcontent, int mimageAmount, int mcommentAmount, int mweiboLike, String mcreateTime) {
        this.weiboId = mweiboId;
        this.authorId = mauthorId;
        this.createTime = mcreateTime;
        this.weiboLike = mweiboLike;
        this.authorAvatar = mauthorAvatar;
        this.authorName = mauthorName;
        this.commentAmount = mcommentAmount;
        this.weiboContent = mcontent;
        this.imageAmount = mimageAmount;
    }
    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
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

    public String getWeiboContent() {
        return weiboContent;
    }

    public void setWeiboContent(String weiboContent) {
        this.weiboContent = weiboContent == null ? null : weiboContent.trim();
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

}
