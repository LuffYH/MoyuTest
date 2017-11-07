package com.example.moyutest.db;

/**
 * Created by Administrator on 2017/8/18.
 */

public class News {
    private String name;
    private String content;
    private int imageId;

    public News(String name, String content, int imageId) {
        this.name = name;
        this.imageId = imageId;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getContent() {
        return content;
    }
}
