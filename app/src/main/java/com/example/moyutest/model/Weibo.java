package com.example.moyutest.model;

import org.litepal.crud.DataSupport;

import java.util.List;


public class Weibo extends DataSupport {
    private String success;
    private String msg;
    private String token;
    private List<ObjBean> obj;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
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

        private int weiboId;
        private int authorId;
        private String authorName;
        private String authorAvatar;
        private String weiboContent;
        private int imageNumber;
        private int weiboLike;
        private String createTime;

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
            this.authorName = authorName;
        }

        public String getAuthorAvatar() {
            return authorAvatar;
        }

        public void setAuthorAvatar(String authorAvatar) {
            this.authorAvatar = authorAvatar;
        }

        public String getWeiboContent() {
            return weiboContent;
        }

        public void setWeiboContent(String weiboContent) {
            this.weiboContent = weiboContent;
        }

        public int getImageNumber() {
            return imageNumber;
        }

        public void setImageNumber(int imageNumber) {
            this.imageNumber = imageNumber;
        }

        public int getWeiboLike() {
            return weiboLike;
        }

        public void setWeiboLike(int weiboLike) {
            this.weiboLike = weiboLike;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}