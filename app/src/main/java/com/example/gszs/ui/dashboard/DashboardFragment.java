package com.example.gszs.ui.dashboard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gszs.CameraResultActivity;
import com.example.gszs.R;
import com.example.gszs.SettingActivity;
import com.example.gszs.adapter.BlogRvAdapter;
import com.example.gszs.adapter.ResultRvAdapter4;
import com.example.gszs.bean.Dynamics;
import com.example.gszs.bean.Recognition;
import com.example.gszs.databinding.FragmentDashboardBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DashboardFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout srl_simple; // 声明一个下拉刷新布局对象
    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initSrl();
        request();

        return root;
    }
    //初始化下拉刷新
    public void initSrl(){
        srl_simple = binding.srl;
        srl_simple.setOnRefreshListener(this);
        // 设置下拉刷新布局的进度圆圈颜色
        srl_simple.setColorSchemeResources(
                R.color.red, R.color.gszs, R.color.black, R.color.fonts);
    }
    // 一旦在下拉刷新布局内部往下拉动页面，就触发下拉监听器的onRefresh方法
    @Override
    public void onRefresh() {
        request();
        srl_simple.setRefreshing(false);
//        // 延迟若干秒后启动刷新任务
//        mHandler.postDelayed(mRefresh, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void request(){
        ProgressDialog dialog = ProgressDialog.show(getContext(), "提示", "正在刷新");
        OkHttpClient client = new OkHttpClient();
        FormBody requestBody = new FormBody.Builder()
                .build();
        final Request request = new Request.Builder()
                .url("http://119.91.99.23/findAllDynamic")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("提示");
                        builder.setMessage("网络请求超时");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                        dialog.cancel();
                        java.lang.reflect.Type type = new TypeToken<ArrayList<Dynamics>>(){}.getType();
                        ArrayList<Dynamics> dynamics = new Gson().fromJson(res,type);
                        BlogRvAdapter adapter = new BlogRvAdapter(R.layout.item_blog_fragment,dynamics);
                        RecyclerView recyclerView = binding.rvBlog;
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }
}