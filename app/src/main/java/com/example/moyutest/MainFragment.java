package com.example.moyutest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.moyutest.adapter.ContentViewHolder;
import com.example.moyutest.gson.ContentJson;
import com.example.moyutest.model.Contents;
import com.example.moyutest.util.Api;
import com.example.moyutest.util.RetrofitProvider;
import com.example.moyutest.util.SharedPreferencesUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("deprecation")
public class MainFragment extends Fragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private LinearLayout search;
    private Context mContext;
    private List<Contents> contentsList = new ArrayList<>();
    private RecyclerArrayAdapter<Contents> adapter;
    private int intPage = 1;
    private int intSize = 6;
    private LinearLayoutManager layoutManager;
    private String mauthorName;
    private String mcontent;
    private String mauthorAvatar;
    private boolean mmylike = true;
    private int mcommentAmount;
    private int mimageAmount;
    private int mweiboLike;
    private int mweiboId;
    private int mauthorId;
    private String mcreateTime;
    private EasyRecyclerView recyclerView;

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
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Contents>(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ContentViewHolder(parent);
            }
        });
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setRefreshListener(this);
        onRefresh();
        return view;
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        intPage = 1;
        initRefresh(intPage, intSize);
    }

    @Override
    public void onLoadMore() {
        intPage = intPage + 1;
        initRefresh(intPage, intSize);
    }

    private void initRefresh(final int page, int sizes) {
        String id_token = SharedPreferencesUtil.getIdTokenFromXml(mContext);
        Api api = RetrofitProvider.create().create(Api.class);
        api.weibo(id_token, page, sizes, "desc", "create_time")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContentJson>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ContentJson weibo) {
                        Log.d("Phone", "" + weibo);
                        contentsList.clear();
                        List<ContentJson.ObjBean> WeiboBean = weibo.getObj();
                        for (int z = 0; z < WeiboBean.size(); z++) {
                            mauthorName = WeiboBean.get(z).getAuthorName();
                            mcontent = WeiboBean.get(z).getContent();
                            mimageAmount = WeiboBean.get(z).getImageAmount();
                            mweiboLike = WeiboBean.get(z).getLikeAmount();
                            mcreateTime = WeiboBean.get(z).getCreateTime();
                            mcommentAmount = WeiboBean.get(z).getCommentAmount();
                            mweiboId = WeiboBean.get(z).getMicroBlogId();
                            mauthorAvatar = WeiboBean.get(z).getAuthorAvatar();
                            mauthorId = WeiboBean.get(z).getAuthorId();
                            mmylike = WeiboBean.get(z).isMyLike();
                            contentsList.add(new Contents(mweiboId, mauthorId, mauthorName, mauthorAvatar, mcontent, mimageAmount, mcommentAmount, mweiboLike, mcreateTime, mmylike));
                        }
                        adapter.addAll(contentsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
