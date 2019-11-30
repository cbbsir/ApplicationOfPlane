package com.example.chenbing.planetask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenbing.planetask.component.LoginService;
import com.example.chenbing.planetask.component.MainService;

import java.util.Timer;
import java.util.TimerTask;


public class LoginActivity extends Activity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //添加音乐
//        Intent music = new Intent(LoginActivity.this,LoginService.class);
//        startService(music);

        // 获取只能被本应用程序读、写的SharedPreferences对象
        preferences = getSharedPreferences("The_Plane_Of_User_Pwd", MODE_PRIVATE);
        editor = preferences.edit();

        Button bn = (Button) findViewById(R.id.loginBtn);
        bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.name);
                EditText passwd = (EditText) findViewById(R.id.passwd);
//                RadioButton male = (RadioButton) findViewById(R.id.male);
//                String gender = male.isChecked() ? "男 " : "女";


                // 使用Toast提示信息
                Toast toast = new Toast(LoginActivity.this);
                ImageView image = new ImageView(LoginActivity.this);


                // 读取姓名
                String name1 = preferences.getString("name", null);
                // 读取密码
                String passwd1 = preferences.getString("passwd", null);

                String result;
                if (name1.equals(name.getText().toString()) && passwd1.equals(passwd.getText().toString())){
                    result = "登陆成功！\n" + "欢迎回来：" +name1;
                    // 创建一个Intent
                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    // 启动intent对应的Activity
                    startActivity(intent);
                    image.setImageResource(R.drawable.success);
                    finish();
                }else{
                    result ="您暂时还未注册,用户名或密码不正确";
                    image.setImageResource(R.drawable.fail);
                }


                //创建一个LinearLayout容器
                LinearLayout ll = new LinearLayout(LoginActivity.this);
                ll.addView(image);
                //创建一个TextView
                TextView textView = new TextView(LoginActivity.this);
                textView.setText(result);
                textView.setTextColor(Color.GREEN);
                ll.addView(textView);
                toast.setView(ll);
                toast.setDuration(Toast.LENGTH_LONG);
                showMyToast(toast,500);




            }
        });

        //返回注册
        Button bn1 = (Button) findViewById(R.id.register);
        bn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                // 启动intent对应的Activity
                startActivity(intent);
                finish();
            }
        });

    }

    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        },0,3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
//        Intent music = new Intent(LoginActivity.this,LoginService.class);
//        stopService(music);

//        Intent intent1 = new Intent(LoginActivity.this,
//                StartActivity.class);
//        Bundle data = new Bundle();
//        data.putString("choose","123");
//        intent1.putExtras(data);
//        startActivity(intent1);
        super.onStop();
    }
}