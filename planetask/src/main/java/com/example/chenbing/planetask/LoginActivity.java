package com.example.chenbing.planetask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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
                }else{
                    result ="您暂时还未注册,用户名或密码不正确";
                }

                // 使用Toast提示信息
                Toast.makeText(LoginActivity.this, result
                        , Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}