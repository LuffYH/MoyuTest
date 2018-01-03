package com.example.moyutest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moyutest.db.MoyuUser;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.BaseActivity;
import com.example.moyutest.util.HandleResponse;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.google.gson.JsonObject;

import org.litepal.crud.DataSupport;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PersonalDataActivity extends BaseActivity implements View.OnClickListener {
    private EditText nickname, address, resume;
    private RadioGroup genderrg;
    private RadioButton genderrb;
    private TextView confirm, cancel;
    private String strnn, straddress, strresume;
    private int gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        nickname = (EditText) findViewById(R.id.pnicknane);
        address = (EditText) findViewById(R.id.paddress);
        resume = (EditText) findViewById(R.id.presume);
        genderrg = (RadioGroup) findViewById(R.id.psex);
        confirm = (TextView) findViewById(R.id.pconfirm);
        cancel = (TextView) findViewById(R.id.p_cancel);
        MoyuUser user = DataSupport.findFirst(MoyuUser.class);
        nickname.setText(user.getNickname());
        address.setText(user.getLocation());
        resume.setText(user.getIntroduction());
        if (user.getGender() == 1) {
            genderrg.check(R.id.man);
            gender = 1;
        } else {
            genderrg.check(R.id.women);
            gender = 2;
        }
        genderrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                genderrb = (RadioButton) findViewById(radioButtonId);
                if (genderrb.getText().toString().equals("男")) {
                    gender = 1;
                } else {
                    gender = 2;
                }
            }
        });
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.p_cancel:
                finish();
                break;
            case R.id.pconfirm:
                String token = SharedPreferencesUtil.getIdTokenFromXml(this);
                Api api = RetrofitProvider.create().create(Api.class);
                strnn = nickname.getText().toString();
                straddress = address.getText().toString();
                strresume = resume.getText().toString();
                api.edit(token, strnn, String.valueOf(gender), strresume, straddress)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {

                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
//                                HandleResponse.handlePersonalData(strnn, Integer.parseInt(gender), straddress, strresume);
                                MoyuUser user = new MoyuUser();
                                user.setNickname(strnn);
                                user.setGender(gender);
                                user.setLocation(straddress);
                                user.setIntroduction(strresume);
                                String id = SharedPreferencesUtil.getIdFromDB();
                                user.updateAll("userid = ?", id);
                                Intent Resultintent = new Intent();
                                Resultintent.putExtra("strnn", strnn);
                                setResult(1, Resultintent);
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                Toast.makeText(PersonalDataActivity.this, "成功", Toast.LENGTH_SHORT).show();

                            }
                        });
                break;
            default:
                break;
        }
    }
}
