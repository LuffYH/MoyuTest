package com.example.moyutest;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.moyutest.adapter.ContentAdapter;
import com.example.moyutest.gson.ContentJson;
import com.example.moyutest.model.Contents;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("deprecation")
public class MainFragment extends Fragment {
    private LinearLayout search;
    private Context mContext;
    private Handler handler = new Handler();
    private SwipeRefreshLayout swipeRefresh;
    private List<Contents> contentsList = new ArrayList<>();
    private ContentAdapter adapter;
    private int lastVisibleItem;
    private int intFrom = 0;
    private int intSize = 6;
    private int flagnomore = 0;
    private int times = 0;
    private String strFrom = "0", strSize = "6";
    private GridLayoutManager layoutManager;
    private String mauthorName;
    private String mcontent;
    private String mauthorAvatar;
    private int mcommentAmount;
    private int mimageAmount;
    private int mweiboLike;
    private int mweiboId;
    private int mauthorId;
    private String mcreateTime;
    private Contents[] acontents;
    private RecyclerView recyclerView;

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
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(mContext, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContentAdapter(contentsList);
        recyclerView.setAdapter(adapter);
        initRefresh(strFrom, strSize);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                strFrom = "0";
                times = 0;
                initRefresh(strFrom, strSize);

            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1
                        == adapter.getItemCount()) {
                    adapter.changeMoreStatus(ContentAdapter.LOADING_MORE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            strFrom = String.valueOf(intFrom);
                            Log.d("Phone", "strFrom =" + strFrom);
                            initRefresh(strFrom, strSize);
                        }
                    }, 1000);
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1
                        != adapter.getItemCount()) {
                    adapter.changeMoreStatus(ContentAdapter.PULLUP_LOAD_MORE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
        return view;
    }

    private void initRefresh(final String froms, String sizes) {
        String id_token = SharedPreferencesUtil.getIdTokenFromXml(mContext);
        Api api = RetrofitProvider.create().create(Api.class);
        api.weibo(id_token, froms, sizes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContentJson>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ContentJson weibo) {
                        if (froms.equals("0") || froms == "0") {
                            recyclerView.removeAllViews();
                            contentsList.clear();
                        }
                        List<ContentJson.ObjBean> WeiboBean = weibo.getObj();
                        if (WeiboBean == null || WeiboBean.size() == 0) {
                            flagnomore = 0;
                            Log.d("Phone", "checkflag =" + flagnomore);
                            adapter.changeMoreStatus(ContentAdapter.NO_MORE);
                        } else {
                            flagnomore = 1;
                            Log.d("Phone", "size = " + WeiboBean.size());
                            for (int z = 0; z < WeiboBean.size(); z++) {
                                mauthorName = WeiboBean.get(z).getAuthorName();
                                mcontent = WeiboBean.get(z).getWeiboContent();
                                mimageAmount = WeiboBean.get(z).getImageAmount();
                                mweiboLike = WeiboBean.get(z).getWeiboLike();
                                mcreateTime = WeiboBean.get(z).getCreateTime();
                                mcommentAmount = WeiboBean.get(z).getCommentAmount();
                                mweiboId = WeiboBean.get(z).getWeiboId();
                                mauthorAvatar = WeiboBean.get(z).getAuthorAvatar();
                                mauthorId = WeiboBean.get(z).getAuthorId();
                                contentsList.add(new Contents(mweiboId, mauthorId, mauthorName, mauthorAvatar, mcontent, mimageAmount, mcommentAmount, mweiboLike, mcreateTime));
                                Log.d("Phone", "" + WeiboBean);
                            }
                            Log.d("Phone", "flagnomore =" + flagnomore);
                            times++;
                        }
                        adapter.notifyDataSetChanged();
                        intFrom = times * intSize;
                        swipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "获取失败", Toast.LENGTH_LONG).show();
                        swipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        if (flagnomore == 1) {
                            Toast.makeText(mContext, "加载成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
