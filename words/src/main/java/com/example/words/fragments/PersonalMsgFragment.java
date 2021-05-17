package com.example.words.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.example.words.R;
import com.example.words.bean.UserInfo;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersonalMsgFragment extends Fragment {

    View view;
    TextView displayName;
    TextView displayAge;
    TextView displayMotto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //设置加载对话框
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("玩儿命加载中...");
        progressDialog.setProgressStyle(ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
        progressDialog.show();

        if(view == null){
            view = inflater.inflate(R.layout.frag_personinfo,null);
        }

        String username = getArguments().getString("username");

        displayName = view.findViewById(R.id.displayName);
        displayAge = view.findViewById(R.id.displayAge);
        displayMotto = view.findViewById(R.id.displayMotto);


        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String name = bundle.getString("name");
                String age = bundle.getString("age");
                String motto = bundle.getString("motto");

                progressDialog.cancel();

                displayName.setText("name:"+name);
                displayAge.setText("age:"+age);
                displayMotto.setText(motto);
            }
        };


        //从服务器获取用户信息
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("username",username);
                FormBody formBody = builder.build();

                Request request = new Request.Builder().url("http://124.70.186.115:8080/word/getUserInfo").post(formBody).build();
                Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            String parse = (String) JSON.parse(response.body().string());
                            Gson gson = new Gson();
                            UserInfo userInfo = gson.fromJson(parse, UserInfo.class);

                            //将子线程得到的信息由Message传送给Handler，在主线程中更新UI
                            Message message = Message.obtain();
                            Bundle bundle = new Bundle();
                            bundle.putString("name",userInfo.getName());
                            bundle.putString("age", String.valueOf(userInfo.getAge()));
                            bundle.putString("motto",userInfo.getMotto());
                            message.setData(bundle);
                            handler.sendMessage(message);




                        }
                    });




        return view;
    }

}
