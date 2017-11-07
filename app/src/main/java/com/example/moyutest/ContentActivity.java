package com.example.moyutest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ContentActivity extends AppCompatActivity {
    public static final String NEWS_NAME = "news_name";
    public static final String NEWS_IMAGE_ID = "news_image_id";
    public static final String NEWS_CONTENT = "news_content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
    }
}
