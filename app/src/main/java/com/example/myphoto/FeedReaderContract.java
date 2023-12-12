// FeedReaderContract.java
package com.example.myphoto;

import android.provider.BaseColumns;

// 定义一个协定类，用于存放表名和列名的常量
public final class FeedReaderContract {

    // 构造方法私有化，防止实例化
    private FeedReaderContract() {}

    // 定义一个内部类，用于表示图像收藏表
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry"; // 表名
        public static final String COLUMN_NAME_IMAGE_PATH = "image_path"; // 图像路径列名
        public static final String COLUMN_NAME_IMAGE_NAME = "image_name"; // 图像名称列名
        public static final String COLUMN_NAME_IMAGE_DATE = "image_date"; // 图像日期列名
    }
}
