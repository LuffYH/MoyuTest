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
import com.example.moyutest.util.Api;
import com.example.moyutest.util.BaseActivity;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.google.gson.JsonObject;

import org.litepal.crud.DataSupport;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyActivity extends BaseActivity implements View.OnClickListener {
    private TextView follow, fans, nickname;
    private ImageView myphoto;
    private LinearLayout exitll;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Long id;

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
                String id_token = SharedPreferencesUtil.getIdTokenFromXml(this);
                Api api = RetrofitProvider.create().create(Api.class);
                api.logout(id_token).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                editor.clear().commit();
                                DataSupport.deleteAll(MoyuUser.class, "userid = ?", String.valueOf(id));
                                Intent intent = new Intent(MyActivity.this, LoginActivity.class);
                                startActivity(intent);
                                MainActivity.mMainActivity.finish();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                Toast.makeText(MyActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            default:
                break;
        }
    }
}
