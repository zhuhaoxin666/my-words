package com.example.my_words;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    OkHttpClient okHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getTest(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url("http://192.168.134.1:8080/server_WORDS_war_exploded/android").build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    Log.e("TOUCHED______",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Log.e("TOUCHED______","I AM TOUCHED");
    }
}