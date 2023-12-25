// ImageCollectionActivity.java
package com.example.myphoto;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ImageCollectionActivity extends AppCompatActivity {

    private ListView collectionListView;
    private List<String> collectionImagePaths;
    private ImageAdapter collectionImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_collection);

        // 获取 ActionBar
        ActionBar actionBar = getSupportActionBar();
        // 如果 ActionBar 不为 null，则设置返回按钮
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        collectionListView = findViewById(R.id.collection_list_view);
        collectionImagePaths = new ImageCollector(this).getCollection();
        collectionImageAdapter = new ImageAdapter(this, collectionImagePaths);
        collectionListView.setAdapter(collectionImageAdapter);
    }

    // 处理返回按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 点击返回按钮，关闭当前活动，返回到上一个活动（MainActivity）
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
