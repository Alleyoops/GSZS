package com.example.gszs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.gszs.adapter.ResultPagerAdapter;
import com.example.gszs.adapter.TypePagerAdapter;
import com.example.gszs.bean.GoodsInfo;
import com.example.gszs.ui.notifications.NotificationsFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    private String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initView();
    }
    //接受搜索框传来的关键字
    private String recQuery(){
        return getIntent().getExtras().getString("query");
    }
    //接受点击item传来的GoodsInfo对象实例
    private GoodsInfo recItem(){
        return (GoodsInfo) getIntent().getSerializableExtra("info");
    }

    private void initView(){
        String searchWord = recQuery();
        LinearLayout ll_result = (LinearLayout) findViewById(R.id.ll_result);
        ImageView nothing = (ImageView) findViewById(R.id.nothing_result);
        GoodsInfo info = recItem();
        if (info == null){
            nothing.setVisibility(View.VISIBLE);
            ll_result.setVisibility(View.INVISIBLE);
            Toast.makeText(this,"没有请求接口："+searchWord,Toast.LENGTH_LONG).show();
            back();
        } else {
            tag = info.name;
            androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_result);
            ImageView imageView = (ImageView)findViewById(R.id.photo_result);
            imageView.setImageResource(info.photo);
            toolbar.setTitle(info.name);
            initTabLayout();
            back();
        }
        //点击4个btn
        findViewById(R.id.btn1).setOnClickListener(new myOnclickListener());
        findViewById(R.id.btn2).setOnClickListener(new myOnclickListener());
        findViewById(R.id.btn3).setOnClickListener(new myOnclickListener());
        findViewById(R.id.btn4).setOnClickListener(new myOnclickListener());

    }
    //使导航栏返回键可用
    private void back(){
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_result);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }
    //初始化标签布局TabLayout 以及 初始化 翻页试图布局ViewPager
    private  void initTabLayout(){
        TabLayout tab_title = (TabLayout)findViewById(R.id.tab_title_result);
        ViewPager tab_vp = (ViewPager)findViewById(R.id.tab_vp_result);

        //TabLayout
        ArrayList<String> mTitleArray = new ArrayList<>();
        mTitleArray.add("挑");
        mTitleArray.add("吃");
        mTitleArray.add("果蔬简历");
        mTitleArray.add("网友支招");
        // 给tab_title添加一个指定文字的标签
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(0)));
        // 给tab_title添加一个指定文字的标签
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(1)));
        // 给tab_title添加一个指定文字的标签
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(2)));
        // 给tab_title添加一个指定文字的标签
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(3)));
        // 给tab_title添加标签选中监听器，该监听器默认绑定了翻页视图vp_content
        tab_title.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(tab_vp));

        //ViewPager
        // 构建一个商品信息的翻页适配器
        ResultPagerAdapter adapter = new ResultPagerAdapter(getSupportFragmentManager(), mTitleArray);
        // 给vp_content设置商品翻页适配器
        tab_vp.setAdapter(adapter);
        // 给vp_content添加页面变更监听器
        tab_vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 选中tab_title指定位置的标签
                tab_title.getTabAt(position).select();
                //initSearchView(position);
            }
        });
    }
    //自定义关于4个btn的点击事件监听器
    class myOnclickListener implements View.OnClickListener{
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn1:
                    bundle.putString("title","食疗功效"+"-"+tag);
                    bundle.putInt("ItemTag",1);
                    intent.putExtras(bundle);
                    intent.setClass(v.getContext(),SearchResultActivity4btn.class);
                    startActivity(intent);
                    break;
                case R.id.btn2:
                    bundle.putString("title","能不能吃"+"-"+tag);
                    bundle.putInt("ItemTag",2);
                    intent.putExtras(bundle);
                    intent.setClass(v.getContext(),SearchResultActivity4btn.class);
                    startActivity(intent);
                    break;
                case R.id.btn3:
                    bundle.putString("title","营养成分"+"-"+tag);
                    bundle.putInt("ItemTag",3);
                    intent.putExtras(bundle);
                    intent.setClass(v.getContext(),SearchResultActivity4btn.class);
                    startActivity(intent);
                    break;
                case R.id.btn4:
                    bundle.putString("title","食用禁忌"+"-"+tag);
                    bundle.putInt("ItemTag",4);
                    intent.putExtras(bundle);
                    intent.setClass(v.getContext(),SearchResultActivity4btn.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}