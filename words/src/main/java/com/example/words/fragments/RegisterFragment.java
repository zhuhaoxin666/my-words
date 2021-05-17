package com.example.words.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.words.R;
import com.example.words.activity.SecActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        //获取注册用户名
        EditText regName = view.findViewById(R.id.registerName);

        EditText regAge = view.findViewById(R.id.registerAge);

        EditText regMotto = view.findViewById(R.id.registerMotto);
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
                //获取注册年龄
                String regAgeStr = regAge.getText().toString();
                //获取个性签名
                String regMottoStr = regMotto.getText().toString();

                //表单验证
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
                }else if(regAgeStr.equals("")||regMottoStr.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("请重新输入")
                            .setMessage("您输入的用户名年龄或者用户个性签名为空")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                }else if(!isInteger(regAgeStr)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("请重新输入")
                            .setMessage("您输入的用户名年龄必须为数字")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                }
                else {

                    //设置加载对话框
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("玩儿命加载中...");
                    progressDialog.setProgressStyle(ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
                    progressDialog.show();

                    //发送请求，把User注册到服务器
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request.Builder RequestBuilder = new Request.Builder();
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();

                    //构建请求体
                    formBodyBuilder.add("name",regNameStr);
                    formBodyBuilder.add("password",regPassStrConfirm);
                    formBodyBuilder.add("age",regAgeStr);
                    formBodyBuilder.add("motto",regMottoStr);
                    FormBody formBody = formBodyBuilder.build();

                    Request request = RequestBuilder.url("http://124.70.186.115:8080/word/userRegister").post(formBody).build();
                    Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Looper.prepare();
                            progressDialog.cancel();
                            Toast.makeText(getActivity(),"请求失败，请重试",Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Looper.prepare();
                            progressDialog.cancel();
                            Intent intent = new Intent(getActivity(), SecActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("username",regNameStr);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            getActivity().finish();
                            Looper.loop();
                        }
                    });



                }
            }
        });

        return view;
    }


    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
