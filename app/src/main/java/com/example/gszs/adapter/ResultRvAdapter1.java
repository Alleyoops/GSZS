package com.example.gszs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gszs.R;

public class ResultRvAdapter1 extends RecyclerView.Adapter<ResultRvAdapter1.ResultRvViewHolder>  {

    private int layoutId;

    public ResultRvAdapter1(int layoutId) {
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
        public TextView attr_choose;
        public ImageView photo_choose;
        public TextView desc_attr_choose;
        public ResultRvViewHolder(View itemView) {
            super(itemView);
            attr_choose = (TextView) itemView.findViewById(R.id.attr_choose);
            photo_choose = (ImageView) itemView.findViewById(R.id.photo_choose);
            desc_attr_choose = (TextView) itemView.findViewById(R.id.desc_attr_choose);
        }
    }
}
