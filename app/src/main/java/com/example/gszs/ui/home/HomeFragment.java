package com.example.gszs.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gszs.CameraActivity;
import com.example.gszs.GoodsDetailActivity;
import com.example.gszs.R;
import com.example.gszs.SearchResultActivity;
import com.example.gszs.SettingActivity;
import com.example.gszs.adapter.HomeRvAdapter;
import com.example.gszs.bean.GoodsInfo;
import com.example.gszs.databinding.FragmentHomeBinding;
import com.example.gszs.ui.Fragment.PagerFragment1;
import com.example.gszs.ui.Fragment.PagerFragment2;
import com.example.gszs.ui.Fragment.PagerFragment3;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout srl_simple; // ????????????????????????????????????
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initSrl();
        initRecyclerView();
        initSearchView();
        initScan();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //?????????????????????
    public void initSrl(){
        srl_simple = binding.srl;
        srl_simple.setOnRefreshListener(this);
        // ?????????????????????????????????????????????
        srl_simple.setColorSchemeResources(
                R.color.red, R.color.gszs, R.color.black, R.color.fonts);
    }

    //?????????recyclerview
    public void initRecyclerView(){
        List<GoodsInfo> list = GoodsInfo.getAllList();
        RecyclerView recyclerView = binding.recycleViewHome;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new HomeRvAdapter(list,R.layout.item_home_fragment));
    }
    //??????????????????
    public void initSearchView() {
        SearchView searchView = binding.toolbarHome.searchView;
        // ???????????????????????????????????????
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //            ????????????????????????????????????
            @Override
            public boolean onQueryTextSubmit(String query) {
                //??????????????????????????????????????????????????????
                //?????????????????????query????????????????????????????????????????????????
                //?????????????????????????????????Bundle??????????????????????????????????????????
                Intent intent = new Intent();
                intent.putExtra("query", query);
                intent.setClass(getContext(), SearchResultActivity.class);
                startActivity(intent);
                return false;
            }

            // ??????????????????????????????
            @Override
            public boolean onQueryTextChange(String newText) {
                //do nothing
                return true;
            }
        });
    }
    //?????????????????????
    public void initScan(){
        ImageView iv_scan = binding.toolbarHome.ivScan;
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(getContext(), CameraActivity.class));
            }
        });
    }

    // ?????????????????????????????????????????????????????????????????????????????????onRefresh??????
    @Override
    public void onRefresh() {
        netRequest();
        srl_simple.setRefreshing(false);
//        // ????????????????????????????????????
//        mHandler.postDelayed(mRefresh, 2000);
    }

//    private Handler mHandler = new Handler(); // ???????????????????????????
//    // ????????????????????????
//    private Runnable mRefresh = new Runnable() {
//        @Override
//        public void run() {
//            //????????????
//
//            // ???????????????????????????????????????
//            srl_simple.setRefreshing(false);
//        }
//    };
    private void netRequest(){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name","??????")
                .build();
        final Request request = new Request.Builder()
                .url("http://119.91.99.23/findGoodsByName")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("??????");
                        builder.setMessage("??????????????????");
                        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = Objects.requireNonNull(response.body()).string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"??????????????????\n"+formatString(res),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public static String formatString(String text){
        StringBuilder json = new StringBuilder();
        String indentString = "";
        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append(indentString + letter + "\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;

                default:
                    json.append(letter);
                    break;
            }
        }
        return json.toString();
    }
}