package com.example.gszs.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gszs.R;
import com.example.gszs.SearchResultActivity;
import com.example.gszs.bean.GoodsInfo;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>  {

    private List<GoodsInfo> contacts;
    private int layoutId;
    private int count=0;

    public ContactsAdapter(List<GoodsInfo> contacts, int layoutId) {
        this.contacts = contacts;
        this.layoutId = layoutId;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        GoodsInfo contact = contacts.get(position);
        // 下面的if语句使具有同一个index的列表项显示在同一个index布局下
        if (position == 0 || !contacts.get(position-1).getIndex().equals(contact.getIndex())) {
            holder.tvIndex.setVisibility(View.VISIBLE);
            holder.tvIndex.setText(contact.getIndex());
        } else {
            holder.tvIndex.setVisibility(View.GONE);
        }
        holder.tvName.setText(contact.getName());
        holder.ivAvatar.setImageResource(contact.getPhoto());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 携带item信息跳转到SearchResultActivity
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("info",contact);
                    intent.putExtras(bundle);
                    intent.setClass(v.getContext(), SearchResultActivity.class);
                    v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIndex;
        public ImageView ivAvatar;
        public TextView tvName;
        public LinearLayout ll;
        public ContactsViewHolder(View itemView) {
            super(itemView);
            tvIndex = (TextView) itemView.findViewById(R.id.tv_index);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ll = (LinearLayout) itemView.findViewById(R.id.item);
        }
    }
}
