package com.example.chenbing.planetask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chenbing.planetask.component.StartService;

//程序启动入口
public class StartActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        //添加音乐
        Intent music = new Intent(StartActivity.this,StartService.class);
        startService(music);


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

    @Override
    protected void onStart() {
        super.onStart();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("Cbb","--------onPause--------");
//        musicCount++;
//        if (musicCount == 1){
//            Intent music = new Intent(StartActivity.this,StartService.class);
//            musicCount--;
//            Intent intent1 = getIntent();
//            String choose = "123";
//            choose = intent1.getStringExtra("choose");
//            if (choose=="abc"){
//                stopService(music);
//                finish();
//                Log.d("Cbb", "--------onPauseChoose--------");
//            }
//            Log.d("Cbb","--------onPauseEnd--------");
//        }
//    }


    @Override
    protected void onStop() {


        super.onStop();
        StartActivity.this.finish();
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}