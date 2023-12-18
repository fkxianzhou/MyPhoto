// ImageCollector.java
package com.example.myphoto;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class ImageCollector {

    private FeedReaderDbHelper dbHelper; // 数据库帮助器

    // 构造方法
    public ImageCollector(Context context) {
        dbHelper = new FeedReaderDbHelper(context);
    }

    // 添加图片到收藏
    public void addToCollection(String imagePath) {
        dbHelper.insertImage(imagePath);
    }

    // 获取收藏的图片路径列表
    public List<String> getCollection() {
        List<String> collection = new ArrayList<>();
        Cursor cursor = dbHelper.queryImage();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int columnIndex = cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE_PATH);
                if (columnIndex != -1) {
                    String imagePath = cursor.getString(columnIndex);
                    collection.add(imagePath);
                }
            }
            cursor.close();
        }

        return collection;
    }
}
