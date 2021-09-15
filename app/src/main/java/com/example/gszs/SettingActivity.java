package com.example.gszs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView(){
        findViewById(R.id.exit_login).setOnClickListener(this);
        findViewById(R.id.api_test1).setOnClickListener(this);
        findViewById(R.id.api_test2).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.exit_login){
            //退出登录，清空sp，跳转到login页面
            SharedPreferences sp =getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();editor.commit();
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);//禁止返回上一页
            intent.setClass(SettingActivity.this,LoginActivity.class);
            startActivity(intent);
        }else if (v.getId()==R.id.api_test1){
            //接口测试，各种网络请求
            api_test("findAllDynamic");
        }else if (v.getId()==R.id.api_test2){
            //接口测试，各种网络请求
            api_test("topicAll");
        }
    }
    private void api_test(String s){
        ProgressDialog dialog = ProgressDialog.show(this, "提示", "网络加载中");
        OkHttpClient client = new OkHttpClient();
        FormBody requestBody = new FormBody.Builder()
                .build();
        final Request request = new Request.Builder()
                .url("http://119.91.99.23/"+s)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                SettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
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
                SettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        TextView tv_test = (TextView)findViewById(R.id.tv_test);
                        tv_test.setText(formatString(res));
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