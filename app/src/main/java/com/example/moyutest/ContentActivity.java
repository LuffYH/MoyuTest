package com.example.moyutest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moyutest.adapter.CommentAdapter;
import com.example.moyutest.adapter.ContentAdapter;
import com.example.moyutest.gson.CommentJson;
import com.example.moyutest.gson.ContentJson;
import com.example.moyutest.model.Comments;
import com.example.moyutest.model.Contents;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.BaseActivity;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    private GridLayoutManager layoutManager;
    private int lastVisibleItem;
    private TextView cancle, contentName, contentcontent, createtime, commentNum, commentbarLike, commentnone;
    private ImageView contentImage;
    private LinearLayout editcomment, commentbar;
    private RecyclerView commentrecyclerView;
    private String authorname, content, contentcreatetime, authoravatar;
    private int imageamount, weiboId, authorId, weibolike, commentamount;
    private CommentAdapter cmadapter;
    private List<Comments> commentsList = new ArrayList<>();
    private String strFrom = "0", strSize = "6";
    private int intFrom = 0;
    private int intSize = 6;
    private int times = 0;
    private int flagnomore = 0;
    private SwipeRefreshLayout cmswipeRefresh;

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
        editcomment = (LinearLayout) findViewById(R.id.edit_comment);
        commentbar = (LinearLayout) findViewById(R.id.ll_commentbar);
        commentrecyclerView = (RecyclerView) findViewById(R.id.comment_list);
        cmswipeRefresh = (SwipeRefreshLayout) findViewById(R.id.comment_refresh);
        commentnone = (TextView) findViewById(R.id.comment_none);
        layoutManager = new GridLayoutManager(this, 1);
        commentrecyclerView.setLayoutManager(layoutManager);
        cmadapter = new CommentAdapter(commentsList);
        commentrecyclerView.setAdapter(cmadapter);
        setText();
        cmswipeRefresh.setEnabled(false);
        cancle.setOnClickListener(this);
        editcomment.setOnClickListener(this);
        commentbar.setOnClickListener(this);
        commentrecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1
                        == cmadapter.getItemCount()) {
                    cmadapter.changeMoreStatus(ContentAdapter.LOADING_MORE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            strFrom = String.valueOf(intFrom);
                            Log.d("Phone", "strFrom =" + strFrom);
                            initRefresh(strFrom, strSize);
                            Log.d("Phone", "flagnomore =" + flagnomore);
                        }
                    }, 1000);
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1
                        != cmadapter.getItemCount()) {
                    cmadapter.changeMoreStatus(ContentAdapter.PULLUP_LOAD_MORE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        if ((commentamount = intent.getIntExtra(CONTENTS_COMMENTAMOUNT, 0)) != 0) {
            commentamount = commentamount - 1;
        }
        weiboId = intent.getIntExtra(CONTENTS_WEIBOID, 0);
        authorId = intent.getIntExtra(CONTENTS_AUTHORID, 0);
        weibolike = intent.getIntExtra(CONTENTS_WEIBOLIKE, 0);
        authorname = intent.getStringExtra(CONTENTS_AUTHORNAME);
        imageamount = intent.getIntExtra(CONTENTS_IMAGEAMOUNT, 0);
        content = intent.getStringExtra(CONTENTS_CONTENT);
        contentcreatetime = intent.getStringExtra(CREATE_TIME);
        authoravatar = intent.getStringExtra(AUTHOR_AVATAR);
    }

    private void setText() {
        contentcontent.setText(content);
        contentName.setText(authorname);
        createtime.setText(contentcreatetime);
        commentNum.setText("评论 " + commentamount);
        commentbarLike.setText("赞 " + weibolike);
        Log.d("Phone", "detail avatar" + authoravatar);
        Glide.with(ContentActivity.this)
                .load("http://10.4.105.32:8080/moyu/images/avatar/" + authoravatar)
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
                startActivity(commentintent);
                break;
            case R.id.ll_commentbar:
                strFrom = "0";
                times = 0;
                initRefresh(strFrom, strSize);
                break;
            default:
                break;
        }
    }

    private void initRefresh(final String froms, String sizes) {
        String id_token = SharedPreferencesUtil.getIdTokenFromXml(ContentActivity.this);
        Api api = RetrofitProvider.create().create(Api.class);
        api.comment(id_token, String.valueOf(weiboId), froms, sizes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentJson>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommentJson commentJson) {
                        if (froms.equals("0") || froms == "0") {
                            commentrecyclerView.removeAllViews();
                            commentsList.clear();
                        }
                        List<CommentJson.ObjBean> CommentBean = commentJson.getObj();
                        if (CommentBean == null || CommentBean.size() == 0) {
                            flagnomore = 0;
                            Log.d("Phone", "checkflag =" + flagnomore);
                            cmadapter.changeMoreStatus(ContentAdapter.NO_MORE);
                        } else {
                            flagnomore = 1;
                            Log.d("Phone", "size = " + CommentBean.size());
                            for (int z = 0; z < CommentBean.size(); z++) {
                                String cmauthorName = CommentBean.get(z).getAuthorName();
                                String cmcontent = CommentBean.get(z).getCommentContent();
                                int cmLike = CommentBean.get(z).getCommentLike();
                                String cmcreateTime = CommentBean.get(z).getCreateTime();
                                String cmauthorAvatar = CommentBean.get(z).getAuthorAvatar();
                                int cmweiboId = CommentBean.get(z).getWeiboId();
                                int cmauthorId = CommentBean.get(z).getAuthorId();
                                commentsList.add(new Comments(cmauthorId, cmauthorName, cmauthorAvatar
                                        , cmcontent, cmcreateTime, cmweiboId, cmLike));
                                Log.d("Phone", cmcontent);
                            }
                            Log.d("Phone", "flagnomore =" + flagnomore);
                            times++;
                        }
                        cmadapter.notifyDataSetChanged();
                        intFrom = times * intSize;
                        cmswipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ContentActivity.this, "获取失败", Toast.LENGTH_LONG).show();
                        cmswipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        if (flagnomore == 1) {
                            commentnone.setVisibility(View.GONE);
                            commentrecyclerView.setVisibility(View.VISIBLE);
                            Toast.makeText(ContentActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                        }
                        if (times == 0 && flagnomore == 0) {
                            commentrecyclerView.setVisibility(View.GONE);
                            commentnone.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
