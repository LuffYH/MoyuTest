package com.example.moyutest;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moyutest.model.MoyuUser;
import com.example.moyutest.util.HttpUtil;
import com.example.moyutest.util.Utility;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private Button mBtnLogin;
    private boolean loginFlag = false;
    private EditText mEtMobile, mEtPassword;
    private ImageView mIvCleanPhone, mCleanPassword, mIvShowPwd, mLogo;
    private ScrollView mScrollView;
    private LinearLayout mContent, mService;
    private TextView mRegister;
    public static Activity mLoginActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginActivity = this;
        checklogin();
        //获取到SQLiteDatabase的实例
        SQLiteStudioService.instance().start(this);
        Connector.getDatabase();
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mEtMobile = (EditText) findViewById(R.id.et_mobile);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mIvCleanPhone = (ImageView) findViewById(R.id.iv_clean_phone);
        mCleanPassword = (ImageView) findViewById(R.id.clean_password);
        mIvShowPwd = (ImageView) findViewById(R.id.iv_show_pwd);
        mLogo = (ImageView) findViewById(R.id.logo);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mContent = (LinearLayout) findViewById(R.id.content);
        mService = (LinearLayout) findViewById(R.id.service);
        mRegister = (TextView) findViewById(R.id.regist);
        mIvCleanPhone.setOnClickListener(this);
        mCleanPassword.setOnClickListener(this);
        mIvShowPwd.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mEtMobile.clearFocus();
        mEtPassword.clearFocus();
        initView();
        initEvent();
    }

    private void checklogin() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String token = pref.getString("token", "");
        if (token != null && !token.equals("")) {
            loginFlag = true;
            Log.d("Phone", "登陆token = " + token);
        }
        if (loginFlag == true) {
            Intent logintent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(logintent);
            Log.d("Phone", "登录状态");
            finish();
        }
    }

    private void initView() {
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
    }

    private void initEvent() {
        mEtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && mIvCleanPhone.getVisibility() == View.GONE) {
                    mIvCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mIvCleanPhone.setVisibility(View.GONE);
                }
            }
        });
        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && mCleanPassword.getVisibility() == View.GONE) {
                    mCleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mCleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(LoginActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    mEtPassword.setSelection(s.length());
                }
            }
        });
        //禁止键盘弹起的时候可以滚动
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        //适应小屏幕，弹出键盘时候账号密码框向上移动
        mScrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>" + (oldBottom - bottom));
                    int dist = mContent.getBottom() - bottom;
                    if (dist > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                    }
                    mService.setVisibility(View.INVISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>" + (bottom - oldBottom));
                    if ((mContent.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", mContent.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                    }
                    mService.setVisibility(View.VISIBLE);
                }
            }
        });
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
        switch (v.getId()) {
            case R.id.iv_clean_phone:
                mEtMobile.setText("");
                break;
            case R.id.clean_password:
                mEtPassword.setText("");
                break;
            case R.id.iv_show_pwd:
                if (mEtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mIvShowPwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mIvShowPwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd = mEtPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    mEtPassword.setSelection(pwd.length());
                break;
            case R.id.regist:
                Intent intentRegist = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intentRegist);
                break;
            case R.id.btn_login:
                String logurl = "http://114.67.134.219:8080/moyu/login";
                String mb = mEtMobile.getText().toString();
                String pw = mEtPassword.getText().toString();
                String md5pw = new String(Hex.encodeHex(DigestUtils.md5(pw)));
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String token = pref.getString("token", "");
                Long userid = DataSupport.findFirst(MoyuUser.class).getUserId();
                HttpUtil.login(logurl, mb, md5pw, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        String tk = Utility.handletokenResponse(responseText);
                        Log.d("Phone", "登陆返回Json" + responseText);
                        if (tk != null && !tk.equals("")) {
                            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                            editor.putString("token", tk);
                            editor.commit();
                            Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intentMain);
                            finish();
                        } else {
                            Looper.prepare();
                            Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                });
                break;
        }
    }
}
