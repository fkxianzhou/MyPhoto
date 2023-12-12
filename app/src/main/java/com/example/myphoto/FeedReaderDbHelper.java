// FeedReaderDbHelper.java
package com.example.myphoto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myphoto.FeedReaderContract.FeedEntry;

import java.text.SimpleDateFormat;
import java.util.Date;

// 定义一个SQLiteOpenHelper的子类，用于创建和更新数据库
public class FeedReaderDbHelper extends SQLiteOpenHelper {

    // 如果更改了数据库架构，必须增加数据库版本
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    // 创建表的SQL语句
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_IMAGE_PATH + " TEXT," +
                    FeedEntry.COLUMN_NAME_IMAGE_NAME + " TEXT," +
                    FeedEntry.COLUMN_NAME_IMAGE_DATE + " TEXT)";

    // 删除表的SQL语句
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    // 构造方法
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 创建数据库的回调方法
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES); // 执行创建表的SQL语句
    }

    // 更新数据库的回调方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 这个数据库只是一个缓存，所以更新策略是丢弃旧数据并重新创建
        db.execSQL(SQL_DELETE_ENTRIES); // 执行删除表的SQL语句
        onCreate(db); // 重新创建数据库
    }

    // 降级数据库的回调方法
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion); // 调用更新数据库的方法
    }

    // 将图像的路径和其他信息插入到数据库中的方法
    public void insertImage(String imagePath) {
        // 获取数据库的可写引用
        SQLiteDatabase db = this.getWritableDatabase();

        // 创建一个ContentValues对象，用于存放要插入的数据
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_IMAGE_PATH, imagePath); // 图像路径
        values.put(FeedEntry.COLUMN_NAME_IMAGE_NAME, getImageName(imagePath)); // 图像名称
        values.put(FeedEntry.COLUMN_NAME_IMAGE_DATE, getCurrentDate()); // 图像日期

        // 插入数据，返回新行的ID
        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
    }

    // 从数据库中查询图像的路径和其他信息的方法
    public Cursor queryImage() {
        // 获取数据库的只读引用
        SQLiteDatabase db = this.getReadableDatabase();

        // 定义要查询的列
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_IMAGE_PATH,
                FeedEntry.COLUMN_NAME_IMAGE_NAME,
                FeedEntry.COLUMN_NAME_IMAGE_DATE
        };

        // 定义排序规则
        String sortOrder = FeedEntry.COLUMN_NAME_IMAGE_DATE + " DESC";

        // 查询数据，返回一个Cursor对象
        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME, // 要查询的表名
                projection, // 要查询的列
                null, // 要查询的行
                null, // 要查询的行的参数
                null, // 分组方式
                null, // 分组过滤器
                sortOrder // 排序方式
        );

        return cursor;
    }

    // 根据图像路径获取图像名称的方法
    private String getImageName(String imagePath) {
        // 如果图像路径为空，返回空字符串
        if (imagePath == null || imagePath.isEmpty()) {
            return "";
        }
        // 如果图像路径包含分隔符，返回最后一个分隔符后的子字符串
        if (imagePath.contains("/")) {
            return imagePath.substring(imagePath.lastIndexOf("/") + 1);
        }
        // 否则，返回图像路径本身
        return imagePath;
    }

    // 获取当前日期的方法
    private String getCurrentDate() {
        // 定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前日期
        Date date = new Date();
        // 返回格式化后的日期字符串
        return sdf.format(date);
    }
}
