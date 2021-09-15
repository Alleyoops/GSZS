package com.example.gszs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gszs.R;
import com.example.gszs.bean.Dynamics;
import com.example.gszs.bean.GoodsInfo;

import java.util.ArrayList;

public class BlogRvAdapter extends RecyclerView.Adapter<BlogRvAdapter.ResultRvViewHolder>  {
    private ArrayList<Dynamics> info;
    private int layoutId;

    public BlogRvAdapter(int layoutId, ArrayList<Dynamics> info) {
        this.layoutId = layoutId;
        this.info = info;
    }

    @Override
    public ResultRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ResultRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultRvViewHolder holder, int position) {
//        GoodsInfo fakeInfoBean = fakeInfo.get(position);
//        holder.tvIndex.setVisibility(View.GONE);
//        holder.tvName.setText(fakeInfoBean.getName());
//        holder.ivAvatar.setImageResource(fakeInfoBean.getPhoto());
        Dynamics temp = info.get(position);
        holder.content_tip.setText(temp.getContent());
        holder.date_tip.setText(temp.getTime());
        holder.username_tip.setText(temp.getNickname());
        likePressed(holder.like_tip);

    }

    @Override
    public int getItemCount() {
        return info.size();
    }


    class ResultRvViewHolder extends RecyclerView.ViewHolder {
        public ImageView head_tip;
        public TextView username_tip;
        public TextView date_tip;
        public TextView content_tip;
        public TextView zan_tip;
        public ImageView like_tip;
        public ResultRvViewHolder(View itemView) {
            super(itemView);
            head_tip = (ImageView) itemView.findViewById(R.id.head_tip);
            username_tip = (TextView) itemView.findViewById(R.id.username_tip);
            date_tip = (TextView) itemView.findViewById(R.id.date_tip);
            content_tip = (TextView) itemView.findViewById(R.id.content_tip);
            zan_tip = (TextView) itemView.findViewById(R.id.zan_tip);
            like_tip = (ImageView) itemView.findViewById(R.id.like_tip);
        }
    }
    //赞 的点击事件
    private void likePressed(ImageView like){
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换选中状态
                like.setSelected(!like.isSelected());
                //点赞数加一
                //····
            }
        });
    }
}
