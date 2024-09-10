package com.qhwnb.collect;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ApiService {

    // 定义 JSON 格式
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    /**
     * 更新用户信息
     * @param appId 应用ID
     * @param appSecret 应用密钥
     * @param username 用户名
     * @param avatar 头像URL
     * @param introduce 个人介绍
     * @param sex 性别（0: 男, 1: 女）
     * @param id 用户ID
     * @param callback 回调函数，用于处理响应
     */
    public void updateUserInfo(String appId, String appSecret, String username, String avatar, String introduce, int sex, int id, Callback callback) {
        // 构建请求体
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("avatar", "http://1.95.42.69:40027/nuJpw1.jpg");
        bodyMap.put("id", "1833102751324835840");
        bodyMap.put("introduce", "sadafja");
        bodyMap.put("sex", "1");
        bodyMap.put("username", "qhw");
        // 将Map转换为字符串类型加入请求体中

        // 将 Map 转换为 JSON 格式的字符串
        String body = gson.toJson(bodyMap);

        // 构建请求头
        Headers headers = new Headers.Builder()
                .add("Accept", "application/json, text/plain, */*")
                .add("appId", appId)
                .add("appSecret", appSecret)
                .add("Content-Type", "application/json")
                .build();


        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

        //请求组合创建
        Request request = new Request.Builder()
                .url("https://api-store.openguet.cn/api/member/photo/user/update")
                // 将请求头加至请求中
                .headers(headers)
                .post(RequestBody.create(MEDIA_TYPE_JSON, body))
                .build();

        // 发送请求
        client.newCall(request).enqueue(callback);
    }
}
