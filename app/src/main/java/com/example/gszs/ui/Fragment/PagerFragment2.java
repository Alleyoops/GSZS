package com.example.gszs.ui.Fragment;

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

public class PagerFragment2 extends Fragment {
    private RecyclerView rvContacts;
    private WaveSideBar sideBar;
    private ArrayList<GoodsInfo> contacts = new ArrayList<>();
    private FragmentPagerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPagerBinding.inflate(inflater, container, false);
        initView();
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
        sideBar.setOnSelectIndexItemListener(index -> {
            for (int i=0; i<contacts.size(); i++) {
                if (contacts.get(i).getIndex().equals(index)) {
                    ((LinearLayoutManager) rvContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                    return;
                }
            }
        });
    }

    private void initData() {
        contacts.addAll(GoodsInfo.getDefaultList(2));
    }
    public void changeRv(ArrayList<GoodsInfo> temp){
        rvContacts = binding.recycleView;
        rvContacts.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvContacts.setAdapter(new ContactsAdapter(temp, R.layout.item_pager_fragment));
        System.out.println(temp);
    }
}