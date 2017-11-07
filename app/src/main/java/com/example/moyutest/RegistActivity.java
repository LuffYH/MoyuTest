package com.example.moyutest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moyutest.util.HttpUtil;
import com.mob.MobSDK;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EventHandler eventHandler;
    private Button btnregist;
    private Button btnrequest;
    private EditText etphone; // 手机号输入框
    private EditText etcode; // 验证码输入框
    int i = 60; // 设置短信验证提示时间为30s
    private String phoneNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        toolbar = (Toolbar) findViewById(R.id.registtb);
        etphone = (EditText) findViewById(R.id.regist_phone);
        etcode = (EditText) findViewById(R.id.code);
        btnregist = (Button) findViewById(R.id.btn_regist);
        btnrequest = (Button) findViewById(R.id.btn_request);
        toolbar.setNavigationIcon(R.drawable.back);
        btnrequest.setOnClickListener(this);
        btnregist.setOnClickListener(this);
        setSupportActionBar(toolbar);
        //返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MobSDK.init(RegistActivity.this, "2203567c909c8", "1f55c62c190d7b53c4c5d55d2f1f9f54");
// 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    String msgT = throwable.getMessage();
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 处理你自己的逻辑
                        Message msg = new Message();
                        msg.arg1 = event;
                        msg.arg2 = result;
                        msg.obj = data;
                        handler.sendMessage(msg);
                    }
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Message msg = new Message();
                        msg.arg1 = event;
                        msg.arg2 = result;
                        msg.obj = data;
                        handler.sendMessage(msg);
                    }
                }
            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }

    //点击监听
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    //判断是否点击edittext
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {

        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0];
            int top = l[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    //隐藏keyboard
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public void onClick(View v) {
        phoneNums = etphone.getText().toString();
        switch (v.getId()) {
            case R.id.btn_request:
                if (!judgePhoneNums(phoneNums)) {// 判断输入号码是否正确
                    return;
                }
                checkPhone(); // 判断输入号码是否正确
                break;
            case R.id.btn_regist:
                SMSSDK.submitVerificationCode("86", phoneNums, etcode.getText().toString());
                break;
        }
    }

    private void sendSMS() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnrequest.setClickable(false);// 设置按钮不可点击 显示倒计时
                btnrequest.setText("(" + i + ")s");
            }
        });
        SMSSDK.getVerificationCode("86", phoneNums); // 调用sdk发送短信验证
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (i = 60; i > 0; i--) {
                    handler.sendEmptyMessage(-9);
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);// 线程休眠实现读秒功能
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(-8);// 在60秒后重新显示为获取验证码
            }
        }).start();

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                btnrequest.setText("(" + i + ")s");
            } else if (msg.what == -8) {
                btnrequest.setText("发送");
                btnrequest.setClickable(true); // 设置可点击
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast toast = Toast.makeText(getApplicationContext(), "验证成功",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        // 验证成功后跳转主界面
                        //此处可注释掉
                        Intent intent = new Intent(RegistActivity.this, PasswordActivity.class);
                        intent.putExtra("Phone", phoneNums);
                        startActivity(intent);
                        finish();// 成功跳转之后销毁当前页面
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                        Toast.makeText(getApplicationContext(), "验证码大概不对",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }


    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    public static boolean isMobileNO(String mobileNums) {
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    private void checkPhone() {
        String phoneUrl = "http://114.67.134.219:8080/moyu/user/registered";
        HttpUtil.postphone(phoneUrl, phoneNums, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Log.d("Phone", phoneNums);
                if (responseText.equals("false")) {
                    Log.d("Phone", responseText + "没注册");
                    sendSMS();
                } else if (responseText.equals("true")) {
                    Log.d("Phone", responseText + "已注册");
                    Looper.prepare();
                    Toast.makeText(RegistActivity.this, "账号已经注册！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    Log.d("Phone", "与服务器断开连接！" + responseText);
                    Looper.prepare();
                    Toast.makeText(RegistActivity.this, "与服务器断开连接！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
