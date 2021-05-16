package com.example.words;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.words.bean.LoginStatus;
import com.example.words.bean.WordAndMeaning;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.ByteString;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test(){
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("username","noone");
        builder.add("password","123456");
        RequestBody requestBody = builder.build();

        Request request = new Request.Builder().url("http://124.70.186.115:8080/word/existUser").post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();

            //先用FastJson将字符串转化为json串
            String parse = (String) JSON.parse(response.body().string());
            System.out.println(parse);
            //再用Gson转为Java对象
            Gson gson = new Gson();
            LoginStatus status = gson.fromJson(parse,LoginStatus.class);
            System.out.println(status);
//            System.out.println(string1);
//            String jso = new String(string1);
//            JSON parse = (JSON) JSON.parse(jso);
//            Status status = (Status) JSON.toJavaObject(parse,Status.class);
//            System.out.println(status.getCode()+"\t"+status.getMessage()+"\t"+status.getName());
//            Status status = gson.fromJson(parse, Status.class);

//            String s = gson.toJson(string);
//            System.out.println(string);
//            System.out.println(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test(){
        String s = "[ \"{\"meaning\":\".n 书\",\"word\":\"book\"}\", \"{\"meaning\":\".n 桌子\",\"word\":\"table\"}\", \"{\"meaning\":\".v 玩\",\"word\":\"play\"}\" ]";
        Object parse = JSONArray.parse(s);
        System.out.println(parse);
//        List<WordAndMeaning> wordAndMeanings = JSONObject.parseArray(parse, WordAndMeaning.class);
    }

}