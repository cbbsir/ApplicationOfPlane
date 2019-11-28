package com.example.chenbing.planetask;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//程序启动入口
public class StartActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        //改变背景图片透明度
        View vw = findViewById(R.id.startd);
        vw.getBackground().setAlpha(200);

        //登陆按钮
        Button login = (Button)findViewById(R.id.login);
        //注册按钮
        Button register = (Button)findViewById(R.id.register);




        //进入登陆界面
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent
                Intent intent = new Intent(StartActivity.this,
                        LoginActivity.class);

                // 启动intent对应的Activity
                startActivity(intent);
            }
        });

        //进入注册界面
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent
                Intent intent = new Intent(StartActivity.this,
                        RegisterActivity.class);

                // 启动intent对应的Activity
                startActivity(intent);
            }
        });



    }
}