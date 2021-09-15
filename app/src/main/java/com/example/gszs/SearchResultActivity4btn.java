package com.example.gszs;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.gszs.adapter.ResultPagerAdapter;
import com.example.gszs.adapter.ResultRv4btnAdapter;
import com.example.gszs.adapter.ResultRvAdapter1;
import com.example.gszs.bean.GoodsInfo;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SearchResultActivity4btn extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_4_btn);
        initView();
    }
    //接受btn传来的title关键字
    private String recQuery(){
        return getIntent().getExtras().getString("title");
    }
    //接受传来的itemTag来显示不同的item布局
    private int recItemTag(){
        return getIntent().getExtras().getInt("ItemTag");
    }
    //根据btn顺序返回对应item布局
    private int Which_item(int ItemTag){
        ItemTag = recItemTag();
        int temp = 0;
        if(ItemTag == 1) temp = R.layout.item_result_activity_4_btn_1;
        else if(ItemTag == 2) temp = R.layout.item_result_activity_4_btn_2;
        else if(ItemTag == 3) temp = R.layout.item_result_activity_4_btn_3;
        else if(ItemTag == 4) temp = R.layout.item_result_activity_4_btn_4;
        return temp;
    }

    private void initView(){
        //设置toolbar标题名称
        String title = recQuery();
        int ItemTag = recItemTag();
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_result_4_btn);
        toolbar.setTitle(title);
        //适配RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView_result_4_btn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ResultRv4btnAdapter(Which_item(recItemTag())));
        back();

    }
    //使导航栏返回键可用
    private void back(){
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_result_4_btn);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }


}