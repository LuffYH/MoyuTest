package com.example.moyutest;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moyutest.model.MoyuUser;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.BaseActivity;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.example.moyutest.util.Utility;
import com.google.gson.JsonObject;

import org.litepal.crud.DataSupport;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

public class EditActivity extends BaseActivity implements View.OnClickListener {
    private EditText etcontent;
    private TextView send, cancal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etcontent = (EditText) findViewById(R.id.idea_content);
        send = (TextView) findViewById(R.id.idea_send);
        cancal = (TextView) findViewById(R.id.idea_cancel);
        etcontent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    send.setBackgroundColor(getResources().getColor(R.color.orange));
                    send.setTextColor(getResources().getColor(R.color.black));
                } else if (TextUtils.isEmpty(s)) {
                    send.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        });
        send.setOnClickListener(this);
        cancal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idea_send:
                String id_token = SharedPreferencesUtil.getIdTokenFromXml(this);
                final Api api = RetrofitProvider.create().create(Api.class);
                api.sendweibo(etcontent.getText().toString(), id_token).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        String responseText = response.body().toString();
                        Log.d("Phone", "微博responseText" + responseText);
                        boolean sendflag = Utility.sendweibo(responseText);
                        if (sendflag) {
                            Toast.makeText(EditActivity.this, "发送成功",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditActivity.this, "发送失败，请稍后重试",
                                    Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
                break;
            case R.id.idea_cancel:
                finish();
                break;
            default:
                break;
        }

    }
}
