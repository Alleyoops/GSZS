package com.example.gszs.ui.fifth;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gszs.R;
import com.example.gszs.SettingActivity;
import com.example.gszs.databinding.FifthFragmentBinding;
import com.example.gszs.databinding.FourthFragmentBinding;
import com.example.gszs.ui.fourth.FourthViewModel;

public class fifth extends Fragment implements View.OnClickListener {
    private FifthFragmentBinding binding;
    private FifthViewModel mViewModel;
    public static fifth newInstance() {
        return new fifth();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(FifthViewModel.class);
        binding = FifthFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initView();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FifthViewModel.class);
        // TODO: Use the ViewModel
    }
    private void initView(){
        TextView tv1 = binding.nameFif;
        TextView tv2 = binding.descFif;
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        String psw = bundle.getString("psw");
        tv1.setText(name);
        tv2.setText(psw);
        //一些点击事件
        binding.show.setOnClickListener(this);
        binding.favorite.setOnClickListener(this);
        binding.shoppingCar.setOnClickListener(this);
        binding.setting.setOnClickListener(this);
        binding.phoneUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.setting){
            startActivity(new Intent().setClass(getContext(), SettingActivity.class));
        }
    }
}