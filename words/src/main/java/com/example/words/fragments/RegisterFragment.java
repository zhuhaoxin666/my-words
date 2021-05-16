package com.example.words.fragments;

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

public class RegisterFragment extends Fragment {
    View view;

    @Override
    public void onStart() {
        super.onStart();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_register, container, false);
        }
        //获取注册密码EditText
        EditText regPass = view.findViewById(R.id.registerPassword);
        //获取确认密码的EditText
        EditText regPassConfirm = view.findViewById(R.id.registerPassConfirm);

        EditText regName = view.findViewById(R.id.registerName);

        //获取register界面的注册按钮
        Button regBtn = view.findViewById(R.id.registerConfirm);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取注册密码
                String regPassStr = regPass.getText().toString();
                //获取确认密码
                String regPassStrConfirm = regPassConfirm.getText().toString();
                //获取注册用户名
                String regNameStr = regName.getText().toString();

                //对两次密码和用户名的验证
                if (!regPassStr.equals(regPassStrConfirm)) {
                    Log.e("ERROR*******", "两次密码不一致");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("请重新输入")
                            .setMessage("两次密码不一致")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                } else if (regPassStr.equals("") || regPassConfirm.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("请重新输入")
                            .setMessage("您输入的密码或确认密码为空")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                }else if(regNameStr.equals("") || regNameStr.length() > 15){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("请重新输入")
                            .setMessage("您输入的用户名为空或者用户名长度过长")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                }
                else {
                    //获取宿主Activity的FragmentManager的FragmentTransaction
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    //将MainActivity的fragment换为注册界面的fragment
//                    fragmentTransaction.replace(R.id.loginLayout, new LoadingFragment());
//                    fragmentTransaction.commit();

                }
            }
        });

        return view;
    }
}
