package com.example.gszs;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gszs.ui.dashboard.DashboardFragment;
import com.example.gszs.ui.fifth.fifth;
import com.example.gszs.ui.fourth.fourth;
import com.example.gszs.ui.home.HomeFragment;
import com.example.gszs.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.gszs.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private HomeFragment fragment1;
    private DashboardFragment fragment2;
    private NotificationsFragment fragment3;
    private fourth fragment4;
    private fifth fragment5;
    private Fragment[] fragments;
    private int lastFragment;//用于记录上个选择的Fragment

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //BottomNavigationView mBottomNavigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupWithNavController(binding.navView, navController);
        initFragment();

    }
    private void initFragment()
    {

        fragment1 = new HomeFragment();
        fragment2 = new DashboardFragment();
        fragment3 = new NotificationsFragment();
        fragment4 = new fourth();
        fragment5 = new fifth();
        fragments = new Fragment[]{fragment1,fragment2,fragment3,fragment4,fragment5};
        lastFragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.lin_lay_fragment,fragment1).show(fragment1).commit();
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }
    //判断选择的菜单
    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                {
                    if(lastFragment!=0)
                    {
                        switchFragment(lastFragment,0);
                        lastFragment=0;
                    }
                    return true;
                }
                case R.id.navigation_dashboard:
                {
                    if(lastFragment!=1)
                    {
                        switchFragment(lastFragment,1);
                        lastFragment=1;
                    }
                    return true;
                }
                case R.id.navigation_notifications:
                {
                    if(lastFragment!=2)
                    {
                        switchFragment(lastFragment,2);
                        lastFragment=2;
                    }
                    return true;
                }
                case R.id.navigation_fourth:
                {
                    if(lastFragment!=3)
                    {
                        switchFragment(lastFragment,3);
                        lastFragment=3;
                    }
                    return true;
                }
                case R.id.navigation_fifth:
                {
                    if(lastFragment!=4)
                    {
                        switchFragment(lastFragment,4);
                        lastFragment=4;
                    }
                    return true;
                }
            }
            return false;
        }
    };
    //切换Fragment
    private void switchFragment(int lastFragment,int index)
    {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastFragment]);//隐藏上个Fragment
        if(fragments[index].isAdded()==false)
        {
            transaction.add(R.id.lin_lay_fragment,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }


    // just for test
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getApplicationContext(), "test_destroy", Toast.LENGTH_SHORT).show();
    }
    // 按返回键不销毁当前Activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}