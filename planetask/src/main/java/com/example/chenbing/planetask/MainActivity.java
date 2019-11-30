package com.example.chenbing.planetask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenbing.planetask.component.MainService;
import com.example.chenbing.planetask.component.PlaneView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements SensorEventListener
{
    // 定义Sensor管理器
    private SensorManager mSensorManager;
//    EditText etGravity;

    // 定义飞机的移动速度
    private int speed = 10;
    PlaneView planeView;



    int count = 1;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //添加音乐
//        Intent music = new Intent(MainActivity.this,MainService.class);
//        startService(music);
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 创建PlaneView组件
//        final PlaneView planeView = (PlaneView)findViewById(R.id.planeView);
//        setContentView(R.layout.activity_main);
        planeView = new PlaneView(this);
        setContentView(planeView);
        planeView.setBackgroundResource(R.drawable.back);


//        etGravity = (EditText) findViewById(R.id.etGravity);

        // 获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        // 获得屏幕宽和高
        display.getMetrics(metrics);
        // 设置飞机的初始位置
        planeView.currentX = metrics.widthPixels / 2;
        planeView.currentY = metrics.heightPixels - 1500;
        //①在模拟器上通过键盘控制飞机的移动
        // 为planeView组件的键盘事件绑定监听器
        planeView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View source, int keyCode, KeyEvent event)
            {
                // 获取由哪个键触发的事件
                switch (event.getKeyCode())
                {
                    // 控制飞机下移
                    case KeyEvent.KEYCODE_S:
                        planeView.currentY += speed;
                        break;
                    // 控制飞机上移
                    case KeyEvent.KEYCODE_W:
                        planeView.currentY -= speed;
                        break;
                    // 控制飞机左移
                    case KeyEvent.KEYCODE_A:
                        planeView.currentX -= speed;
                        break;
                    // 控制飞机右移
                    case KeyEvent.KEYCODE_D:
                        planeView.currentX += speed;
                        break;
                }

                String tip = "游戏结束！";
                //判断飞机是否碰到手机边缘
                if (planeView.currentX < 0 || planeView.currentY < 0 || planeView.currentX+225 >  metrics.widthPixels || planeView.currentY + 225 > metrics.heightPixels){
                    // 使用Toast提示信息
                    Toast.makeText(MainActivity.this, tip
                            , Toast.LENGTH_SHORT).show();
                    // 创建一个Intent
                    Intent intent = new Intent(MainActivity.this,
                            StartActivity.class);
                    // 启动intent对应的Activity
                    startActivity(intent);
                }

                // 通知planeView组件重绘
                planeView.invalidate();
                return true;
            }
        });
        // 获取传感器管理服务
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //游戏的开始和暂停Toast提示框
        String tip;
        Toast toast = new Toast(MainActivity.this);
        ImageView image = new ImageView(MainActivity.this);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //有按下动作时暂停
                if (count == 1){
                    count--;
                    speed = 0;
                    tip = "游戏暂停";
                    image.setImageResource(R.drawable.pause);
                    this.onPause();
                }else {
                    count++;
                    speed = 10;
                    tip = "游戏开始";
                    image.setImageResource(R.drawable.continuepic);
                    this.onResume();
                }

                // 使用Toast提示信息
//                Toast.makeText(MainActivity.this, tip
//                        , Toast.LENGTH_SHORT).show();

                //创建一个LinearLayout容器
                LinearLayout ll = new LinearLayout(MainActivity.this);
                ll.addView(image);
                //创建一个TextView
                TextView textView = new TextView(MainActivity.this);
                textView.setText(tip);
                textView.setTextColor(Color.GREEN);
                ll.addView(textView);
                toast.setView(ll);
                toast.setDuration(Toast.LENGTH_LONG);
                showMyToast(toast,500);
                break;
        }
        return super.dispatchTouchEvent(ev);
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
        }, cnt );
    }

    @Override
    protected void onPause() {
        // 程序暂停时取消注册传感器监听器
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStart() {
        // 获取传感器管理服务
//        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 为系统的重力传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        // 程序退出时取消注册传感器监听器
        mSensorManager.unregisterListener(this);

        Intent music = new Intent(MainActivity.this,MainService.class);
        stopService(music);
        super.onStop();
    }

    //在手机上通过重力传感器控制飞机的移动
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // 获取触发event的传感器类型
        int sensorType = event.sensor.getType();
        switch (sensorType){
            // 重力传感器
            case Sensor.TYPE_GRAVITY:
                planeView.currentX -= values[0]*5;
                planeView.currentY += values[1]*5;
                // 通知planeView组件重绘
                planeView.invalidate();
                break;
        }
        // 获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        // 获得屏幕宽和高
        display.getMetrics(metrics);

        String tip = "游戏结束！";
        //判断飞机是否碰到手机边缘
        if (planeView.currentX < 0 || planeView.currentY < 0 || planeView.currentX+225 >  metrics.widthPixels || planeView.currentY + 225 > metrics.heightPixels){
            // 使用Toast提示信息
            Toast toast = new Toast(MainActivity.this);
            //创建一个TextView
            TextView textView = new TextView(MainActivity.this);
            textView.setText(tip);
            toast.setView(textView);
            toast.setDuration(Toast.LENGTH_LONG);
            showMyToast(toast, 1000);

            Intent intent = new Intent(MainActivity.this,
                    StartActivity.class);
            // 启动intent对应的Activity
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

