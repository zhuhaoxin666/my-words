package com.example.words.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import com.example.words.R;
import com.example.words.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

    Button btnRegister;
//jkc
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.loginLayout,new LoginFragment());
        fragmentTransaction.commit();


    }
}