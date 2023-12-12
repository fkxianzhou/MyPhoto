// ImageAdapter.java
package com.example.myphoto;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context context; // 上下文
    private List<String> imagePaths; // 图像路径列表
    private LayoutInflater layoutInflater; // 布局填充器

    // 构造方法
    public ImageAdapter(Context context, List<String> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
        this.layoutInflater = LayoutInflater.from(context);
    }

    // 获取列表项的数量
    @Override
    public int getCount() {
        return imagePaths.size();
    }

    // 获取列表项的数据
    @Override
    public Object getItem(int position) {
        return imagePaths.get(position);
    }

    // 获取列表项的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 获取列表项的视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 定义一个视图持有者
        ViewHolder viewHolder;
        // 判断复用视图是否为空
        if (convertView == null) {
            // 如果是，创建新的视图
            convertView = layoutInflater.inflate(R.layout.item_image, parent, false);
            // 创建新的视图持有者
            viewHolder = new ViewHolder();
            // 获取视图中的组件
            viewHolder.imageView = convertView.findViewById(R.id.item_image_view);
            // 将视图持有者设置到视图中
            convertView.setTag(viewHolder);
        } else {
            // 如果否，获取视图中的视图持有者
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 获取列表项的数据
        String imagePath = imagePaths.get(position);
        // 设置图像视图的图像源
        viewHolder.imageView.setImageURI(Uri.parse(imagePath));
        // 返回视图
        return convertView;
    }

    // 定义一个视图持有者类
    static class ViewHolder {
        ImageView imageView; // 图像视图
    }
}
