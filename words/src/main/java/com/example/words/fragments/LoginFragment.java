package com.example.words.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.words.R;

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

                //判断密码是否为空
                if (usernameStr.equals("") || passwordStr.equals("")) {
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
