package com.qhwnb.collect;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpRequestTask {

    private static final String TAG = "HttpRequestTask";
    private static final String API_URL = "https://api-store.openguet.cn/api/member/photo/user/update";
    private static final String APP_ID = "d2806f63d44e44bf9268f0c8d925068b";
    private static final String APP_SECRET = "138086fd68218d6e94753bbb609d8745c1e9a";

    private final ExecutorService executorService;

    public HttpRequestTask() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void execute() {
        executorService.execute(this::sendHttpRequest);
    }

    private void sendHttpRequest() {
        HttpURLConnection urlConnection = null;
        try {
            // Create URL object
            URL url = new URL(API_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            // Set request headers
            urlConnection.setRequestProperty("Accept", "application/json, text/plain, */*");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("appId", APP_ID);
            urlConnection.setRequestProperty("appSecret", APP_SECRET);

            // Prepare request body
            String jsonInputString = "{"
                    + "\"avatar\": \"http://1.95.42.69:40027/nuJpw1.jpg\","
                    + "\"id\": \"1833102751324835840\","
                    + "\"introduce\": \"测试一下\","
                    + "\"sex\": \"2\","
                    + "\"username\": \"老六\""
                    + "}";

            // Send request
            urlConnection.setDoOutput(true);
            try (OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response
            int responseCode = urlConnection.getResponseCode();
            Log.d(TAG, "Response Code: " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            Log.d(TAG, "Response Body: " + response.toString());

        } catch (Exception e) {
            Log.e(TAG, "Error during HTTP request", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
