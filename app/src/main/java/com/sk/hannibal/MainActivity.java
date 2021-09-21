package com.sk.hannibal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;


import butterknife.BindView;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.sdsdds)
    AppCompatTextView sss;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

