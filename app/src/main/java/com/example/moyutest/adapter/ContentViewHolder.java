package com.example.moyutest.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moyutest.ContentActivity;
import com.example.moyutest.ProfileActivity;
import com.example.moyutest.R;
import com.example.moyutest.gson.ContentJson;
import com.example.moyutest.gson.PersonJson;
import com.example.moyutest.model.Contents;
import com.example.moyutest.model.Person;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.HandleResponse;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.google.gson.JsonObject;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Mr.Jude on 2015/2/22.
 */
public class ContentViewHolder extends BaseViewHolder<Contents> {
    private Contents mContents;
    public LinearLayout attitude;
    public ImageView contentImage, imgfeedlike;
    public TextView contentName;
    public TextView contentcontent;
    private int intlike;
    public TextView createtime, contentredirect, contentcomment, contentfeedlike;
    private Api api = RetrofitProvider.create().create(Api.class);
    private String id_token;
    private String name, avatar, location, introduction;
    private int follow, follower, gender, userid;
    private boolean myfollow;

    public ContentViewHolder(final ViewGroup parent) {
        super(parent, R.layout.item_weibo);
        contentImage = $(R.id.profile_img);
        contentName = $(R.id.profile_name);
        contentcontent = $(R.id.mention_content);
        createtime = $(R.id.profile_time);
        contentredirect = $(R.id.redirect);
        contentcomment = $(R.id.comment);
        contentfeedlike = $(R.id.feedlike);
        attitude = $(R.id.bottombar_attitude);
        imgfeedlike = $(R.id.img_feedlike);
        contentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mContents.getAuthorId();
                Log.d("Phone", "" + id);
                Intent userintent = new Intent(parent.getContext(), ProfileActivity.class);
                userintent.putExtra(ProfileActivity.USER_ID, id);
                parent.getContext().startActivity(userintent);
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), ContentActivity.class);
                intent.putExtra(ContentActivity.CONTENTS_AUTHORNAME, mContents.getAuthorName());
                intent.putExtra(ContentActivity.CONTENTS_IMAGEAMOUNT, mContents.getImageAmount());
                intent.putExtra(ContentActivity.CONTENTS_CONTENT, mContents.getContent());
                intent.putExtra(ContentActivity.CREATE_TIME, mContents.getCreateTime());
                intent.putExtra(ContentActivity.CONTENTS_COMMENTAMOUNT, mContents.getCommentAmount());
                intent.putExtra(ContentActivity.CONTENTS_WEIBOLIKE, mContents.getWeiboLike());
                intent.putExtra(ContentActivity.CONTENTS_WEIBOID, mContents.getMicroBlogId());
                intent.putExtra(ContentActivity.CONTENTS_AUTHORID, mContents.getAuthorId());
                intent.putExtra(ContentActivity.AUTHOR_AVATAR, mContents.getAuthorAvatar());
                intent.putExtra(ContentActivity.MY_LIKE, mContents.isMyLike());
                parent.getContext().startActivity(intent);
            }
        });
        attitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strlike = contentfeedlike.getText().toString();
                intlike = mContents.getWeiboLike();
                if (strlike.indexOf("已") != -1) {
                    api.unlike(String.valueOf(mContents.getMicroBlogId()), id_token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<JsonObject>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(JsonObject jsonObject) {
                                    intlike = intlike - 1;
                                    mContents.setWeiboLike(intlike);
                                    mContents.setMyLike(false);
                                    contentfeedlike.setText("点赞 " + intlike);
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
                    api.like(String.valueOf(mContents.getMicroBlogId()), id_token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<JsonObject>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(JsonObject jsonObject) {
                                    intlike = intlike + 1;
                                    mContents.setWeiboLike(intlike);
                                    mContents.setMyLike(true);
                                    contentfeedlike.setText("已赞 " + intlike);
                                    imgfeedlike.setImageResource(R.drawable.timeline_icon_like);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    Toast.makeText(parent.getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    @Override
    public void setData(final Contents contents) {
        Log.i("ViewHolder", "position" + getDataPosition());
        mContents = contents;
        contentName.setText(contents.getAuthorName());
        contentcontent.setText(contents.getContent());
        createtime.setText(contents.getCreateTime());
        if (!contents.isMyLike()) {
            contentfeedlike.setText("点赞 " + contents.getWeiboLike());
            imgfeedlike.setImageResource(R.drawable.timeline_icon_unlike);
        } else {
            contentfeedlike.setText("已赞 " + contents.getWeiboLike());
            imgfeedlike.setImageResource(R.drawable.timeline_icon_like);
        }
        contentcomment.setText("评论 " + contents.getCommentAmount());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.account);
        Glide.with(getContext())
                .load("http://120.79.42.49:8080/images/avatar/" + contents.getAuthorAvatar())
                .apply(requestOptions)
                .into(contentImage);
    }
}
