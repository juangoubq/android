package com.example.pic_look;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SearchActivity extends AppCompatActivity {


    private TextView userNameTextView;
    private TextView introductionTextView;
    private EditText searchInput;
    private LinearLayout uc;
    private ImageButton followButton;
    private static final String TAG = "SearchActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 初始化组件
        searchInput = findViewById(R.id.search_input);
        userNameTextView = findViewById(R.id.user_name);
        introductionTextView = findViewById(R.id.introduction);
        uc = findViewById(R.id.user_card);
        followButton = findViewById(R.id.focusButton);
        // 设置搜索按钮点击监听
        findViewById(R.id.search_button).setOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        String query = searchInput.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
            return;
        }

        // 发起网络请求获取搜索结果
        searchRecords("heguizhang");
    }

    private void searchRecords(String keyword) {
        new Thread(() -> {
            // 构建URL
            String url = "https://api-store.openguet.cn/api/member/photo/user/getUserByName?username=" + keyword;

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
            runOnUiThread(() -> Toast.makeText(SearchActivity.this, "请求失败", Toast.LENGTH_SHORT).show());
        }

        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            if (!response.isSuccessful()) {
                runOnUiThread(() -> Toast.makeText(SearchActivity.this, "请求失败", Toast.LENGTH_SHORT).show());
                return;
            }
            String body = response.body().string();
            Log.d(TAG, "Response Body: " + body);

            Gson gson = new Gson();
            ApiResponse apiResponse = gson.fromJson(body, ApiResponse.class);

            // 确保code为200且有数据
            if (apiResponse != null && apiResponse.getCode() == 200 && apiResponse.getData() != null) {
                Data data = apiResponse.getData();
                runOnUiThread(() -> {
                    // 更新UI
                    updateUI(data);
                    uc.setVisibility(View.VISIBLE);
                });
            } else {
                runOnUiThread(() -> Toast.makeText(SearchActivity.this, "没有找到数据", Toast.LENGTH_SHORT).show());
            }
        }
    };

    // 更新UI的方法
    private void updateUI(Data item) {
        userNameTextView.setText("用户名：" + item.getUsername());
        introductionTextView.setText("个人介绍：" + item.getIntroduce());
        //关注和取消关注按钮
        followButton.setOnClickListener(v -> {
            if (item.isHasFocus()) {
                // 已经关注，点击后取消关注
                item.setHasFocus(false);
                followButton.setImageResource(R.drawable.ic_gz4);
                Toast.makeText(SearchActivity.this, "取消关注成功", Toast.LENGTH_SHORT).show();
            } else {
                // 未关注，点击后关注
                item.setHasFocus(true);
                followButton.setImageResource(R.drawable.ic_ygz2);
                Toast.makeText(SearchActivity.this, "已关注", Toast.LENGTH_SHORT).show();
            }
        });
    }
}