package com.example.pic_look;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class MeActivity extends AppCompatActivity {

    private LinearLayout profileLayout;
    private ImageView profileImage;
    private TextView profileUsername;
    private LinearLayout releaseLayout;
    private LinearLayout aboutLayout;
    private LinearLayout downloadsLayout;
    private LinearLayout collectionsLayout;
    private LinearLayout historyLayout;
    private LinearLayout settingsLayout;
    private ListView myDynamicsListView;
    private MyDynamicsActivity.MyDynamicsAdapter myDynamicsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_me);

        // 初始化组件
        profileLayout = findViewById(R.id.profile_layout);
        profileImage = findViewById(R.id.profile_image);
        profileUsername = findViewById(R.id.profile_username);
        releaseLayout = findViewById(R.id.release);
        aboutLayout = findViewById(R.id.about_layout);
        downloadsLayout = findViewById(R.id.downloads_layout);
        collectionsLayout = findViewById(R.id.collections_layout);
        historyLayout = findViewById(R.id.history_layout);
        settingsLayout = findViewById(R.id.settings_layout);


        // 设置点击事件
        setupProfileClicks();
        setupReleaseClicks();
        setupAboutClicks();
        setupDownloadsClicks();
        setupCollectionsClicks();
        setupHistoryClicks();
        setupSettingsClicks();

        get();
        fetchFollowedActivities();
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

            Gson gson = new Gson();
            ApiResponse apiResponse = gson.fromJson(body, ApiResponse.class);

            // 确保code为200且有数据
            if (apiResponse != null && apiResponse.getCode() == 200 && apiResponse.getData() != null) {
                List<Record> records = apiResponse.getData().getRecords();
                runOnUiThread(() -> {
                    TextView releaseTextView = findViewById(R.id.release_num);
                    releaseTextView.setText(String.valueOf(records.size()));
                });
            }
        }
    };

    private void fetchFollowedActivities() {
        new Thread(() -> {
            // 用户ID
            long userId = 1832492258876854272L;
            // 构建URL
            String url = "https://api-store.openguet.cn/api/member/photo/focus?userId=" + userId;

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
            client.newCall(request).enqueue(callback2);
        }).start();
    }

    // 回调
    private final Callback callback2 = new Callback() {
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

            Gson gson = new Gson();
            ApiResponse apiResponse = gson.fromJson(body, ApiResponse.class);

            // 确保code为200且有数据
            if (apiResponse != null && apiResponse.getCode() == 200 && apiResponse.getData() != null) {
                List<Record> records = apiResponse.getData().getRecords();
                runOnUiThread(() -> {
                    TextView focusTextView = findViewById(R.id.focus_num);
                    focusTextView.setText(String.valueOf(records.size()));
                });
            }
        }
    };

    private void setupProfileClicks() {
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到登录页面
                // startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    private void setupReleaseClicks() {
        releaseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到动态页面
                 startActivity(new Intent(MeActivity.this, MyDynamicsActivity.class));
            }
        });
    }

    private void setupAboutClicks() {
        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到关注页面
                startActivity(new Intent(MeActivity.this, FocusActivity.class));
            }
        });
    }

    private void setupDownloadsClicks() {
        downloadsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到草稿页面
                startActivity(new Intent(MeActivity.this, DraftsActivity.class));
            }
        });
    }

    private void setupCollectionsClicks() {
        collectionsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到收藏页面
                 startActivity(new Intent(MeActivity.this, CollectionActivity.class));
            }
        });
    }

    private void setupHistoryClicks() {
        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到点赞页面
                 startActivity(new Intent(MeActivity.this, LikesActivity.class));
            }
        });
    }

    private void setupSettingsClicks() {
        settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到设置页面
                 startActivity(new Intent(MeActivity.this, SettingsActivity.class));
            }
        });
    }
}
