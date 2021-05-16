package com.example.words.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.words.R;
import com.example.words.bean.WordAndMeaning;
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

import static android.content.ContentValues.TAG;

public class RememberWordsFragment extends Fragment {
    View view;

    String[] words = new String[]{"book","table","mouse","door","login","record","class","fragment","computer","screen","window"};
    String[] meaning = new String[]{".n 书",".n 桌子",".n 鼠标",".n 门",".n 登录",".n 记录",".n 班级",".n 片段",".n 电脑",".n 屏幕",".n 窗户"};

    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.frag_remeberwords,null);
        }

        String username = getArguments().getString("username");
        Log.e(TAG, "onCreateView: "+username, null);

       new Thread(new Runnable() {
           @Override
           public void run() {
               OkHttpClient okHttpClient = new OkHttpClient();
               FormBody.Builder builder = new FormBody.Builder();
               FormBody name = builder.add("username", username).build();
               Request request = new Request.Builder().url("http://124.70.186.115:8080/word/output").post(name).build();
               Call call = okHttpClient.newCall(request);
               try {
                   Response response = call.execute();
//                   String string = response.body().string();
//                   Log.e("-----------------", string);
                   Log.e("-----------------", response.body().string());

//
//                   List<WordAndMeaning> wordAndMeanings = JSONObject.parseArray(parse, WordAndMeaning.class);
//                   for (WordAndMeaning w:wordAndMeanings
//                   ) {
//                       Log.e("_________WORDANDMENING_____",w.getWord()+"\t"+w.getMeaning());


               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }).start();

        List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            Map<String,String> map = new HashMap<>();
            map.put("words",words[i]);
            map.put("meaning",meaning[i]);
            list.add(map);
        }

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View listWords = layoutInflater.inflate(R.layout.listview_words,null);
        SimpleAdapter simpleAdapter =
                new SimpleAdapter(getActivity(),
                        list,R.layout.listview_words,
                        new String[]{"words","meaning"},
                        new int[]{R.id.listword,R.id.listmeaning});
        ListView listView = view.findViewById(R.id.ListView_words);
        listView.setAdapter(simpleAdapter);
        return view;
    }
}
