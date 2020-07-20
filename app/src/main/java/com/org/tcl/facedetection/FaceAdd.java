package com.org.tcl.facedetection;

import java.util.HashMap;
import java.util.Map;

public class FaceAdd {
    private static String TAG = "FaceAdd";
    private static String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
    public static String add() {
        // 请求url
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", "027d8308a2ec665acb1bdf63e513bcb9");
            map.put("group_id", "group_repeat");
            map.put("user_id", "user1");
            map.put("user_info", "abc");
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "FACE_TOKEN");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            Logd(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
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
