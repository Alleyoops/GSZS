package com.example.gszs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.ColorUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.SurfaceControl;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gszs.bean.Recognition;
import com.example.gszs.engine.GlideEngine;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkLogin();
        hideBar();
        initView();
    }
    public void initView(){
        findViewById(R.id.tv_login).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.choose_head).setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
        initTextView();

    }
    public void initTextView(){
        TextView tv = (TextView)findViewById(R.id.tv);
        final SpannableStringBuilder style = new SpannableStringBuilder();
        //设置文字
        style.append("请先注册再登录，点击注册");
        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                CardView card = (CardView)findViewById(R.id.card);
                card.setVisibility(View.VISIBLE);
            }
        };
        style.setSpan(clickableSpan, 8, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(style);
        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#ADE6B2"));
        style.setSpan(foregroundColorSpan, 8, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //配置给TextView
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(style);
    }
    public void  hideBar(){
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //状态栏颜色深色
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
    //检查是否已经登录，可以直接登录
    private void  checkLogin(){
        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
        String name = sp.getString("name","");
        String psw = sp.getString("psw","");
        if(!name.equals("")&!psw.equals("")){
            //直接跳转MainActivity
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("psw",psw);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);//禁止返回上一页
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel) {
            CardView card = (CardView)findViewById(R.id.card);
            card.setVisibility(View.INVISIBLE);
        }else if (v.getId() == R.id.choose_head){
            //从相册选择图片
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofAll())
                    .selectionMode(PictureConfig.SINGLE)
                    .imageEngine(GlideEngine.createGlideEngine())
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }else if (v.getId() == R.id.tv_register){
            //注册
            register();
        }else  if(v.getId() == R.id.tv_login){
            //登录
            login();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 结果回调
                    List<LocalMedia> result = PictureSelector.obtainMultipleResult(data);
                    path = result.get(0).getRealPath();
                    ImageView iv = (ImageView)findViewById(R.id.choose_head);
                    iv.setImageBitmap(BitmapFactory.decodeFile(result.get(0).getRealPath()));
                    break;
                default:
                    break;
            }
        }
    }
    public String path;
    public void register(){
        EditText et1 = (EditText)findViewById(R.id.et1);
        EditText et2 = (EditText)findViewById(R.id.et2);
        EditText et3 = (EditText)findViewById(R.id.et3);
        if(et1.getText().toString().equals("")||et2.getText().toString().equals("")||et3.getText().toString().equals("")||path==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("请将补充信息补充完整");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else {
            //注册
            register_request(et1.getText().toString(),et2.getText().toString(),et3.getText().toString(),path);
        }
    }
    public void register_request(String et1,String et2,String et3,String path){
        ProgressDialog dialog = ProgressDialog.show(this, "提示", "加载中");
        OkHttpClient client = new OkHttpClient();
        File file = new File(path);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username",et1)
                .addFormDataPart("password",et2)
                .addFormDataPart("nickname",et3)
                .addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"),file))
                .build();
        final Request request = new Request.Builder()
                .url("http://119.91.99.23/register")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("网络请求超时，请更换账号密码或者多次尝试");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CardView card = (CardView)findViewById(R.id.card);
                                card.setVisibility(View.INVISIBLE);
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
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        if(res.equals("success")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("提示");
                            builder.setMessage("注册成功");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CardView card = (CardView)findViewById(R.id.card);
                                    card.setVisibility(View.INVISIBLE);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("提示");
                            builder.setMessage("注册失败，可能是服务器原因，请稍后再试");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                });
            }
        });
    }
    public void login(){
        EditText num = (EditText)findViewById(R.id.et_phone_num);
        EditText psw = (EditText)findViewById(R.id.et_password);
        if(num.getText().toString().equals("")||psw.getText().toString().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("请将补充信息补充完整");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else {
            //登录
            login_request(num.getText().toString(),psw.getText().toString());
        }
    }
    public void login_request(String num,String psw){
        ProgressDialog dialog = ProgressDialog.show(this, "提示", "登录中");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username",num)
                .addFormDataPart("password",psw)
                .build();
        final Request request = new Request.Builder()
                .url("http://119.91.99.23/login")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        if(res.equals("")){
                            //把登录信息保存到本地，下次直接登录,login指的是sp的xml文件名
                            SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();//从而可以编辑sp的xml文件
                            editor.putString("name",num);
                            editor.putString("psw",psw);
                            editor.commit();
                            //携带登录信息跳转MainActivity
                            Bundle bundle = new Bundle();
                            bundle.putString("name",num);
                            bundle.putString("psw",psw);
                            Intent intent = new Intent();
                            intent.putExtras(bundle);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);//禁止返回上一页
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("提示");
                            builder.setMessage("密码错误，登录失败");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                });
            }
        });
    }
}