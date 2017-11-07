package com.example.moyutest.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class MoyuUser extends DataSupport {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String mobile;

    private String email;

    @Column(nullable = false)
    private String password;

    private String avatar;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int gender;

    private String introduce;

    private String location;

    @Column(nullable = false)
    private Long follow;

    @Column(nullable = false)
    private Long follower;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getFollow() {
        return follow;
    }

    public void setFollow(Long follow) {
        this.follow = follow;
    }

    public Long getFollower() {
        return follower;
    }

    public void setFollower(Long follower) {
        this.follower = follower;
    }


}