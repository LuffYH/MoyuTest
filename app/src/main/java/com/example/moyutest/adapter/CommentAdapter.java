/*
package com.example.moyutest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moyutest.R;

import com.example.moyutest.model.Comments;

import java.util.List;

*/
/**
 * Created by Administrator on 2017/12/6.
 *//*


public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Comments> mCommentsList;
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
        public TextView authorName;
        public TextView commentcontent;
        public TextView createtime;
        public ImageView commentImage;

        public ItemViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view;
            authorName = (TextView) view.findViewById(R.id.comment_profile_name);
            commentcontent = (TextView) view.findViewById(R.id.comment_content);
            createtime = (TextView) view.findViewById(R.id.comment_time);
            commentImage = (ImageView) view.findViewById(R.id.comment_img);
        }
    }

    */
/**
     * 底部FootView布局
     *//*

    public static class FootViewHolder extends RecyclerView.ViewHolder {
        public TextView foot_view_item_tv;

        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv = (TextView) view.findViewById(R.id.foot_view);
            foot_view_item_tv.setTextSize(15);
        }
    }

    public CommentAdapter(List<Comments> commentList) {
        mCommentsList = commentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
            CommentAdapter.ItemViewHolder itemViewHolder = new CommentAdapter.ItemViewHolder(view);
            return itemViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View foot_view = LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            CommentAdapter.FootViewHolder footViewHolder = new CommentAdapter.FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentAdapter.ItemViewHolder) {
            Comments Comments = mCommentsList.get(position);
            ((ItemViewHolder) holder).authorName.setText(Comments.getAuthorName());
            ((ItemViewHolder) holder).commentcontent.setText(Comments.getCommentContent());
            ((ItemViewHolder) holder).createtime.setText(Comments.getCreateTime());

            Glide.with(mContext).load("http://120.79.42.49:8080/moyu/images/avatar/" + Comments.getAuthorAvatar()).into(((CommentAdapter.ItemViewHolder) holder).commentImage);

         */
/*   ((ItemViewHolder) holder).commentfeedlike.setText("赞 " + Comments.getCommentLike());*//*

        } else if (holder instanceof CommentAdapter.FootViewHolder) {
            CommentAdapter.FootViewHolder footViewHolder = (CommentAdapter.FootViewHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText("上滑加载更多");
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
        if (mCommentsList.size() < 6) {
            return mCommentsList.size();
        } else {
            return mCommentsList.size() + 1;
        }
    }

    */
/**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     *
     * @param status
     *//*

    public void changeMoreStatus(int status) {
        load_more_status = status;
        if (load_more_status == 1) {
            notifyDataSetChanged();
        }
    }
}
*/
