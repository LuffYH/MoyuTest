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
import com.example.moyutest.db.Contents;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private Context mContext;
    private List<Contents> mContentsList;

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

    public ContentAdapter(List<Contents> contentsList) {
        mContentsList = contentsList;
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
                Contents contents = mContentsList.get(position);
                Intent intent = new Intent(mContext, ContentActivity.class);
                intent.putExtra(ContentActivity.CONTENTS_NAME, contents.getName());
                intent.putExtra(ContentActivity.CONTENTS_IMAGE_ID, contents.getImageId());
                intent.putExtra(ContentActivity.CONTENTS_CONTENT, contents.getContent());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contents contents = mContentsList.get(position);
        holder.newsName.setText(contents.getName());
        holder.news_content.setText(contents.getContent());
        Glide.with(mContext).load(contents.getImageId()).into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return mContentsList.size();
    }


}
