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

    private Context context;
    private List<String> imagePaths;
    private LayoutInflater layoutInflater;

    public ImageAdapter(Context context, List<String> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_image, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.item_image_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String imagePath = imagePaths.get(position);
        viewHolder.imageView.setImageURI(Uri.parse(imagePath));
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
