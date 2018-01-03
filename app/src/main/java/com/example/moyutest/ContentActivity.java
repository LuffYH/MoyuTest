package com.example.moyutest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moyutest.adapter.CommentViewHolder;
import com.example.moyutest.gson.CommentJson;
import com.example.moyutest.model.Comments;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.BaseActivity;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.google.gson.JsonObject;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContentActivity extends BaseActivity implements View.OnClickListener, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String CONTENTS_AUTHORNAME = "contents_name";
    public static final String CONTENTS_IMAGEAMOUNT = "contents_imageamount";
    public static final String CONTENTS_CONTENT = "contents_content";
    public static final String CREATE_TIME = "create_time";
    public static final String CONTENTS_COMMENTAMOUNT = "contents_commentAmount";
    public static final String CONTENTS_WEIBOLIKE = "contents_weiboLike";
    public static final String CONTENTS_WEIBOID = "contents_weiboId";
    public static final String CONTENTS_AUTHORID = "contents_authorId";
    public static final String AUTHOR_AVATAR = "author_Avatar";
    public static final String MY_LIKE = "my_like";
    private RecyclerArrayAdapter<Comments> commentadapter;
    private LinearLayoutManager layoutManager;
    private TextView cancle, contentfeedlike, contentName, contentcontent, createtime, commentNum, commentbarLike;
    private ImageView contentImage, imgfeedlike;
    private LinearLayout editcomment, commentbar, attitude;
    private EasyRecyclerView commentrecyclerView;
    private String authorname, content, contentcreatetime, authoravatar;
    private boolean mylike;
    private int imageamount, weiboId, authorId, weibolike, commentamount;
    private List<Comments> commentsList = new ArrayList<>();
    private int intSize = 6;
    private int intPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weibo_detail);
        initData();
        findById();
        layoutManager = new LinearLayoutManager(this);
        commentrecyclerView.setLayoutManager(layoutManager);
        setText();
        cancle.setOnClickListener(this);
        attitude.setOnClickListener(this);
        editcomment.setOnClickListener(this);
        commentbar.setOnClickListener(this);
        contentImage.setOnClickListener(this);
        commentrecyclerView.setAdapterWithProgress(commentadapter = new RecyclerArrayAdapter<Comments>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new CommentViewHolder(parent);
            }
        });
        commentadapter.setMore(R.layout.view_more, this);
        commentadapter.setNoMore(R.layout.view_nomore);
        commentrecyclerView.setRefreshListener(this);
        onRefresh();
    }

    private void findById() {
        contentfeedlike = (TextView) findViewById(R.id.feedlike);
        attitude = (LinearLayout) findViewById(R.id.bottombar_attitude);
        imgfeedlike = (ImageView) findViewById(R.id.img_feedlike);
        commentbarLike = (TextView) findViewById(R.id.commentBar_like);
        commentNum = (TextView) findViewById(R.id.commentBar_comment);
        contentImage = (ImageView) findViewById(R.id.profile_img);
        contentName = (TextView) findViewById(R.id.profile_name);
        contentcontent = (TextView) findViewById(R.id.mention_content);
        createtime = (TextView) findViewById(R.id.profile_time);
        cancle = (TextView) findViewById(R.id.detail_cancel);
        editcomment = (LinearLayout) findViewById(R.id.edit_comment);
        commentbar = (LinearLayout) findViewById(R.id.ll_commentbar);
        commentrecyclerView = (EasyRecyclerView) findViewById(R.id.comment_list);
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
        authoravatar = intent.getStringExtra(AUTHOR_AVATAR);
        mylike = intent.getBooleanExtra(MY_LIKE, true);
    }

    private void setText() {
        Log.d("Phone", "ismylike" + mylike);
        if (mylike) {
            contentfeedlike.setText("已赞");
            imgfeedlike.setImageResource(R.drawable.timeline_icon_like);
        }
        contentcontent.setText(content);
        contentName.setText(authorname);
        createtime.setText(contentcreatetime);
        commentNum.setText("评论 " + commentamount);
        commentbarLike.setText("赞 " + weibolike);
        Log.d("Phone", "detail avatar" + authoravatar);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.account);
        Glide.with(ContentActivity.this)
                .load("http://120.79.42.49:8080/images/avatar/" + authoravatar)
                .apply(requestOptions)
                .into(contentImage);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_cancel:
                finish();
                break;
            case R.id.edit_comment:
                Intent commentintent = new Intent(ContentActivity.this, CommentActivity.class);
                commentintent.putExtra("weiboid", weiboId);
                startActivityForResult(commentintent, 2);
                break;
            case R.id.ll_commentbar:
                intPage = 1;
                initRefresh(intPage, intSize);
                break;
            case R.id.bottombar_attitude:
                String strlike = contentfeedlike.getText().toString();
                Api api = RetrofitProvider.create().create(Api.class);
                String id_token = SharedPreferencesUtil.getIdTokenFromXml(this);
                if (strlike.indexOf("已") != -1) {
                    api.unlike(String.valueOf(weiboId), id_token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<JsonObject>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(JsonObject jsonObject) {
                                    weibolike = weibolike - 1;
                                    contentfeedlike.setText("点赞");
                                    commentbarLike.setText("赞 " + weibolike);
                                    imgfeedlike.setImageResource(R.drawable.timeline_icon_unlike);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                }
                            });
                } else {
                    api.like((String.valueOf(weiboId)), id_token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<JsonObject>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(JsonObject jsonObject) {
                                    weibolike = weibolike + 1;
                                    contentfeedlike.setText("已赞");
                                    commentbarLike.setText("赞 " + weibolike);
                                    imgfeedlike.setImageResource(R.drawable.timeline_icon_like);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    Toast.makeText(ContentActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();

                                }
                            });
                }
                break;
            case R.id.profile_img:
                Log.d("Phone", "" + authorId);
                Intent userintent = new Intent(ContentActivity.this, ProfileActivity.class);
                userintent.putExtra(ProfileActivity.USER_ID, authorId);
                startActivity(userintent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                onRefresh();
                break;
        }
    }

    private void initRefresh(final int page, int sizes) {
        String id_token = SharedPreferencesUtil.getIdTokenFromXml(ContentActivity.this);
        Api api = RetrofitProvider.create().create(Api.class);
        api.comment(id_token, String.valueOf(weiboId), page, sizes, "desc", "create_time")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentJson>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommentJson commentJson) {
                        commentsList.clear();
                        List<CommentJson.ObjBean> CommentBean = commentJson.getObj();
                        Log.d("Phone", "commentJson = " + commentJson);
                        for (int z = 0; z < CommentBean.size(); z++) {
                            String cmauthorName = CommentBean.get(z).getAuthorName();
                            String cmcontent = CommentBean.get(z).getContent();
                            String cmcreateTime = CommentBean.get(z).getCreateTime();
                            String cmauthorAvatar = CommentBean.get(z).getAuthorAvatar();
                            int cmweiboId = CommentBean.get(z).getMicroBlogId();
                            int cmauthorId = CommentBean.get(z).getAuthorId();
                            commentNum.setText("评论 " + CommentBean.size());
                            commentsList.add(new Comments(cmauthorId, cmauthorName, cmauthorAvatar
                                    , cmcontent, cmcreateTime, cmweiboId));
                        }
                        commentadapter.addAll(commentsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void onRefresh() {
        commentadapter.clear();
        intPage = 1;
        initRefresh(intPage, intSize);
    }

    @Override
    public void onLoadMore() {
        intPage = intPage + 1;
        initRefresh(intPage, intSize);
    }
}
