package com.example.gszs.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gszs.GoodsDetailActivity;
import com.example.gszs.R;
import com.example.gszs.SearchResultActivity;
import com.example.gszs.bean.GoodsInfo;

import java.util.List;

public class HomeRvAdapter extends RecyclerView.Adapter<HomeRvAdapter.HomeRvViewHolder>  {

    private int layoutId;
    private List<GoodsInfo> goods;

    public HomeRvAdapter(List<GoodsInfo> goods,int layoutId) {
        this.goods = goods;
        this.layoutId = layoutId;
    }

    @Override
    public HomeRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new HomeRvViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(HomeRvViewHolder holder, int position) {
//        GoodsInfo fakeInfoBean = fakeInfo.get(position);
//        holder.tvIndex.setVisibility(View.GONE);
//        holder.tvName.setText(fakeInfoBean.getName());
//        holder.ivAvatar.setImageResource(fakeInfoBean.getPhoto());
        GoodsInfo good = goods.get(position);
        holder.photo_home.setImageResource(good.getPhoto());
        holder.name_home.setText(good.getName());
        holder.desc_home.setText(good.getDescription());
        holder.price_home.setText("¥"+String.valueOf(good.getPrice()));
        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 携带item信息跳转到SearchResultActivity
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("info",good);
                intent.putExtras(bundle);
                intent.setClass(v.getContext(), GoodsDetailActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }


    class HomeRvViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo_home;
        public TextView name_home;
        public TextView desc_home;
        public TextView price_home;
        public TextView src_home;
        public ConstraintLayout cl;
        public HomeRvViewHolder(View itemView) {
            super(itemView);
            photo_home = (ImageView) itemView.findViewById(R.id.photo_home);
            name_home = (TextView) itemView.findViewById(R.id.name_home);
            desc_home = (TextView) itemView.findViewById(R.id.desc_home);
            price_home = (TextView) itemView.findViewById(R.id.price_home);
            src_home = (TextView) itemView.findViewById(R.id.src_home);
            cl = (ConstraintLayout) itemView.findViewById(R.id.cl);
        }
    }
}
