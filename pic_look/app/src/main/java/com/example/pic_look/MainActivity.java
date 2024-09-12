package com.example.pic_look;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;
    private EditText searchEditText;
    private Button searchButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化底部导航栏
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 初始化 Fragment 容器
        fragmentContainer = findViewById(R.id.fragment_container);

        // 设置默认显示的 Fragment
        showFragment(new HomeFragment());

        // 设置底部导航栏的点击事件
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home) {
                    showFragment(new HomeFragment());
                    return true;
                } else if (id == R.id.messages) {
                    showFragment(new MessagesFragment());
                    return true;
                } else if (id == R.id.me) {
                    showFragment(new MeFragment());
                    return true;
                } else {
                    return false;
                }
            }
        });

        // 初始化控件
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);

        // 检查是否为空
        if (searchEditText == null || searchButton == null) {
            Toast.makeText(this, "找不到搜索框或搜索按钮", Toast.LENGTH_SHORT).show();
        }

        // 设置搜索按钮点击事件
        if (searchButton != null) {
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String keyword = searchEditText.getText().toString().trim();
                    System.out.println("搜索关键词：" + keyword);
                    if (!keyword.isEmpty()) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra("keyword", keyword);
                        startActivity(intent);
                    } else {
                        // 提示用户输入关键词
                        searchEditText.setError("请输入关键词");
                    }
                }
            });
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
