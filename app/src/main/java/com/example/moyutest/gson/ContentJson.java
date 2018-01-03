package com.example.moyutest.gson;

import java.util.List;


public class ContentJson {

    private boolean success;
    private String msg;
    private String token;
    private List<ObjBean> obj;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {


        private int microBlogId;
        private int authorId;
        private String content;
        private int imageAmount;
        private String createTime;
        private String authorName;
        private String authorAvatar;
        private int commentAmount;
        private int likeAmount;
        private boolean myLike;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getImageAmount() {
            return imageAmount;
        }

        public void setImageAmount(int imageAmount) {
            this.imageAmount = imageAmount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getAuthorAvatar() {
            return authorAvatar;
        }

        public void setAuthorAvatar(String authorAvatar) {
            this.authorAvatar = authorAvatar;
        }

        public int getCommentAmount() {
            return commentAmount;
        }

        public void setCommentAmount(int commentAmount) {
            this.commentAmount = commentAmount;
        }

        public int getLikeAmount() {
            return likeAmount;
        }

        public void setLikeAmount(int likeAmount) {
            this.likeAmount = likeAmount;
        }

        public boolean isMyLike() {
            return myLike;
        }

        public void setMyLike(boolean myLike) {
            this.myLike = myLike;
        }
    }
}