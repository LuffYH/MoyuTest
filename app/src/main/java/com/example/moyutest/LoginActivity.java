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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moyutest.util.Api;
import com.example.moyutest.util.BaseActivity;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.example.moyutest.util.Utility;
import com.google.gson.JsonObject;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.litepal.tablemanager.Connector;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

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
    private String mb, md5pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginActivity = this;
        //获取到SQLiteDatabase的实例
        SQLiteStudioService.instance().start(this);
        Connector.getDatabase();
        checklogin();
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
            Log.d("Phone", "验证是否已经登录token = " + token);
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
                mb = mEtMobile.getText().toString();
                final String pw = mEtPassword.getText().toString();
                md5pw = new String(Hex.encodeHex(DigestUtils.md5(pw)));
                final Api api = RetrofitProvider.create().create(Api.class);
                api.login(mb, md5pw).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {

                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                String responseText = jsonObject.toString();
                                String token = Utility.handletokenResponse(responseText, md5pw, mb);
                                Log.d("Phone", "登陆返回Json" + responseText);
                                if (token != null && !token.equals("")) {
                                    SharedPreferencesUtil.putTokenFromXml(LoginActivity.this, token);
                                    Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intentMain);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
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
        }
    }
}
