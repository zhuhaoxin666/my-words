package com.example.words.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.words.R;
import com.example.words.fragments.PersonalMsgFragment;
import com.example.words.fragments.RecordWordsFragment;
import com.example.words.fragments.RememberWordsFragment;
import com.example.words.fragments.Welcome;

import java.util.Timer;
import java.util.TimerTask;

public class SecActivity extends AppCompatActivity {

    private RelativeLayout rememberLayout;
    private RelativeLayout recordLayout;
    private RelativeLayout personInfoLayout;

    private RecordWordsFragment recordWordsFragment = new RecordWordsFragment();
    private PersonalMsgFragment personalMsgFragment = new PersonalMsgFragment();
    private RememberWordsFragment rememberWordsFragment = new RememberWordsFragment();
    private Welcome welcome = new Welcome();

    //进行Fragment的管理
    private FragmentManager fragmentManager;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);


        //获取登录的用户名
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String username = extras.getString("username");
        Log.e("____________username___________", username);


        //获取三个对应功能块的ID
        personInfoLayout = findViewById(R.id.person_msg_layout);
        recordLayout = findViewById(R.id.record_layout);
        rememberLayout = findViewById(R.id.remember_layout);
        fragmentManager = getSupportFragmentManager();


        //进入主界面时就展示背单词的fragment
        fragmentManager.beginTransaction().replace(R.id.frag_content, welcome).commit();


        //为三个模块设置监听，点击就转到对应的模块
        rememberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                rememberWordsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.frag_content, rememberWordsFragment).commit();
            }
        });

        recordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                recordWordsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.frag_content, recordWordsFragment).commit();
            }
        });

        personInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                personalMsgFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.frag_content, personalMsgFragment).commit();
            }
        });
    }

    //设置点击返回，两秒之内再次点击返回，退出界面

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
}