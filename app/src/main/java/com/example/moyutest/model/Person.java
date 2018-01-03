package com.example.moyutest.model;

/**
 * Created by Administrator on 2018/1/2.
 */

public class Person {
    private int uesrId;
    private String avatar;
    private String nickname;
    private int gender;
    private String introduction;
    private String location;
    private int follow;
    private int follower;
    private boolean myFollow;

    public Person(int uesrId, String avatar, String nickname, int gender, String introduction, String location, int follow, int follower, boolean myFollow) {
        this.uesrId = uesrId;
        this.avatar = avatar;
        this.nickname = nickname;
        this.gender = gender;
        this.introduction = introduction;
        this.location = location;
        this.follow = follow;
        this.follower = follower;
        this.myFollow = myFollow;
    }

    public boolean isMyFollow() {
        return myFollow;
    }

    public void setMyFollow(boolean myFollow) {
        this.myFollow = myFollow;
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

    public int getUesrId() {
        return uesrId;
    }

    public void setUesrId(int uesrId) {
        this.uesrId = uesrId;
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
}
