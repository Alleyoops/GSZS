package com.example.gszs.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.gszs.CameraActivity;
import com.example.gszs.R;
import com.example.gszs.SearchResultActivity;
import com.example.gszs.adapter.TypePagerAdapter;
import com.example.gszs.bean.GoodsInfo;
import com.example.gszs.databinding.FragmentNotificationsBinding;
import com.example.gszs.ui.Fragment.PagerFragment1;
import com.example.gszs.ui.Fragment.PagerFragment2;
import com.example.gszs.ui.Fragment.PagerFragment3;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private TabLayout tab_title;
    private ViewPager tab_vp;
    private ImageView imageView;
    private ArrayList<String> mTitleArray = new ArrayList<String>(); // 标题文字队列

//    onCreate 先执行，完成与 UI 无关的内容。
//
//    onCreateView 后执行，完成与 UI 相关的内容。
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =//声明一个ViewModel对象,//获取ViewModel,让ViewModel与此activity绑定
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tab_title = binding.tabTitle;
        tab_vp = binding.tabVp;
        //注册观察者,观察数据的变化

        initScan(); // 初始化拍照
        initTabLayout(); // 初始化标签布局
        initTabViewPager(); // 初始化标签翻页
        initSearchView(0);
        return root;
    }

    // 初始化标签布局
    private void initTabLayout() {
        mTitleArray.add("水果");
        mTitleArray.add("蔬菜");
        mTitleArray.add("坚果");
        // 给tab_title添加一个指定文字的标签
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(0)));
        // 给tab_title添加一个指定文字的标签
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(1)));
        // 给tab_title添加一个指定文字的标签
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(2)));
        // 给tab_title添加标签选中监听器，该监听器默认绑定了翻页视图vp_content
        tab_title.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(tab_vp));
    }

    // 初始化标签翻页
    private void initTabViewPager() {
        // 构建一个商品信息的翻页适配器
        TypePagerAdapter adapter = new TypePagerAdapter(getFragmentManager(), mTitleArray);
        // 给vp_content设置商品翻页适配器
        tab_vp.setAdapter(adapter);
        // 给vp_content添加页面变更监听器
        tab_vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 选中tab_title指定位置的标签
                tab_title.getTabAt(position).select();
                initSearchView(position);
            }
        });
    }


    private ArrayList<GoodsInfo> contacts = new ArrayList<>();

        // 初始化搜索框
    public void initSearchView(int position) {
        SearchView searchView = binding.toolbar.searchView;
        LayoutInflater layout=this.getLayoutInflater();
        View view=layout.inflate(R.layout.fragment_pager, null);
        RecyclerView rvContacts = view.findViewById(R.id.recycleView);
        // 给搜索框设置文本变化监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //            单击搜索按钮时激发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                //实际应用中应该在该方法内执行实际查询
                //携带搜索关键字query跳转到搜索结果（即水果详情页面）页面
                //传递多个数据可以尝试用Bundle，这里只传一个字符串，就免了
                Intent intent = new Intent();
                intent.putExtra("query",query);
                intent.setClass(getContext(), SearchResultActivity.class);
                startActivity(intent);
                return false;
            }
            // 用户输入时激发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                contacts = GoodsInfo.getDefaultList(position+1);
                ArrayList<GoodsInfo> temp = new ArrayList<>();
                // 保存所有名字
                ArrayList<String> names = new ArrayList<>();
                for ( int i =0 ;i<contacts.size();i++){
                    names.add(contacts.get(i).name);
                }
                //根据newText筛选contacts的内容给temp
                temp.clear();
                for (int i = 0 ; i < names.size();i++ ){
                    String name =names.get(i);
                    if(name.contains(newText))
                        temp.add(contacts.get(i));
                }
                if (temp.isEmpty())
                {
                    tab_vp.setVisibility(View.INVISIBLE);
                    imageView = binding.nothing;
                    imageView.setVisibility(View.VISIBLE);
                }else {
                    tab_vp.setVisibility(View.VISIBLE);
                    imageView = binding.nothing;
                    imageView.setVisibility(View.INVISIBLE);
                    // 传递参数给pagerFragment
                    FragmentStatePagerAdapter fsp = (FragmentStatePagerAdapter) tab_vp.getAdapter();
                    if (position == 0){
                        PagerFragment1 mFragment = (PagerFragment1) fsp.instantiateItem(tab_vp,position);
                        mFragment.changeRv(temp);
                    }
                    if (position == 1){
                        PagerFragment2 mFragment = (PagerFragment2) fsp.instantiateItem(tab_vp,position);
                        mFragment.changeRv(temp);
                    }
                    if (position == 2){
                        PagerFragment3 mFragment = (PagerFragment3) fsp.instantiateItem(tab_vp,position);
                        mFragment.changeRv(temp);
                    }
                }

                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    //初始化拍照按钮
    public void initScan(){
        ImageView iv_scan = binding.toolbar.ivScan;
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(getContext(), CameraActivity.class));
            }
        });
    }
}