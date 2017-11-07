package com.example.moyutest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moyutest.ContentActivity;
import com.example.moyutest.R;
import com.example.moyutest.db.News;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context mContext;
    private List<News> mNewsList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView newsImage;
        TextView newsName,news_content;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            newsImage = (ImageView) view.findViewById(R.id.news_image);
            newsName = (TextView) view.findViewById(R.id.news_name);
            news_content = (TextView) view.findViewById(R.id.news_content);
        }
    }

    public NewsAdapter(List<News> newsList) {
        mNewsList = newsList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                News news = mNewsList.get(position);
                Intent intent = new Intent(mContext, ContentActivity.class);
                intent.putExtra(ContentActivity.NEWS_NAME, news.getName());
                intent.putExtra(ContentActivity.NEWS_IMAGE_ID, news.getImageId());
                intent.putExtra(ContentActivity.NEWS_CONTENT, news.getContent());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = mNewsList.get(position);
        holder.newsName.setText(news.getName());
        holder.news_content.setText(news.getContent());
        Glide.with(mContext).load(news.getImageId()).into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }


}
