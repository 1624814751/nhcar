package com.nhcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySplashSimpleAnimation extends AppCompatActivity {
    // 声明控件对象（要获取谁就声明谁，注意类型对应）
    private ImageView mSplashItem_iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);


        initView();
        initData();
        initListener();

    }

    private void initView() {
        mSplashItem_iv = (ImageView) findViewById(R.id.splash_loading_item);
         }

    private void initData() {

    }

    private void initListener() {
        //1.实例化一个补间动画对象(实参为建立在res/anim文件夹下的动画文件)
        Animation translate = AnimationUtils.loadAnimation(this,
                R.anim.splash_loading);

        //2.设置动画对象的监听器
        translate.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            // 播放完动画后实现跳转,并实现切换效果
            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                // 2.4.1实例化一个意图对象
                Intent intent = new Intent(ActivitySplashSimpleAnimation.this,
                        ActivityHome.class);
                // 2.4.2启动意图实现跳转
                startActivity(intent);
                // 2.4.3关闭前一个窗口
                ActivitySplashSimpleAnimation.this.finish();
            }
        });

        //3.启动动画
        mSplashItem_iv.setAnimation(translate);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            Intent intent = new Intent(getApplicationContext(), ActivityHome.class);
            startActivity(intent);
            this.finish();
        }
    }
}
