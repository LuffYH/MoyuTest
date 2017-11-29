package com.example.moyutest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moyutest.model.MoyuUser;

import org.litepal.crud.DataSupport;

public class MyFragment extends Fragment {
    private TextView follow, fans, nickname;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        follow = (TextView) view.findViewById(R.id.my_follow);
        fans = (TextView) view.findViewById(R.id.my_fans);
        nickname = (TextView) view.findViewById(R.id.my_nickname);
        MoyuUser user = DataSupport.findFirst(MoyuUser.class);
        follow.setText(String.valueOf(user.getFollow()));
        fans.setText(String.valueOf(user.getFollower()));
        nickname.setText(user.getNickname());
        return view;
    }


}
