package com.example.words.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.alibaba.fastjson.JSON;
import com.example.words.R;
import com.example.words.activity.SecActivity;
import com.example.words.bean.LoginStatus;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginFragment extends Fragment {
    View view;
    Button btnRegister;
    Button btnLogin;
    EditText username;
    EditText password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_login, container, false);
        }
        //获取登录按钮
        btnLogin = view.findViewById(R.id.login);

        //获取 用户名EditText和 密码EditText
        username = view.findViewById(R.id.loginUsername);
        password = view.findViewById(R.id.loginPassword);


        //设置登录监听
        //跳转至主界面
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                Log.e("------------------------", "OnclickBegin---------------");

                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();

                boolean flag = true;

                //判断密码是否为空
                if (usernameStr.equals("") || passwordStr.equals("")) {
                    flag = false;

                    Log.e("TAG", "--------PASSWORD OR USERNAME IS NULL---------");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("请重新输入")
                            .setMessage("您输入的用户名或密码为空")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                }

                //验证用户名和密码
                if (flag) {
                    //设置加载对话框
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("玩儿命加载中...");
                    progressDialog.setProgressStyle(ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
                    progressDialog.show();
                    new Thread(new Runnable() {


                        @Override
                        public void run() {
                            OkHttpClient okHttpClient = new OkHttpClient();

                            //构造POST请求体FormBody -> RequestBody
                            FormBody.Builder builder = new FormBody.Builder();
                            builder.add("username", usernameStr);
                            builder.add("password", passwordStr);
                            RequestBody requestBody = builder.build();

                            Request request =
                                    new Request
                                            .Builder()
                                            .url("http://124.70.186.115:8080/word/existUser")
                                            .post(requestBody).build();
                            Call call = okHttpClient.newCall(request);
                            Response response;
                            try {
                                response = call.execute();
//                                Log.e("________HTTP-RESPONSE_________", String.valueOf(response.body().string()));

                                //先用FastJson将字符串转化为json串
                                String parse = (String) JSON.parse(response.body().string());
                                //再用Gson转为Java(Status)对象
                                Gson gson = new Gson();
                                LoginStatus status = gson.fromJson(parse, LoginStatus.class);
                                Integer code = status.getCode();
                                String name = status.getName();

                                Log.e("________Status_________", String.valueOf(status.getCode()));


                                if (code == 200) {
                                    Log.e("________Status_________", status.getMessage());
                                    Log.e("________Status_________", status.getName());

                                    //传递获得的用户名到SecActivity
                                    Intent intent = new Intent(getActivity(),SecActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username",status.getName());
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                } else if(code == 400){
                                    //弹出提示框对话框，提示输入密码或用户名错误

                                    //在子线程中操作UI需在两端加上Looper.prepare()与Looper.loop()
                                    Looper.prepare();
                                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                    builder1.setTitle("请重新输入")
                                            .setMessage("用户名或密码错误")
                                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    progressDialog.cancel();
                                                }
                                            }).create().show();

                                    Looper.loop();
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
            }
        });

        //跳转至注册页面
        //获取注册按钮
        btnRegister = view.findViewById(R.id.register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "ONCLICLK————————————————————————————————————————————————*********");

                //获取宿主Activity的FragmentManager的FragmentTransaction
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                //将MainActivity的fragment换为注册界面的fragment
                fragmentTransaction.replace(R.id.loginLayout, new RegisterFragment());
                //添加返回栈，使得注册界面可以返回到登录界面
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
