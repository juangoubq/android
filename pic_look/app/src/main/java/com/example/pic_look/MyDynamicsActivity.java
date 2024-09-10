package com.example.pic_look;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyDynamicsActivity extends AppCompatActivity {

    private ListView myDynamicsListView;
    private MyDynamicsAdapter myDynamicsAdapter;
    private static final String TAG = "MyDynamicsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dynamics);

        // 初始化ListView
        myDynamicsListView = findViewById(R.id.myDynamicsListView);
        myDynamicsAdapter = new MyDynamicsAdapter(this, new ArrayList<>());
        myDynamicsListView.setAdapter(myDynamicsAdapter);

        // 设置 item 之间的间距
        myDynamicsListView.setDivider(getResources().getDrawable(R.drawable.divider));
        myDynamicsListView.setDividerHeight(25); // 设置间距高度

        get();
    }

    private void get() {
        new Thread(() -> {
            // 用户ID
            long userId = 1832492258876854272L;
            // 构建URL
            String url = "https://api-store.openguet.cn/api/member/photo/share/myself?userId=" + userId;

            // 请求头
            Headers headers = new Headers.Builder()
                    .add("appId", "55bfc267a5a04e5baafaa64b423e551d")
                    .add("appSecret", "923766af4a5d2542046adb0a5db6d2a9c3f0c")
                    .add("Accept", "application/json, text/plain, */*")
                    .build();

            // 请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .get()
                    .build();

            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(callback);
        }).start();
    }

    // 回调
    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            // 请求失败处理
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String body = response.body().string();
            Log.d(TAG, "Response Body: " + body);

            Gson gson = new Gson();
            ApiResponse apiResponse = gson.fromJson(body, ApiResponse.class);

            // 确保code为200且有数据
            if (apiResponse != null && apiResponse.getCode() == 200 && apiResponse.getData() != null) {
                List<Record> records = apiResponse.getData().getRecords();
                runOnUiThread(() -> {
                    // 更新UI
                    updateUI(records);
                });
            }
        }
    };

    // 更新UI的方法
    private void updateUI(List<Record> items) {
        myDynamicsAdapter.setData(items);
        myDynamicsAdapter.notifyDataSetChanged();
    }

    // 自定义适配器
    public static class MyDynamicsAdapter extends ArrayAdapter<Record> {
        private List<Record> items;

        public MyDynamicsAdapter(Context context, List<Record> items) {
            super(context, R.layout.item_my_dynamics, items);
            this.items = items;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_my_dynamics, parent, false);
            }

            Record item = items.get(position);
            TextView titleTextView = convertView.findViewById(R.id.titleTextView);
            TextView contentTextView = convertView.findViewById(R.id.contentTextView);
            TextView timeTextView = convertView.findViewById(R.id.timeTextView);
            ImageView imageView = convertView.findViewById(R.id.imageView);

            titleTextView.setText(item.getTitle());
            contentTextView.setText(item.getContent());
            timeTextView.setText(item.getCreateTime());

            // 检查 imageUrlList 是否为空
            List<String> imageUrlList = item.getImageUrlList();
            if (imageUrlList != null && !imageUrlList.isEmpty()) {
                // 加载图片
                Picasso.get().load(imageUrlList.get(0)).into(imageView);
            } else {
                // 使用默认图片或不显示图片
                // 例如，使用默认图片
                imageView.setImageResource(R.drawable.default_image2);
            }


            return convertView;
        }

        public void setData(List<Record> newData) {
            this.items.clear();
            this.items.addAll(newData);
            notifyDataSetChanged();
        }
    }

}