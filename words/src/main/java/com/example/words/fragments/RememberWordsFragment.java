package com.example.words.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.words.R;
import com.example.words.bean.WordAndMeaning;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

import static android.content.ContentValues.TAG;

public class RememberWordsFragment extends Fragment {

    FragmentManager fragmentManager;


    View view;
    List<WordAndMeaning> wordList = new ArrayList<WordAndMeaning>();
    FloatingActionButton delWordsBtn;
    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public  View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //设置加载对话框
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("玩儿命加载中...");
        progressDialog.setProgressStyle(ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
        progressDialog.show();





        if(view == null){
            view = inflater.inflate(R.layout.frag_remeberwords,null);
        }

        //获取删除按钮
        delWordsBtn = view.findViewById(R.id.delWordsBtn);

        String username = getArguments().getString("username");
        Log.e(TAG, "onCreateView: "+username, null);


        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String str = bundle.getString("list");
                Object parse = JSON.parse(str);
                Gson gson = new Gson();
                wordList = gson.fromJson((String) parse,new TypeToken<List<WordAndMeaning>>(){}.getType());
                List<Map<String,String>> list = new ArrayList<>();
                for (int i = 0; i < wordList.size(); i++) {
                    Map<String,String> map = new HashMap<>();
                    WordAndMeaning wordAndMeaning = wordList.get(i);
                    map.put("word",wordAndMeaning.getWord());
                    map.put("meaning",wordAndMeaning.getMeaning());
                    list.add(map);
                }

                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View listWords = layoutInflater.inflate(R.layout.listview_words,null);
                SimpleAdapter simpleAdapter =
                        new SimpleAdapter(getActivity(),
                                list,R.layout.listview_words,
                                new String[]{"word","meaning"},
                                new int[]{R.id.listword,R.id.listmeaning});
                ListView listView = view.findViewById(R.id.ListView_words);
                listView.setAdapter(simpleAdapter);
                progressDialog.cancel();
            }
        };


        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        FormBody name = builder.add("username", username).build();
        Request request = new Request.Builder().url("http://124.70.186.115:8080/word/output").post(name).build();
        Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    String string = response.body().string();
//                   Log.e("-----------------", string);

                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("list",string);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        String username = getArguments().getString("username");
        delWordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(getActivity());
                DialogBuilder
                        .setTitle("确认删除！")
                        .setMessage("您确认删除所有的单词吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //设置加载对话框
                                ProgressDialog progressDialog1 = new ProgressDialog(getActivity());
                                progressDialog1.setMessage("玩儿命加载中...");
                                progressDialog1.setProgressStyle(ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
                                progressDialog1.show();


                                Log.e("_____del_____","删除单词被点击了");
                                OkHttpClient okpClient = new OkHttpClient();
                                Request.Builder builder1 = new Request.Builder();
                                FormBody.Builder formBuilder = new FormBody.Builder();
                                formBuilder.add("username",username);
                                FormBody formBody = formBuilder.build();
                                Request request1 = builder1.url("http://124.70.186.115:8080/word/delete").post(formBody).build();
                                Call newCall = okpClient.newCall(request1);
                                newCall.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                    }

                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                        progressDialog1.cancel();
                                        Looper.prepare();
                                        Toast.makeText(getActivity(),"删除完成,点击\"录单词\"添加单词即可",Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();

            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
