package com.example.moyutest.db;

/**
 * Created by Administrator on 2017/8/18.
 */

public class Contents {
    private String mauthorName;
    private String mcontent;
    private int mimageId;
    private int mweiboLike;
    private String mcreateTime;

    public Contents(String authorName, String content, int imageId, int weiboLike, String createTime) {
        this.mauthorName = authorName;
        this.mimageId = imageId;
        this.mcontent = content;
        this.mweiboLike = weiboLike;
        this.mcreateTime = createTime;
    }

    public String getName() {
        return mauthorName;
    }

    public int getImageId() {
        return mimageId;
    }

    public String getContent() {
        return mcontent;
    }

    public int getMweiboLike() {
        return mweiboLike;
    }

    public String getMcreateTime() {
        return mcreateTime;
    }

}
