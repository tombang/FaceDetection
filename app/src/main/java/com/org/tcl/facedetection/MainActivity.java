package com.org.tcl.facedetection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener{
    private String TAG = "MainActivity";
    private static final int CODE = 1;
    private Context mContext;
    private Button button;
    String text = null;
    String picture = null;
    Thread thread = null;

    //@SuppressLint("StringFormatInvalid")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        button = findViewById(R.id.btn_add);
        button.setOnClickListener(this);
//        text = mContext.getResources().getString(R.string.wifi_calling_off_explanation, mContext.getResources().getString(R.string.wifi_calling_off_explanation_2));
//        String emptyViewText = mContext.getResources().getString(R.string.wifi_calling_off_explanation) + mContext.getResources().getString(R.string.wifi_calling_off_explanation_2);
//        Log.i(TAG, "text: " + text + "\n  emptyViewText: " + emptyViewText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                Log.i(TAG, "this is add button");
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CODE);
                break;
            case R.id.btn_check:
                Log.i(TAG, "this is check button");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CODE) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data"); // bundle.get("data") return android.graphics.Bitmap@cdcbb22
                Log.i(TAG, "this: " + bitmap);
                picture = Base64Util.encode(getByteFromBitmap(bitmap));
                thread = new Thread(networkTask);
                thread.start();
            }
        }
    }

    private byte[] getByteFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            String result = FaceAdd.add(picture);
            Log.i(TAG, "this result is: " + result);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.interrupt();
    }
}