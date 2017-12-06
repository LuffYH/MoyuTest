package com.example.moyutest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moyutest.ContentActivity;
import com.example.moyutest.R;
import com.example.moyutest.db.Contents;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Contents> mContentsList;
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //没有更多
    public static final int NO_MORE = 2;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public ImageView newsImage;
        public TextView newsName;
        public TextView newscontent;
        public TextView createtime, newsredirect, newscomment, newsfeedlike;

        public ItemViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view;
            newsImage = (ImageView) view.findViewById(R.id.profile_img);
            newsName = (TextView) view.findViewById(R.id.profile_name);
            newscontent = (TextView) view.findViewById(R.id.mention_content);
            createtime = (TextView) view.findViewById(R.id.profile_time);
            newsredirect = (TextView) view.findViewById(R.id.redirect);
            newscomment = (TextView) view.findViewById(R.id.comment);
            newsfeedlike = (TextView) view.findViewById(R.id.feedlike);
        }
    }

    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView foot_view_item_tv;

        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv = (TextView) view.findViewById(R.id.foot_view);
        }
    }

    public ContentAdapter(List<Contents> contentsList) {
        mContentsList = contentsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_weibo, parent, false);
            final ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            itemViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = itemViewHolder.getAdapterPosition();
                    Contents contents = mContentsList.get(position);
                    Intent intent = new Intent(mContext, ContentActivity.class);
                    intent.putExtra(ContentActivity.CONTENTS_AUTHORNAME, contents.getAuthorName());
                    intent.putExtra(ContentActivity.CONTENTS_IMAGEAMOUNT, contents.getImageAmount());
                    intent.putExtra(ContentActivity.CONTENTS_CONTENT, contents.getWeiboContent());
                    intent.putExtra(ContentActivity.CREATE_TIME, contents.getCreateTime());
                    intent.putExtra(ContentActivity.CONTENTS_COMMENTAMOUNT, contents.getCommentAmount());
                    intent.putExtra(ContentActivity.CONTENTS_WEIBOLIKE, contents.getWeiboLike());
                    intent.putExtra(ContentActivity.CONTENTS_WEIBOID, contents.getWeiboId());
                    intent.putExtra(ContentActivity.CONTENTS_AUTHORID, contents.getAuthorId());
                    intent.putExtra(ContentActivity.AUTHOR_AVATAR, contents.getAuthorAvatar());
                    mContext.startActivity(intent);
                }
            });
            return itemViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View foot_view = LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Contents contents = mContentsList.get(position);
            ((ItemViewHolder) holder).newsName.setText(contents.getAuthorName());
            ((ItemViewHolder) holder).newscontent.setText(contents.getWeiboContent());
            ((ItemViewHolder) holder).createtime.setText(contents.getCreateTime());
            ((ItemViewHolder) holder).newsfeedlike.setText("赞 " + contents.getWeiboLike());
//            ((ItemViewHolder) holder).newsredirect.setText("转发 "+contents.getCommentAmount());
            ((ItemViewHolder) holder).newscomment.setText("评论 " + contents.getCommentAmount());
            Glide.with(mContext).load(contents.getImageAmount()).into(((ItemViewHolder) holder).newsImage);
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText("上滑正在加载");
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    break;
                case NO_MORE:
                    footViewHolder.foot_view_item_tv.setText("没有更多");
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (mContentsList.size() < 6) {
            return mContentsList.size();
        } else {
            return mContentsList.size() + 1;
        }
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
        if (load_more_status == 1) {
            notifyDataSetChanged();
        }
    }

}
