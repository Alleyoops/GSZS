package com.example.gszs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gszs.R;

public class ResultRv4btnAdapter extends RecyclerView.Adapter<ResultRv4btnAdapter.ResultRv4btnViewHolder>  {

    private int layoutId;

    public ResultRv4btnAdapter(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public ResultRv4btnViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ResultRv4btnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultRv4btnViewHolder holder, int position) {
//        GoodsInfo fakeInfoBean = fakeInfo.get(position);
//        holder.tvIndex.setVisibility(View.GONE);
//        holder.tvName.setText(fakeInfoBean.getName());
//        holder.ivAvatar.setImageResource(fakeInfoBean.getPhoto());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ResultRv4btnViewHolder extends RecyclerView.ViewHolder {
//        public TextView attr_choose;
//        public ImageView photo_choose;
//        public TextView desc_attr_choose;
        public ResultRv4btnViewHolder(View itemView) {
            super(itemView);
//            attr_choose = (TextView) itemView.findViewById(R.id.attr_choose);
//            photo_choose = (ImageView) itemView.findViewById(R.id.photo_choose);
//            desc_attr_choose = (TextView) itemView.findViewById(R.id.desc_attr_choose);
        }
    }
}