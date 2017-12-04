package com.example.moyutest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moyutest.model.MoyuUser;
import com.example.moyutest.util.BaseActivity;

import org.litepal.crud.DataSupport;

public class MyActivity extends BaseActivity implements View.OnClickListener {
    private TextView follow, fans, nickname;
    private ImageView myphoto;
    private LinearLayout exitll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        follow = (TextView) findViewById(R.id.my_follow);
        fans = (TextView) findViewById(R.id.my_fans);
        nickname = (TextView) findViewById(R.id.my_nickname);
        exitll = (LinearLayout) findViewById(R.id.exit);
        MoyuUser user = DataSupport.findFirst(MoyuUser.class);
        follow.setText(String.valueOf(user.getFollow()));
        fans.setText(String.valueOf(user.getFollower()));
        nickname.setText(user.getNickname());
        myphoto = (ImageView) findViewById(R.id.my_photo);
        exitll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit:
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear().commit();
                DataSupport.delete(MoyuUser.class, 1);
                Intent intent = new Intent(MyActivity.this, LoginActivity.class);
                startActivity(intent);
                MainActivity.mMainActivity.finish();
                break;
            default:
                break;
        }
    }
}
