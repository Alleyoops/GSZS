package com.example.gszs.bean;

import android.nfc.Tag;

import com.example.gszs.R;
import com.example.gszs.adapter.TypePagerAdapter;
import com.example.gszs.util.GoodsInfoSortByIndexOfName;
import com.example.gszs.util.TextPinyinUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class GoodsInfo implements Serializable {
//继承序列化接口方可被bundle传输

    public int id;//id，不用
    public String name;//名称
    public float price;//价格
    public String description;//描述
    public int typename;//种类名
    public int typeid;//种类ID，不用
    public int photo;//图片资源编号.....setImageResource

    public String index;//名字首字母（排序用）

    //重写构造函数
    public GoodsInfo() {
        name = "";
        price = 0;
        description = "";
        typename = 0;//123三个类型
        photo = 0;
    }

    public String getIndex() {
        index = Character.toString(TextPinyinUtil.getInstance().getPinyin(name).charAt(0)).toUpperCase();//通过名称获得首字母
        return index;
    }

    public String getName() {
        return name;
    }

    public int getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    // 声明一个商品的名称数组
    private static String[] mNameArray = {
            "菠萝", "百香果", "菠萝蜜","土豆","花生","橙子","柑橘","西瓜","冬瓜","开心果","白菜","夏威夷果","苹果","香蕉",
            "荔枝","梨子","板栗","葡萄","西柚","樱桃","草莓","龙眼","芒果","菠菜","豆角"
    };
    // 声明一个商品的描述数组
    private static String[] mDescArray = {
            "看到了吧，这是菠萝，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是百香果，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是菠萝蜜，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是土豆，它是蔬菜，吃个桃桃，好凉凉。",
            "看到了吧，这是花生，它是坚果，吃个桃桃，好凉凉。",
            "看到了吧，这是橙子，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是柑橘，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是西瓜，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是冬瓜，它是蔬菜，吃个桃桃，好凉凉。",
            "看到了吧，这是开心果，它是坚果，吃个桃桃，好凉凉。",
            "看到了吧，这是白菜，它是蔬菜，吃个桃桃，好凉凉。",
            "看到了吧，这是夏威夷果，它是坚果，吃个桃桃，好凉凉。",
            "看到了吧，这是苹果，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是香蕉，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是荔枝，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是梨子，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是板栗，它是坚果，吃个桃桃，好凉凉。",
            "看到了吧，这是葡萄，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是西柚，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是樱桃，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是草莓，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是龙眼，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是芒果，它是水果，吃个桃桃，好凉凉。",
            "看到了吧，这是菠菜，它是蔬菜，吃个桃桃，好凉凉。",
            "看到了吧，这是豆角，它是蔬菜，吃个桃桃，好凉凉。"
    };
    // 声明一个商品的价格数组
    private static float[] mPriceArray = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,
            15,16,17,18,19,20,21,22,23,24,25};
    // 声明一个商品的种类数组（方便分类排序）
    private static int[] mTypeArray= {1,1,1,2,3,1,1,1,2,3,2,3,1,1,
            1,1,3,1,1,1,1,1,1,2,2};
    // 声明一个商品的图数组
    private static int[] mPicArray = {
            R.drawable.boluo, R.drawable.baixiangguo, R.drawable.boluomi,
            R.drawable.tudou, R.drawable.huasheng, R.drawable.chengzi,
            R.drawable.ganju,R.drawable.xigua,R.drawable.donggua,
            R.drawable.kaixinguo,R.drawable.baicai,R.drawable.xiaweiyiguo,
            R.drawable.pingguo, R.drawable.xiangjiao,R.drawable.lizhi ,
            R.drawable.lizi, R.drawable.banli,R.drawable.putao,
            R.drawable.youzi, R.drawable.yingtao,R.drawable.caomei,
            R.drawable.longyan, R.drawable.mangguo,R.drawable.bocai,
            R.drawable.doujiao,
    };

    // 获取默认的商品信息列表
    public static ArrayList<GoodsInfo> getDefaultList(int typename) {
        ArrayList<GoodsInfo> mGoodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.name = mNameArray[i];
            info.price= mPriceArray[i];
            info.description = mDescArray[i];
            info.typename = mTypeArray[i];
            info.photo = mPicArray[i];
            if (info.typename == typename)//GoodsInfo的类型与选中标签匹配
            {
                mGoodsList.add(info);
            }
        }
        // 按照名字首字母返回
        return GoodsInfoSortByIndexOfName.getInstance().Sort(mGoodsList);
    }
    // 获取所有的商品信息列表
    public static ArrayList<GoodsInfo> getAllList() {
        ArrayList<GoodsInfo> mGoodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.name = mNameArray[i];
            info.price= mPriceArray[i];
            info.description = mDescArray[i];
            info.typename = mTypeArray[i];
            info.photo = mPicArray[i];
            mGoodsList.add(info);
        }
        // 按默认顺序返回
        return mGoodsList;
    }

}
