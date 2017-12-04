package com.example.moyutest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moyutest.util.BaseActivity;

public class ContentActivity extends BaseActivity {

    public static final String CONTENTS_NAME = "news_name";
    public static final String CONTENTS_IMAGE_ID = "news_image_id";
    public static final String CONTENTS_CONTENT = "news_content";
    public static final String CREATE_TIME = "create_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content);
        Intent intent = getIntent();
        String contentname = intent.getStringExtra(CONTENTS_NAME);
        int imageid = intent.getIntExtra(CONTENTS_IMAGE_ID, 0);
        String content = intent.getStringExtra(CONTENTS_CONTENT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.coolapsing_toolbar);
        ImageView fruitImageView = (ImageView) findViewById(R.id.image_view);
        TextView fruitContentText = (TextView) findViewById(R.id.content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(contentname);
        Glide.with(this).load(imageid).into(fruitImageView);
        fruitContentText.setText(content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
