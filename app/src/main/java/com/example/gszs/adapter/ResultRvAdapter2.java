package com.example.gszs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gszs.R;

public class ResultRvAdapter2 extends RecyclerView.Adapter<ResultRvAdapter2.ResultRvViewHolder>  {

    private int layoutId;

    public ResultRvAdapter2(int layoutId) {
        this.layoutId = layoutId;
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
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ResultRvViewHolder extends RecyclerView.ViewHolder {
        public TextView way_eat;
        public ImageView photo_eat;
        public TextView desc_way_eat;
        public ResultRvViewHolder(View itemView) {
            super(itemView);
            way_eat = (TextView) itemView.findViewById(R.id.way_eat);
            photo_eat = (ImageView) itemView.findViewById(R.id.photo_eat);
            desc_way_eat = (TextView) itemView.findViewById(R.id.desc_way_eat);
        }
    }
}
