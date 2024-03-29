package com.example.chenbing.planetask;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.chenbing.planetask.component.PlaneView;

public class MainActivityKeyboard extends Activity
{
    // 定义飞机的移动速度
    private int speed = 10;

    int count = 1;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 创建PlaneView组件
        final PlaneView planeView = new PlaneView(this);
        setContentView(planeView);
        planeView.setBackgroundResource(R.drawable.back);
        // 获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        // 获得屏幕宽和高
        display.getMetrics(metrics);
        // 设置飞机的初始位置
        planeView.currentX = metrics.widthPixels / 2;
        planeView.currentY = metrics.heightPixels - 100;
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
                // 通知planeView组件重绘
                planeView.invalidate();
                return true;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String tip;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //有按下动作时暂停
                if (count == 1){
                    count--;
                    speed = 0;
                    tip = "游戏暂停";
                }else {
                    count++;
                    speed = 10;
                    tip = "游戏开始";
                }

                // 使用Toast提示信息
                Toast.makeText(MainActivityKeyboard.this, tip
                        , Toast.LENGTH_SHORT).show();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

