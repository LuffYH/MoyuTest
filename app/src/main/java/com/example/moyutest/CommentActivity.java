package com.example.moyutest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moyutest.model.MoyuUser;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.BaseActivity;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.google.gson.JsonObject;

import org.litepal.crud.DataSupport;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommentActivity extends BaseActivity implements View.OnClickListener {

    private TextView cmcancel, cmsend;
    private EditText etcomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        cmcancel = (TextView) findViewById(R.id.comment_cancel);
        etcomment = (EditText) findViewById(R.id.comment_content);
        cmcancel.setOnClickListener(this);
        cmsend = (TextView) findViewById(R.id.comment_send);
        etcomment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    cmsend.setBackgroundColor(getResources().getColor(R.color.orange));
                    cmsend.setTextColor(getResources().getColor(R.color.black));
                } else if (TextUtils.isEmpty(s)) {
                    cmsend.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_cancel:
                finish();
                break;
            case R.id.comment_send:
                String id_token = SharedPreferencesUtil.getIdTokenFromXml(this);
                String id = SharedPreferencesUtil.getIdFromDB();
                Api api = RetrofitProvider.create().create(Api.class);
                api.sendcomment(id, etcomment.getText().toString(), id_token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            default:
                break;
        }
    }
}
