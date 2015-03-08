package com.botb.activity;

import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelcomeActivity extends Activity {

	private ImageView mShowPicture = null;// 显示图片
	private Animation mFadeIn = null;// 开始动画
	private Animation mFadeOut = null;// 开始动画
	private Drawable mPicture_1 = null;// 第一张图片
	private Drawable mPicture_2 = null;
	private ImageView mShowtext = null;;

	protected void onCreate(Bundle savedInstanceState) {
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
		WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		// 绑定界面
		mShowPicture = (ImageView) findViewById(R.id.guide_picture);
		mShowtext = (ImageView) findViewById(R.id.guide_text);


		
		// 初始化图片资源
		mPicture_1 = getResources().getDrawable(R.drawable.pac_bg_logo3);
		
		// 设置开始界面效果
		mShowPicture.setImageDrawable(mPicture_1);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.mshowpicture);
		anim.setFillAfter(true);
		mShowPicture.startAnimation(anim);
		
        mPicture_2 = getResources().getDrawable(R.drawable.pac_bg_text);
        mShowtext.setImageDrawable(mPicture_2);
		mShowtext.setVisibility(View.GONE);       
		
		mFadeIn = AnimationUtils.loadAnimation(this,R.anim.guide_welcome_fade_in);
		mFadeIn.setDuration(1500L);
		


		// 初始化动画
		Handler handler = new Handler();
		TimerTask task = new TimerTask() {
			public void run() {
				// 隐藏掉全屏图片
				mShowtext.setVisibility(View.VISIBLE);
				mShowtext.startAnimation(mFadeIn);
			}
		};
		// 5秒后执行TimerTask任务
		handler.postDelayed(task, 1500);
		
		// 设置动画的监听
		mFadeIn.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				// 第一个动画结束时开始第二个动画
				startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
				finish();
			}
		});
	}
}
