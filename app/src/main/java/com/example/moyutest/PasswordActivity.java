package com.example.moyutest;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moyutest.util.HttpUtil;
import com.example.moyutest.util.Utility;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PasswordActivity extends AppCompatActivity {


    private String phone, pw, code, nn, url, pw2;
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
        pw = password.getText().toString();
        pw2 = password2.getText().toString();
        password_confirm = (Button) findViewById(R.id.password_confirm);
        password_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pw.equals(pw2)) {
                    Log.d("Phone", pw + "+" + pw2);
                    Toast.makeText(PasswordActivity.this, "确认密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    pw = new String(Hex.encodeHex(DigestUtils.md5(pw)));
                    code = new String(Hex.encodeHex(DigestUtils.md5(phone)));
                    nn = nickname.getText().toString();
                    join();

                }
            }
        });
    }

    private void join() {
        url = "http://114.67.134.219:8080/moyu/user/join";
        final int flag = 1;
        HttpUtil.postjoin(url, phone, pw, nn, code, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsetext = response.body().string();
                Log.d("Phone", responsetext);
                if (Utility.handlejoinResponse(responsetext, pw, phone)) {
                    Intent successintent = new Intent(PasswordActivity.this, MainActivity.class);
                    startActivity(successintent);
                    LoginActivity.mLoginActivity.finish();
                    finish();
                } else {
                    Looper.prepare();
                    Toast.makeText(PasswordActivity.this, "数据错误，注册失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
}
