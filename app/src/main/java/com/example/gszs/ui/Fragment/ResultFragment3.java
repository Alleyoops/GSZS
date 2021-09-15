package com.example.gszs.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gszs.R;
import com.example.gszs.adapter.ResultRvAdapter1;
import com.example.gszs.adapter.ResultRvAdapter3;
import com.example.gszs.databinding.FragmentResultBinding;
import com.example.gszs.ui.notifications.NotificationsFragment;

public class ResultFragment3 extends NotificationsFragment {

    private FragmentResultBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(inflater, container, false);

        initView();
        return binding.getRoot();
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }

    //暂时使用假数据来观察效果
    private void initData() {

    }
    private void initView() {
        RecyclerView recyclerView = binding.recycleViewResult;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new ResultRvAdapter3(R.layout.item_result_fragment_desc));
    }

}