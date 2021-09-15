package com.example.gszs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gszs.bean.Recognition;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CameraResultActivity extends AppCompatActivity {
    ImageView Iv_bitmap;
    ImageView Iv_bitmap2;
    OkHttpClient client = new OkHttpClient();
    String name_temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);
        Bundle bundle = getIntent().getExtras();
        int flag = bundle.getInt("flag");
        String path = bundle.getString("path");
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Iv_bitmap = (ImageView)findViewById(R.id.photo_camera);
        Iv_bitmap2 = (ImageView)findViewById(R.id.photo_camera_2);
        if(flag == 1){//根据flag判断图片来源，然后根据不同来源对图片进行旋转90度，因为后置默认横向拍照
            Iv_bitmap.setRotation(90);
            Iv_bitmap.setImageBitmap(bitmap);
            Iv_bitmap2.setImageBitmap(bitmap);
        }else if (flag == 2){
            Iv_bitmap.setImageBitmap(bitmap);
            Iv_bitmap2.setImageBitmap(bitmap);
        }
        initTextView();
        requestPhoto(path);
        initBack();

    }

    public void requestPhoto(String path){
        File file = new File(path);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",file.getName(),RequestBody.create(MediaType.parse("application/octet-stream"),file))
                .build();
        final Request request = new Request.Builder()
                .url("http://119.91.99.23/recognition")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                CameraResultActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CameraResultActivity.this,"failed"+":"+file.getName(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = Objects.requireNonNull(response.body()).string();
                CameraResultActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressBar pBar = (ProgressBar)findViewById(R.id.pBar);
                        Recognition recognition = new Gson().fromJson(res,Recognition.class);
                        Toast.makeText(CameraResultActivity.this,res,Toast.LENGTH_SHORT).show();
                        TextView result_name = (TextView)findViewById(R.id.result_name);
                        result_name.setText(recognition.getName());
                        name_temp = recognition.getName();
                        pBar.setVisibility(View.INVISIBLE);
                        TextView tv = (TextView)findViewById(R.id.more);
                        tv.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
    public void initWebView(String name){
        if(name.equals("非果蔬食材")) {
            name="黄平";
            Toast.makeText(CameraResultActivity.this,"请确保上传果蔬照片",Toast.LENGTH_SHORT).show();
        }
        //获得控件
        WebView webView = (WebView) findViewById(R.id.webView);
        //访问网页
        webView.loadUrl("https://baike.baidu.com/item/"+name);
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });
    }
    public void initBack(){
        ImageView back = (ImageView)findViewById(R.id.back_camera2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //初始化超链接
    public void initTextView(){
        TextView tv = (TextView)findViewById(R.id.more);
        final SpannableStringBuilder style = new SpannableStringBuilder();
        //设置文字
        style.append("了解更多?点击跳转百度百科");
        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                CardView card_camara = (CardView)findViewById(R.id.card_camera);
                card_camara.setVisibility(View.VISIBLE);
                WebView webView = (WebView)findViewById(R.id.webView);
                webView.setVisibility(View.VISIBLE);
                initWebView(name_temp);
                ImageView cancel = (ImageView)findViewById(R.id.cancel_WebView);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card_camara.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };
        style.setSpan(clickableSpan, 5, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(style);
        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#ADE6B2"));
        style.setSpan(foregroundColorSpan, 5, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //配置给TextView
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(style);
    }
}