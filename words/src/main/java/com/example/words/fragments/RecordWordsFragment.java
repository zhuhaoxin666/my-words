package com.example.words.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecordWordsFragment extends Fragment {
    View view;
    EditText recordWord;
    EditText recordMeaning;
    Button recordBtn;
    String recordWordStr;
    String recodeMeaningStr;

    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.frag_recordwords,null);
        }
        String username = getArguments().getString("username");
        Log.e("-----RecordWordsFragment------",username);

        recordWord = view.findViewById(R.id.recodeWord);
        recordMeaning = view.findViewById(R.id.recodeMeaning);
        recordBtn = view.findViewById(R.id.recodeBtn);

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取要存入的单词和解释
                recordWordStr = recordWord.getText().toString().trim();
                recodeMeaningStr = recordMeaning.getText().toString().trim();

                //判断上述获取的字符串的有效性
                if(recordWordStr.equals("")||recodeMeaningStr.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("请重新输入");
                    builder.setMessage("您输入的单词或解释不能为空");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();

                }else {

                    Log.e("name",username);
                    Log.e("word",recordWordStr);
                    Log.e("meaning",recodeMeaningStr);


                    //设置加载对话框
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("玩儿命加载中...");
                    progressDialog.setProgressStyle(ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
                    progressDialog.show();

                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request.Builder RequestBuilder = new Request.Builder();
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();
                    formBodyBuilder.add("username",username);
                    formBodyBuilder.add("word",recordWordStr);
                    formBodyBuilder.add("interpretation",recodeMeaningStr);
                    FormBody formBody = formBodyBuilder.build();

                    recordWord.setText("");
                    recordMeaning.setText("");

                    Request request = new Request.Builder().url("http://124.70.186.115:8080/word/input").post(formBody).build();

                    Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            progressDialog.cancel();
                            Toast.makeText(getActivity(),"网络原因，插入失败，请稍后重试",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Looper.prepare();

                            progressDialog.cancel();
                            Log.e("___INSERT WORDS RESPONSE___",response.body().string());
                            Toast.makeText(getActivity(),"插入成功,点击\"录单词\"即可查看",Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    });

                }

            }
        });


        return view;
    }
}
