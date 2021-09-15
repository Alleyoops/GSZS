package com.example.gszs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gszs.bean.GoodsInfo;

public class GoodsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        initView();
    }
    //接受点击item传来的GoodsInfo对象实例
    private GoodsInfo recItem(){
        return (GoodsInfo) getIntent().getSerializableExtra("info");
    }
    @SuppressLint("SetTextI18n")
    private void initView(){
        GoodsInfo info = recItem();
        ImageView photo = (ImageView) findViewById(R.id.photo_goods_detail);
        TextView name = (TextView) findViewById(R.id.name_goods_detail);
        TextView desc = (TextView) findViewById(R.id.desc_goods_detail);
        TextView price = (TextView) findViewById(R.id.price_goods_detail);
        photo.setImageResource(info.getPhoto());
        name.setText(info.getName());
        desc.setText(info.getDescription());
        price.setText("¥"+info.getPrice());

    }
}