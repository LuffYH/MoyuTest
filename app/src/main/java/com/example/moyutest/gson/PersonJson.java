package com.example.moyutest.gson;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class PersonJson {


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


        private int userId;
        private String avatar;
        private String nickname;
        private int gender;
        private String introduction;
        private String location;
        private int follow;
        private int follower;
        private boolean myFollow;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public int getFollower() {
            return follower;
        }

        public void setFollower(int follower) {
            this.follower = follower;
        }

        public boolean isMyFollow() {
            return myFollow;
        }

        public void setMyFollow(boolean myFollow) {
            this.myFollow = myFollow;
        }
    }
}
