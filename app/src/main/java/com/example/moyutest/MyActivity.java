package com.example.moyutest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.moyutest.model.MoyuUser;

import org.litepal.crud.DataSupport;

public class MyActivity extends AppCompatActivity {
    private TextView follow, fans, nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        follow = (TextView) findViewById(R.id.my_follow);
        fans = (TextView) findViewById(R.id.my_fans);
        nickname = (TextView) findViewById(R.id.my_nickname);
        MoyuUser user = DataSupport.findFirst(MoyuUser.class);
        follow.setText(String.valueOf(user.getFollow()));
        fans.setText(String.valueOf(user.getFollower()));
        nickname.setText(user.getNickname());
    }
}
