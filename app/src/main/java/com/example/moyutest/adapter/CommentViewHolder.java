package com.example.moyutest.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moyutest.ProfileActivity;
import com.example.moyutest.R;
import com.example.moyutest.gson.PersonJson;
import com.example.moyutest.model.Comments;
import com.example.moyutest.model.Person;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.HandleResponse;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.google.gson.JsonObject;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import org.w3c.dom.Comment;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Mr.Jude on 2015/2/22.
 */
public class CommentViewHolder extends BaseViewHolder<Comments> {
    private Comments mComments;
    public TextView authorName;
    public TextView commentcontent;
    public TextView createtime;
    public ImageView commentImage;
    private String id_token;
    private String name, avatar, location, introduction;
    private int follow, follower, gender, userid;
    private boolean myfollow;

    public CommentViewHolder(final ViewGroup parent) {
        super(parent, R.layout.item_comment);
        authorName = $(R.id.comment_profile_name);
        commentcontent = $(R.id.comment_content);
        createtime = $(R.id.comment_time);
        commentImage = $(R.id.comment_img);
        commentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mComments.getAuthorId();
                Log.d("Phone", "" + id);
                Intent userintent = new Intent(parent.getContext(), ProfileActivity.class);
                userintent.putExtra(ProfileActivity.USER_ID, id);
                parent.getContext().startActivity(userintent);
            }
        });
    }

    @Override
    public void setData(final Comments comments) {
        mComments = comments;
        Log.i("Phone", "getAuthorName=" + comments.getAuthorName());
        authorName.setText(comments.getAuthorName());
        commentcontent.setText(comments.getCommentContent());
        createtime.setText(comments.getCreateTime());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.account);
        Glide.with(getContext())
                .load("http://120.79.42.49:8080/images/avatar/" + comments.getAuthorAvatar())
                .apply(requestOptions)
                .into(commentImage);

    }
}
