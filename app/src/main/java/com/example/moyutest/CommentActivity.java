package com.example.moyutest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moyutest.util.Api;
import com.example.moyutest.util.BaseActivity;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.example.moyutest.util.HandleResponse;
import com.google.gson.JsonObject;

import io.reactivex.Observer;
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
        etcomment = (EditText) findViewById(R.id.comment_text);
        cmsend = (TextView) findViewById(R.id.comment_send);
        cmcancel.setOnClickListener(this);
        cmsend.setOnClickListener(this);
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
                Intent idintent = getIntent();
                String id_token = SharedPreferencesUtil.getIdTokenFromXml(this);
                int wid = idintent.getIntExtra("weiboid", 0);
                Api api = RetrofitProvider.create().create(Api.class);
                api.sendcomment(String.valueOf(wid), etcomment.getText().toString(), id_token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                String responseText = jsonObject.toString();
                                boolean sendflag = HandleResponse.sendcomment(responseText);
                                if (sendflag) {
                                    Toast.makeText(CommentActivity.this, "发送成功",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    setResult(2, intent);
                                    finish();
                                } else {
                                    Toast.makeText(CommentActivity.this, "发送失败，请稍后重试",
                                            Toast.LENGTH_LONG).show();

                                }
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
