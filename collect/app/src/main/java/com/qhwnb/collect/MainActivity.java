package com.qhwnb.collect;

import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final ApiService apiService = new ApiService();
    private final Gson gson = new Gson();
    private EditText etUsername;
    private EditText etAvatar;
    private EditText etIntroduce;
    private EditText etSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 找到按钮并设置点击监听器
        // 初始化 EditText 和 Button
        etUsername = findViewById(R.id.et_username);
        etAvatar = findViewById(R.id.et_avatar);
        etIntroduce = findViewById(R.id.et_introduce);
        etSex = findViewById(R.id.et_sex);
        Button btnSave = findViewById(R.id.btn_save);

        // 设置点击监听器
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
    }


    private void post(){
        new Thread(() -> {
            // 获取用户输入的内容
            String username = etUsername.getText().toString().trim();
            String avatar = etAvatar.getText().toString().trim();
            String introduce = etIntroduce.getText().toString().trim();
            String sex = etSex.getText().toString().trim();

            // 检查是否有必填字段为空
            if (username.isEmpty() || avatar.isEmpty() || introduce.isEmpty() || sex.isEmpty()) {
                // 如果有为空的字段，显示提示信息
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "参数不能为空", Toast.LENGTH_SHORT).show());
                return;
            }

            // url路径
            String url = "https://api-store.openguet.cn/api/member/photo/user/update";

            // 请求头
            Headers headers = new Headers.Builder()
                    .add("appId", "d2806f63d44e44bf9268f0c8d925068b")
                    .add("appSecret", "138086fd68218d6e94753bbb609d8745c1e9a")
                    .add("Accept", "application/json, text/plain, */*")
                    .build();

            // 请求体
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("avatar", avatar);
            bodyMap.put("id", "1833158371432337408");  // 这个 id 可能需要从实际输入或其他来源获取
            bodyMap.put("introduce", introduce);
            bodyMap.put("sex", sex);
            bodyMap.put("username", username);
            String body = gson.toJson(bodyMap);

            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

            // 请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .post(RequestBody.create(MEDIA_TYPE_JSON, body))
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                // 发起请求，传入 callback 进行回调
                client.newCall(request).enqueue(callback);
            } catch (NetworkOnMainThreadException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * 回调
     */
    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            //TODO 请求失败处理
            e.printStackTrace();
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            //TODO 请求成功处理
            Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
            // 获取响应体的json串
            String body = response.body().string();
            Log.d("info", body);
            // 解析json串到自己封装的状态
            ResponseBody<Object> dataResponseBody = gson.fromJson(body,jsonType);
            Log.d("info", dataResponseBody.toString());
        }
    };

    /**
     * http响应体的封装协议
     * @param <T> 泛型
     */
    public static class ResponseBody <T> {

        /**
         * 业务响应码
         */
        private int code;
        /**
         * 响应提示信息
         */
        private String msg;
        /**
         * 响应数据
         */
        private T data;

        public ResponseBody(){}

        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
        public T getData() {
            return data;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseBody{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}
