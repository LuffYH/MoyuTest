package com.example.moyutest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moyutest.adapter.ContentAdapter;
import com.example.moyutest.util.BaseActivity;

public class ContentActivity extends BaseActivity implements View.OnClickListener {

    public static final String CONTENTS_NAME = "contents_name";
    public static final String CONTENTS_IMAGE_ID = "contents_image_id";
    public static final String CONTENTS_CONTENT = "contents_content";
    public static final String CREATE_TIME = "create_time";
    public static final String CONTENTS_COMMENTAMOUNT = "news_commentAmount";
    public static final String CONTENTS_WEIBOLIKE = "news_weiboLike";
    private TextView cancle, newsName, newscontent, createtime;
    private ImageView newsImage;
    private LinearLayout comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weiboitem_detail);
        Intent intent = getIntent();
        String contentname = intent.getStringExtra(CONTENTS_NAME);
        int imageid = intent.getIntExtra(CONTENTS_IMAGE_ID, 0);
        String content = intent.getStringExtra(CONTENTS_CONTENT);
        String contentcreatetime = intent.getStringExtra(CREATE_TIME);
        newsImage = (ImageView) findViewById(R.id.profile_img);
        newsName = (TextView) findViewById(R.id.profile_name);
        newscontent = (TextView) findViewById(R.id.mention_content);
        createtime = (TextView) findViewById(R.id.profile_time);
        cancle = (TextView) findViewById(R.id.detail_cancel);
        comment = (LinearLayout) findViewById(R.id.bottombar_comment);
        newscontent.setText(content);
        newsName.setText(contentname);
        createtime.setText(contentcreatetime);
        Glide.with(this).load(imageid).into(newsImage);
        cancle.setOnClickListener(this);
        comment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_cancel:
                finish();
                break;
            case R.id.bottombar_comment:
                Intent commentintent = new Intent(ContentActivity.this, CommentActivity.class);
                startActivity(commentintent);
                break;
            default:
                break;
        }
    }
}
