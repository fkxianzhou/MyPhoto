// MainActivity.java
package com.example.myphoto;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1; // 请求码
    private ListView listView; // 列表视图
    private ImageView imageView; // 图像视图
    private List<String> imagePaths; // 图像路径列表
    private ImageAdapter imageAdapter; // 图像适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view); // 获取列表视图
        imageView = findViewById(R.id.image_view); // 获取图像视图
        imagePaths = new ArrayList<>(); // 初始化图像路径列表
        imageAdapter = new ImageAdapter(this, imagePaths); // 初始化图像适配器
        listView.setAdapter(imageAdapter); // 设置列表视图的适配器

        // 设置列表视图的点击事件监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取点击的图像路径
                String imagePath = imagePaths.get(position);
                // 设置图像视图的图像源
                imageView.setImageURI(Uri.parse(imagePath));
            }
        });

        // 检查是否有读取外部存储的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 如果没有，请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            // 如果有，扫描并显示图像
            scanAndShowImages();
        }
    }

    // 处理权限请求的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 判断请求码是否匹配
        if (requestCode == REQUEST_CODE) {
            // 判断权限是否被授予
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果是，扫描并显示图像
                scanAndShowImages();
            } else {
                // 如果否，提示用户
                Toast.makeText(this, "你拒绝了权限请求，无法显示图像", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 扫描并显示图像
    private void scanAndShowImages() {
        // 获取内容解析器
        ContentResolver contentResolver = getContentResolver();
        // 获取媒体图像的Uri
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 查询媒体图像的数据
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        // 判断查询结果是否为空
        if (cursor != null) {
            // 遍历查询结果
            while (cursor.moveToNext()) {
                // 获取图像的路径
                String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                // 添加图像路径到列表
                imagePaths.add(imagePath);
            }
            // 关闭游标
            cursor.close();
            // 通知适配器数据发生变化
            imageAdapter.notifyDataSetChanged();
        }
    }
}
