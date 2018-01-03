package com.example.moyutest.gson;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class CommentJson {

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
        private int commentId;
        private int authorId;
        private String content;
        private String createTime;
        private int microBlogId;
        private String authorName;
        private String authorAvatar;
        private Object replyAmount;
        private boolean myLike;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getMicroBlogId() {
            return microBlogId;
        }

        public void setMicroBlogId(int microBlogId) {
            this.microBlogId = microBlogId;
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

        public Object getReplyAmount() {
            return replyAmount;
        }

        public void setReplyAmount(Object replyAmount) {
            this.replyAmount = replyAmount;
        }

        public boolean isMyLike() {
            return myLike;
        }

        public void setMyLike(boolean myLike) {
            this.myLike = myLike;
        }
    }
}
