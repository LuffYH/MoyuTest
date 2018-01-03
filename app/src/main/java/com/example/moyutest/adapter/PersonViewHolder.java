package com.example.moyutest.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.moyutest.ProfileActivity;
import com.example.moyutest.ProfileActivity;
import com.example.moyutest.gson.PersonJson;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.HandleResponse;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.google.gson.JsonObject;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.bumptech.glide.Glide;
import com.example.moyutest.R;
import com.example.moyutest.model.Person;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Mr.Jude on 2015/2/22.
 */
public class PersonViewHolder extends BaseViewHolder<Person> {
    private Person mperson;
    private TextView mTv_name;
    private ImageView mImg_face;
    private ImageView mImg_gender;
    private TextView mTv_location;
    private TextView mTv_introduction;
    private Button btn_follow;
    private TextView mTv_follow;
    private TextView mTv_follower;
    private Api api = RetrofitProvider.create().create(Api.class);
    private String id_token;
    private String name, avatar, location, introduction;
    private int follow, follower, gender, userid;
    private boolean myfollow;

    public PersonViewHolder(final ViewGroup parent) {
        super(parent, R.layout.item_person);
        mTv_name = $(R.id.person_name);
        mImg_face = $(R.id.person_face);
        mImg_gender = $(R.id.person_gender);
        mTv_location = $(R.id.person_location);
        mTv_introduction = $(R.id.person_introduction);
        btn_follow = $(R.id.btn_follow);
        mTv_follow = $(R.id.person_follow);
        mTv_follower = $(R.id.person_follower);
        mImg_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mperson.getUesrId();
                Log.d("Phone", "" + id);
                Intent userintent = new Intent(parent.getContext(), ProfileActivity.class);
                userintent.putExtra(ProfileActivity.USER_ID, id);
                parent.getContext().startActivity(userintent);
            }
        });
        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mperson.isMyFollow()) {
                    api.unfollow(id_token, mperson.getUesrId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<PersonJson>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(PersonJson personJson) {
                                    mperson.setMyFollow(false);
                                    btn_follow.setText("关注");
                                    btn_follow.setBackgroundResource(R.color.bg_toast);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else {
                    api.follow(id_token, mperson.getUesrId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<PersonJson>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(PersonJson personJson) {
                                    mperson.setMyFollow(true);
                                    btn_follow.setText("已关注");
                                    btn_follow.setBackgroundResource(R.color.gray);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }
        });
    }

    @Override
    public void setData(final Person person) {
        mperson = person;
        Log.i("ViewHolder", "position" + getDataPosition());
        mTv_name.setText(person.getNickname());
        mTv_introduction.setText(person.getIntroduction());
        mTv_location.setText(person.getLocation());
        if (person.getGender() == 0) {
            mImg_gender.setImageResource(R.drawable.man);
        } else {
            mImg_gender.setImageResource(R.drawable.women);
        }
        mTv_follow.setText("关注:  " + person.getFollow());
        mTv_follower.setText("粉丝:  " + person.getFollower());
        if (!person.isMyFollow()) {
            btn_follow.setText("关注");
            btn_follow.setBackgroundResource(R.color.bg_toast);
        } else {
            btn_follow.setText("已关注");
            btn_follow.setBackgroundResource(R.color.gray);
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.account);
        Glide.with(getContext())
                .load("http://120.79.42.49:8080/images/avatar/" + person.getAvatar())
                .apply(requestOptions)
                .into(mImg_face);
    }
}
