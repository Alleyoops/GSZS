package com.example.gszs.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gszs.GoodsDetailActivity;
import com.example.gszs.MainActivity;
import com.example.gszs.R;
import com.example.gszs.bean.GoodsInfo;
import com.example.gszs.ui.fourth.FourthViewModel;
import com.example.gszs.ui.fourth.fourth;
import com.example.gszs.util.MyApplication;

import java.util.List;

public class VerticalRvAdapter extends RecyclerView.Adapter<VerticalRvAdapter.VerticalRvViewHolder>  {

    private int layoutId;
    private List<GoodsInfo> goods;
    GoodsInfo good=GoodsInfo.getAllList().get(0);
    private int changeFlag=0;


    public VerticalRvAdapter(List<GoodsInfo> goods, int layoutId) {
        this.layoutId = layoutId;
        this.goods = goods;
    }

    @Override
    public VerticalRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new VerticalRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VerticalRvViewHolder holder, int position) {
//        GoodsInfo fakeInfoBean = fakeInfo.get(position);
//        holder.tvIndex.setVisibility(View.GONE);
//        holder.tvName.setText(fakeInfoBean.getName());
//        holder.ivAvatar.setImageResource(fakeInfoBean.getPhoto());
        good = goods.get(position);
        holder.vertical_title.setText(good.getName());
        change(holder,position);
        if (onClickMyTextView!=null) {
            holder.cl2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickMyTextView.myTextViewClick(position);
                    holder.cl2.setBackgroundColor(Color.WHITE);
                    changeFlag = position;
//                Toast.makeText(v.getContext(),String.valueOf(getItemId(position)),Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return goods.size();
    }
    class VerticalRvViewHolder extends RecyclerView.ViewHolder {
        public TextView vertical_title;
        public ConstraintLayout cl2;
        public VerticalRvViewHolder(View itemView) {
            super(itemView);
            cl2 = (ConstraintLayout) itemView.findViewById(R.id.cl2);
            vertical_title = (TextView) itemView.findViewById(R.id.vertical_title);

        }
    }
    public void change(VerticalRvViewHolder holder,int position){
            if (changeFlag==position){
                holder.cl2.setBackgroundColor(Color.WHITE);
            } else holder.cl2.setBackgroundColor(0xffeeeeee);
    }

    //接口回调
    private onClickMyTextView onClickMyTextView;
    public interface onClickMyTextView{
        public void myTextViewClick(int pos);
    }
    public void setOnClickMyTextView(onClickMyTextView onClickMyTextView) {
        this.onClickMyTextView = onClickMyTextView;
    }




}
