package com.example.gszs;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gszs.engine.GlideEngine;
import com.example.gszs.widget.Camera2View;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;


public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CameraActivity";
    private Camera2View camera2_view; // 声明一个二代相机视图对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        camera2_view = findViewById(R.id.camera2_view);
        // 设置二代相机视图的摄像头类型
        camera2_view.open(CameraCharacteristics.LENS_FACING_FRONT);
        findViewById(R.id.btn_shutter).setOnClickListener(this);
        findViewById(R.id.back_camera).setOnClickListener(this);
        findViewById(R.id.choose_photo).setOnClickListener(this);
        hideBar();
        myRequestPermission();

    }


//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(); // 创建一个新意图
//        Bundle bundle = new Bundle(); // 创建一个新包裹
//        String photo_path = camera2_view.getPhotoPath(); // 获取照片的保存路径
//        bundle.putInt("type", mTakeType);
//        bundle.putString("path", photo_path);
//        }
//        intent.putExtras(bundle); // 往意图中存入包裹
//        setResult(Activity.RESULT_OK, intent); // 携带意图返回前一个页面
//        finish(); // 关闭当前页面
//    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_shutter) { // 点击了单拍按钮
            // 命令二代相机视图执行单拍操作
            ProgressDialog dialog = ProgressDialog.show(this, "提示", "正在识别中");
            camera2_view.takePicture();
            // 拍照需要完成对焦、图像捕获、图片保存等一系列动作，因而要留足时间给系统处理
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("path", camera2_view.getPhotoPath());
                    bundle.putInt("flag",1);
                    intent.putExtras(bundle);
                    intent.setClass(CameraActivity.this,CameraResultActivity.class);
                    startActivity(intent);
                    dialog.cancel();
                }
            }, 1500);
        } else if(v.getId() == R.id.back_camera){
            //返回
            //Toast.makeText(CameraActivity.this, "what？", Toast.LENGTH_SHORT).show();
            finish();
        } else if(v.getId() == R.id.choose_photo){
            //从相册选择图片
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofAll())
                    .selectionMode(PictureConfig.SINGLE)
                    .imageEngine(GlideEngine.createGlideEngine())
                    .forResult(PictureConfig.CHOOSE_REQUEST);

        }

    }
    private void myRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            finish();
        } else if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            finish();
        }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            finish();
        }
        //Toast.makeText(this,"权限已申请",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 结果回调
                    ProgressDialog dialog = ProgressDialog.show(this, "提示", "正在识别中");
                    List<LocalMedia> result = PictureSelector.obtainMultipleResult(data);
                    //给足处理时间
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.cancel();
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("path", result.get(0).getRealPath());
                            bundle.putInt("flag",2);
                            intent.putExtras(bundle);
                            intent.setClass(CameraActivity.this,CameraResultActivity.class);
                            startActivity(intent);
                        }
                    }, 1500);
                    break;
                default:
                    break;
            }
        }
    }
    public void  hideBar(){
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
