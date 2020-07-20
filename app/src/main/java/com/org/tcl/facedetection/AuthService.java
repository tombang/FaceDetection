package com.org.tcl.facedetection;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class AuthService {
    private static String TAG = "AuthService";
    private static String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
    private static String clientId = "euLs4UhiPGxX70GmtKeOIMZs";
    private static String clientSecret = "vk5xZSPE3Gk7bQep7TYB7ebjWt3dW7q8";
    public static String getAuth() {
        return getAuth(clientId, clientSecret);
    }

    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                Logd("key + \"--->\" + map.get(key)");
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            Logd("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            Logi("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    private static void Logd(String s) {
        android.util.Log.d(TAG, s);
    }

    private static void Logi (String s) {
        android.util.Log.i(TAG, s);
    }
}
