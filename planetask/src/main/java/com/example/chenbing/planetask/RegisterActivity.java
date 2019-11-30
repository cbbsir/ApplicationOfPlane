package com.example.chenbing.planetask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenbing.planetask.component.MainService;
import com.example.chenbing.planetask.component.RegisterService;
import com.example.chenbing.planetask.component.StartService;
import com.example.chenbing.planetask.domain.Person;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//注册界面
public class RegisterActivity extends Activity{

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //引入注册布局配置文件
        setContentView(R.layout.register);

        //添加音乐
//        Intent music = new Intent(RegisterActivity.this,RegisterService.class);
//        startService(music);

        // 获取只能被本应用程序读、写的SharedPreferences对象
        preferences = getSharedPreferences("The_Plane_Of_User_Pwd", MODE_PRIVATE);
        editor = preferences.edit();

        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText name = (EditText) findViewById(R.id.name);
                EditText passwd = (EditText) findViewById(R.id.passwd);
                RadioButton male = (RadioButton) findViewById(R.id.male);
                String gender = male.isChecked() ? "男 " : "女";

                // 使用Toast提示信息
                Toast toast = new Toast(RegisterActivity.this);
                String result;
//                if (name.toString()==null || passwd.toString()==null){
//                    result = "用户名或密码不能为空";
//                }else{
//            }
                Person p = new Person(name.getText().toString(), passwd
                        .getText().toString(), gender);
                // 创建一个Bundle对象
                Bundle data = new Bundle();
                data.putSerializable("person", p);


//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 "
//                        + "hh:mm:ss");
                // 存入当前时间
//                editor.putString("time", sdf.format(new Date()));
                // 存入一个随机数
//                editor.putInt("random", (int) (Math.random() * 100));


                //存入姓名
                editor.putString("name", name.getText().toString());
                //存入密码
                editor.putString("passwd", passwd.getText().toString());
                //存入性别
                editor.putString("gender", gender);

                // 提交所有存入的数据
                editor.commit();


                result = "注册成功";
                // 使用Toast提示信息
//                Toast.makeText(RegisterActivity.this, result
//                        , Toast.LENGTH_SHORT).show();

                ImageView image = new ImageView(RegisterActivity.this);
                image.setImageResource(R.drawable.success);
                //创建一个LinearLayout容器
                LinearLayout ll = new LinearLayout(RegisterActivity.this);
                ll.addView(image);
                //创建一个TextView
                TextView textView = new TextView(RegisterActivity.this);
                textView.setText(result);
                ll.addView(textView);
                toast.setView(ll);
                toast.setDuration(Toast.LENGTH_LONG);
                showMyToast(toast, 500);

                // 创建一个Intent
                Intent intent = new Intent(RegisterActivity.this,
                        StartActivity.class);
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
    protected void onStop() {
        super.onStop();
//        Intent music = new Intent(RegisterActivity.this, RegisterService.class);
//        stopService(music);

    }
}