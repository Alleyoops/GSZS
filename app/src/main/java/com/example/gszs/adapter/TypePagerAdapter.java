package com.example.gszs.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gszs.ui.Fragment.PagerFragment1;
import com.example.gszs.ui.Fragment.PagerFragment2;
import com.example.gszs.ui.Fragment.PagerFragment3;

import java.util.ArrayList;

public class TypePagerAdapter extends FragmentStatePagerAdapter {


    private ArrayList<String> mTitleArray; // 声明一个标题文字队列
    // 碎片页适配器的构造函数，传入碎片管理器与标题队列
    public TypePagerAdapter(FragmentManager fm,ArrayList<String> TitleArray) {
        super(fm);
        mTitleArray = TitleArray;
    }

    // 获取指定位置的碎片Fragment
    public Fragment getItem(int position) {
        if (position == 0) { // 第一页展示水果
            return new PagerFragment1();
        }  if (position == 1) { // 第二页展示蔬菜
            return new PagerFragment2();
        }  if (position == 2){  // 第三页展示坚果
            return new PagerFragment3();
        }
        return new PagerFragment1();//显示第一个Fragment
    }

    // 获取碎片Fragment的个数
    public int getCount() {
        return mTitleArray.size();
    }

    // 获得指定碎片页的标题文本
    public CharSequence getPageTitle(int position) {
        return mTitleArray.get(position);
    }

}
