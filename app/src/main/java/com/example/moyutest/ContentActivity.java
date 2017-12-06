package com.example.moyutest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moyutest.util.BaseActivity;

public class ContentActivity extends BaseActivity implements View.OnClickListener {

    public static final String CONTENTS_AUTHORNAME = "contents_name";
    public static final String CONTENTS_IMAGEAMOUNT = "contents_imageamount";
    public static final String CONTENTS_CONTENT = "contents_content";
    public static final String CREATE_TIME = "create_time";
    public static final String CONTENTS_COMMENTAMOUNT = "contents_commentAmount";
    public static final String CONTENTS_WEIBOLIKE = "contents_weiboLike";
    public static final String CONTENTS_WEIBOID = "contents_weiboId";
    public static final String CONTENTS_AUTHORID = "contents_authorId";
    public static final String AUTHOR_AVATAR = "author_Avatar";

    private TextView cancle, contentName, contentcontent, createtime, commentNum, commentbarLike;
    private ImageView contentImage;
    private LinearLayout comment;
    private RecyclerView commentlist;
    private String authorname, content, contentcreatetime;
    private int imageamount, weiboId, authorId, weibolike, commentamount;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weibo_detail);
        initData();
        commentbarLike = (TextView) findViewById(R.id.commentBar_like);
        commentNum = (TextView) findViewById(R.id.commentBar_comment);
        contentImage = (ImageView) findViewById(R.id.profile_img);
        contentName = (TextView) findViewById(R.id.profile_name);
        contentcontent = (TextView) findViewById(R.id.mention_content);
        createtime = (TextView) findViewById(R.id.profile_time);
        cancle = (TextView) findViewById(R.id.detail_cancel);
        comment = (LinearLayout) findViewById(R.id.bottombar_comment);
        commentlist = (RecyclerView) findViewById(R.id.comment_list);
        setText();
        cancle.setOnClickListener(this);
        comment.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        commentamount = intent.getIntExtra(CONTENTS_COMMENTAMOUNT, 0);
        weiboId = intent.getIntExtra(CONTENTS_WEIBOID, 0);
        authorId = intent.getIntExtra(CONTENTS_AUTHORID, 0);
        weibolike = intent.getIntExtra(CONTENTS_WEIBOLIKE, 0);
        authorname = intent.getStringExtra(CONTENTS_AUTHORNAME);
        imageamount = intent.getIntExtra(CONTENTS_IMAGEAMOUNT, 0);
        content = intent.getStringExtra(CONTENTS_CONTENT);
        contentcreatetime = intent.getStringExtra(CREATE_TIME);
    }

    private void setText() {
        contentcontent.setText(content);
        contentName.setText(authorname);
        createtime.setText(contentcreatetime);
        commentNum.setText("评论 " + commentamount);
        commentbarLike.setText("赞 " + weibolike);
        Glide.with(this).load(imageamount).into(contentImage);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_cancel:
                finish();
                break;
            case R.id.bottombar_comment:
                Intent commentintent = new Intent(ContentActivity.this, CommentActivity.class);
                commentintent.putExtra("weiboid", weiboId);
                startActivity(commentintent);
                break;
            default:
                break;
        }
    }
}
