package com.example.moyutest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.moyutest.db.MoyuUser;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.google.gson.JsonObject;

import org.litepal.crud.DataSupport;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        exit = (RelativeLayout) findViewById(R.id.exitLayout);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exitLayout:
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
                                SharedPreferencesUtil.cleanToken(SettingActivity.this);
                                String id = SharedPreferencesUtil.getIdFromDB();
                                DataSupport.deleteAll(MoyuUser.class, "userid = ?", id);
                                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                startActivity(intent);
                                MainActivity.mMainActivity.finish();
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                Toast.makeText(SettingActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            default:
                break;
        }
    }
}
