package com.example.gszs.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gszs.R;
import com.example.gszs.adapter.ContactsAdapter;
import com.example.gszs.bean.GoodsInfo;
import com.example.gszs.databinding.FragmentPagerBinding;
import com.gjiazhe.wavesidebar.WaveSideBar;

import java.util.ArrayList;

public class PagerFragment3 extends Fragment {
    private RecyclerView rvContacts;
    private WaveSideBar sideBar;
    private ArrayList<GoodsInfo> contacts = new ArrayList<>();
    private FragmentPagerBinding binding;
   // private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPagerBinding.inflate(inflater, container, false);
        initView();
        //initSearchView();
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }


    private void initView() {
        rvContacts = binding.recycleView;
        rvContacts.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvContacts.setAdapter(new ContactsAdapter(contacts, R.layout.item_pager_fragment));

        sideBar = binding.sideBar;
        sideBar.setPosition(WaveSideBar.POSITION_RIGHT);
        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i=0; i<contacts.size(); i++) {
                    if (contacts.get(i).getIndex().equals(index)) {
                        ((LinearLayoutManager) rvContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    private void initData() {
        contacts.addAll(GoodsInfo.getDefaultList(3));
    }
    public void changeRv(ArrayList<GoodsInfo> temp){
        rvContacts = binding.recycleView;
        rvContacts.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvContacts.setAdapter(new ContactsAdapter(temp, R.layout.item_pager_fragment));
        System.out.println(temp);
    }
//    // 初始化搜索框
//    private void initSearchView() {
//        searchView = getActivity().findViewById(R.id.searchView);
//        // 给搜索框设置文本变化监听器
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            //            单击搜索按钮时激发该方法
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //实际应用中应该在该方法内执行实际查询
//                //携带搜索关键字query跳转到搜索结果（即水果详情页面）页面
//                //传递多个数据可以尝试用Bundle，这里只传一个字符串，就免了
//                Intent intent = new Intent();
//                intent.putExtra("query",query);
//                intent.setClass(getContext(), SearchResultActivity.class);
//                startActivity(intent);
//                return false;
//            }
//            // 用户输入时激发该方法
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ArrayList<GoodsInfo> temp = new ArrayList<>();
//                int positon = NotificationsFragment.typeChoose;
//                contacts = GoodsInfo.getDefaultList(positon+1);
//                // 保存所有名字
//                ArrayList<String> names = new ArrayList<>();
//                for ( int i =0 ;i<contacts.size();i++){
//                    names.add(contacts.get(i).name);
//                }
//                //根据newText筛选contacts的内容给temp
//                temp.clear();
//                for (int i = 0 ; i < names.size();i++ ){
//                    String name =names.get(i);
//                    if(name.contains(newText))
//                        temp.add(contacts.get(i));
//                }
//                //把temp填进列表
//                rvContacts.setAdapter(new ContactsAdapter(temp, R.layout.item_pager_fragment));
//                return true;
//            }
//        });
//    }
}