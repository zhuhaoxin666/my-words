package com.example.words.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.words.R;
import com.example.words.fragments.PersonalMsgFragment;
import com.example.words.fragments.RecordWordsFragment;
import com.example.words.fragments.RememberWordsFragment;

public class SecActivity extends AppCompatActivity {

    private RelativeLayout rememberLayout;
    private RelativeLayout recordLayout;
    private RelativeLayout personInfoLayout;

    private RecordWordsFragment recordWordsFragment = new RecordWordsFragment();
    private PersonalMsgFragment personalMsgFragment = new PersonalMsgFragment();
    private RememberWordsFragment rememberWordsFragment = new RememberWordsFragment();

    //进行Fragment的管理
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        //获取三个对应功能块的ID
        personInfoLayout = findViewById(R.id.person_msg_layout);
        recordLayout = findViewById(R.id.record_layout);
        rememberLayout = findViewById(R.id.remember_layout);
        fragmentManager = getSupportFragmentManager();

        //进入主界面时就展示背单词的fragment
        fragmentManager.beginTransaction().replace(R.id.frag_content,rememberWordsFragment).commit();


        //为三个模块设置监听，点击就转到对应的模块
        rememberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frag_content,rememberWordsFragment).commit();
            }
        });

        recordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frag_content,recordWordsFragment).commit();
            }
        });

        personInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frag_content,personalMsgFragment).commit();
            }
        });
    }
}