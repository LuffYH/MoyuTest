package com.example.moyutest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moyutest.util.Api;
import com.example.moyutest.util.BaseActivity;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.example.moyutest.util.Utility;
import com.google.gson.JsonObject;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PasswordActivity extends BaseActivity {


    private String phone, pw, code, nn, url, pw2, pwmd5;
    private EditText nickname, password, password2;
    private Button password_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        final Intent intent = getIntent();
        phone = intent.getStringExtra("Phone");
        nickname = (EditText) findViewById(R.id.nickname);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        password_confirm = (Button) findViewById(R.id.password_confirm);
        password_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw = password.getText().toString();
                pw2 = password2.getText().toString();
                if (!pw.equals(pw2)) {
                    Log.d("Phone", "密码确认错误" + pw + "不等于" + pw2);
                    Toast.makeText(PasswordActivity.this, "确认密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    pwmd5 = new String(Hex.encodeHex(DigestUtils.md5(pw)));
                    code = new String(Hex.encodeHex(DigestUtils.md5(phone)));
                    Log.d("Phone", "cose=" + code);
                    nn = nickname.getText().toString();
                    join();

                }
            }
        });
    }

    private void join() {
        final Api api = RetrofitProvider.create().create(Api.class);
        api.password(phone, pwmd5, nn, code).enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                String responsetext = response.body().toString();
                String token = Utility.handlejoinResponse(responsetext, pwmd5, phone);
                Log.d("Phone", "注册返回token = " + token);
                Log.d("Phone", "pw=" + pwmd5);
                if (token != null && !token.equals("")) {
                    SharedPreferencesUtil.putTokenFromXml(PasswordActivity.this,token);
                    Intent successintent = new Intent(PasswordActivity.this, MainActivity.class);
                    startActivity(successintent);
                    LoginActivity.mLoginActivity.finish();
                    finish();
                } else {
                    Toast.makeText(PasswordActivity.this, "数据错误，注册失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
