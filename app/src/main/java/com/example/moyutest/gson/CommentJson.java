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
        /**
         * commentId : 2
         * authorId : 2
         * authorName : jiean
         * authorAvatar : a
         * commentContent : 真幽默
         * replyAmount : 0
         * commentLike : 0
         * createTime : 2017-12-05 14:53:29
         * weiboId : 14
         */

        private int commentId;
        private int authorId;
        private String authorName;
        private String authorAvatar;
        private String commentContent;
        private int replyAmount;
        private int commentLike;
        private String createTime;
        private int weiboId;

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
            this.authorName = authorName;
        }

        public String getAuthorAvatar() {
            return authorAvatar;
        }

        public void setAuthorAvatar(String authorAvatar) {
            this.authorAvatar = authorAvatar;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public int getReplyAmount() {
            return replyAmount;
        }

        public void setReplyAmount(int replyAmount) {
            this.replyAmount = replyAmount;
        }

        public int getCommentLike() {
            return commentLike;
        }

        public void setCommentLike(int commentLike) {
            this.commentLike = commentLike;
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
}
