package com.example.pic_look;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 设置点击事件
        setupClickListeners();
    }

    private void setupClickListeners() {
        findViewById(R.id.editProfileLinearLayout).setOnClickListener(v -> navigateToEditProfile());
        findViewById(R.id.aboutUsLinearLayout).setOnClickListener(v -> navigateToAboutUs());
        findViewById(R.id.logoutLinearLayout).setOnClickListener(v -> handleLogout());
    }

    private void navigateToEditProfile() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void navigateToAboutUs() {
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }

    private void handleLogout() {
        // 实现退出登录的逻辑
        // 可以在这里清除登录状态，跳转到登录页面等
        // 示例：
        // 清除登录状态
        finish(); // 关闭当前设置页面
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);
    }
}
