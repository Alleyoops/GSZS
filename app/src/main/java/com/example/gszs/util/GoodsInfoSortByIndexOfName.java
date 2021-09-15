package com.example.gszs.util;

import android.util.Log;

import com.example.gszs.bean.GoodsInfo;

import java.util.ArrayList;
import java.util.Comparator;

public class GoodsInfoSortByIndexOfName {
    public ArrayList<GoodsInfo> Sort(ArrayList<GoodsInfo> info){

        ArrayList<String> temp = new ArrayList<>();
        ArrayList<GoodsInfo> tempInfo = new ArrayList<>();
        for (int i = 0; i < info.size(); i++) {
            temp.add(TextPinyinUtil.getInstance().getPinyin(info.get(i).name));
        }
        temp.sort(Comparator.naturalOrder());

        for (int i = 0; i < temp.size();i++){
            for (int j = 0; j < info.size(); j++) {
                if (TextPinyinUtil.getInstance().getPinyin(info.get(j).name).equals(temp.get(i)))
                {
                    tempInfo.add(info.get(j));

                }
            }
        }
        return tempInfo;
    }


    private static GoodsInfoSortByIndexOfName GISBIO = new GoodsInfoSortByIndexOfName();
    public static GoodsInfoSortByIndexOfName getInstance() {
        return GISBIO;
    }
}
