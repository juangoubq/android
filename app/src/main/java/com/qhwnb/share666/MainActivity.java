package com.qhwnb.share666;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageAdapter imageAdapter; // 声明适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取 GridLayout
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // 示例图片资源数组
        int[] imageResources = {
                R.mipmap.s3, // 替换为实际图片资源
                R.mipmap.s4,
                R.mipmap.s7,
                R.mipmap.s1 // 添加第四张图片以便展示4宫格效果
                // 可以继续添加更多图片资源
        };

        // 创建适配器
        imageAdapter = new ImageAdapter(this, imageResources);

        // 设置 GridLayout 的列数为 2（即每行显示 2 张图片）
        gridLayout.setColumnCount(2);

        // 动态添加 ImageView 到 GridLayout
        updateViewGroup(imageResources);
    }

    // 更新 GridLayout 的方法
    public void updateViewGroup(int[] imageResources) {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        gridLayout.removeAllViews(); // 移除所有现有视图

        // 获取列数为 2（即 4 宫格，两张图片一行）
        int columnCount = gridLayout.getColumnCount();

        // 使用适配器填充 GridLayout
        for (int i = 0; i < imageResources.length; i++) {
            ImageView imageView = new ImageView(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            // 根据 2 列布局，调整每个图片的宽高
            params.width = 0; // 设置为0意味着宽度将按权重平分
            params.height = 0; // 高度也按权重平分
            params.rowSpec = GridLayout.spec(i / columnCount, 1f); // 权重为 1
            params.columnSpec = GridLayout.spec(i % columnCount, 1f); // 权重为 1
            params.setMargins(5, 5, 5, 5); // 设置图片间距

            imageView.setLayoutParams(params);
            imageView.setImageResource(imageResources[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            gridLayout.addView(imageView);
        }
    }
}
