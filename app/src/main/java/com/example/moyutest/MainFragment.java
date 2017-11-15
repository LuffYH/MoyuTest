package com.example.moyutest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.moyutest.adapter.ContentAdapter;
import com.example.moyutest.db.Contents;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainFragment extends Fragment {
    private LinearLayout search;
    private Context mContext;
    private SwipeRefreshLayout swipeRefresh;
    private Contents[] acontents = {
            new Contents("11111", "1111111111", R.mipmap.ic_launcher),
            new Contents("22222", "2222222222", R.mipmap.ic_launcher),
            new Contents("33333", "3333333333", R.mipmap.ic_launcher),
            new Contents("44444", "4444444444", R.mipmap.ic_launcher),
            new Contents("55555", "5555555555", R.mipmap.ic_launcher),
            new Contents("66666", "6666666666", R.mipmap.ic_launcher),
            new Contents("77777", "7777777777", R.mipmap.ic_launcher)
    };
    private List<Contents> contentsList = new ArrayList<>();
    private ContentAdapter adapter;

    //fragment需要获取activity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        search = (LinearLayout) view.findViewById(R.id.txt_search);
//
//        //点击回车键盘时候触发的事件
//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
//                        Toast.makeText(mContext, "请输入搜索内容", Toast.LENGTH_SHORT).show();
//                    } else {//不为空时才添加标签
//
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });

        initNews();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContentAdapter(contentsList);
        recyclerView.setAdapter(adapter);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
        return view;
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initNews();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initNews() {
        contentsList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(acontents.length);
            contentsList.add(acontents[index]);
        }
    }
}
