package com.example.pic_look;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;

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
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
