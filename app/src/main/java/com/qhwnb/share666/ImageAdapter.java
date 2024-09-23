package com.qhwnb.share666;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int[] mImageIds;

    // 构造函数，初始化上下文和图片资源数组
    public ImageAdapter(Context context, int[] imageIds) {
        mContext = context;
        mImageIds = imageIds;
    }

    // 返回图片的总数
    @Override
    public int getCount() {
        return mImageIds.length;
    }

    // 根据位置返回图片资源 ID
    @Override
    public Object getItem(int position) {
        return mImageIds[position];
    }

    // 返回图片的位置 ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 返回每个 GridLayout 子项的视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        // 如果 convertView 为 null，创建新的 ImageView
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridLayout.LayoutParams());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            // 如果 convertView 不为 null，重用它
            imageView = (ImageView) convertView;
        }

        // 设置 ImageView 的图片资源
        imageView.setImageResource(mImageIds[position]);

        // 确保 ImageView 不响应点击事件
        imageView.setClickable(false);
        imageView.setFocusable(false);

        return imageView;
    }
}
