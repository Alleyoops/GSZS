package com.example.gszs.ui.fourth;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.gszs.MainActivity;
import com.example.gszs.R;
import com.example.gszs.adapter.VerticalRvAdapter;
import com.example.gszs.bean.GoodsInfo;
import com.example.gszs.databinding.FourthFragmentBinding;
import java.util.List;

public class fourth extends Fragment{
    private FourthFragmentBinding binding;
    private FourthViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(FourthViewModel.class);
        binding = FourthFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initView();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(FourthViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initView(){
        initScrollView(0);//初始化第一个pos的数据
        List<GoodsInfo> list = GoodsInfo.getAllList();
        RecyclerView recyclerView = binding.RVFour;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        VerticalRvAdapter adapter = new VerticalRvAdapter(list,R.layout.item_vertical_fragment);
        recyclerView.setAdapter(adapter);
        //接口的调用，获取传递的pos
        adapter.setOnClickMyTextView(new VerticalRvAdapter.onClickMyTextView() {
            @Override
            public void myTextViewClick(int pos) {
                initScrollView(pos);
            }
        });
    }
    //接受点击item传来的GoodsInfo对象实例
    public void initScrollView(int pos){
        ImageView photo = binding.photoFourth;
        TextView name = binding.nameFourth;
        TextView desc = binding.descFourth;
        TextView price = binding.priceFourth;
        GoodsInfo good = GoodsInfo.getAllList().get(pos);
        photo.setImageResource(good.getPhoto());
        name.setText(good.getName());
        desc.setText(good.getDescription());
        price.setText("¥"+good.getPrice());
    }

}