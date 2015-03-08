package com.ehome;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements OnClickListener{
	
	private ViewPager mTabPager;	
	private ImageView layout_voice;// 动画图片
	private ImageView mTab1,mTab2;
	private TextView thome,tset;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;//单个水平动画位移
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost);
        
        initViews();
        initEvents();
        
        Display currDisplay = getWindowManager().getDefaultDisplay();//获取屏幕当前分辨率
        int displayWidth = currDisplay.getWidth();
        int displayHeight = currDisplay.getHeight();
        one = displayWidth/4; //设置水平动画平移大小
        //Log.i("info", "获取的屏幕分辨率为" + one+"X" + displayHeight);
        
        //InitImageView();//使用动画
        //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.activity_home, null);
        View view2 = mLi.inflate(R.layout.activity_set, null);
        
        //每个页面的view数据
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        
        //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}
			
			//@Override
			//public CharSequence getPageTitle(int position) {
				//return titles.get(position);
			//}
			
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		
		mTabPager.setAdapter(mPagerAdapter);
		
		 /**
		 * 头标点击监听
		 */
	}
	
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
        mTabPager = (ViewPager)findViewById(R.id.tabpager);
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
        
        mTab1 = (ImageView) findViewById(R.id.img_home);
        mTab2 = (ImageView) findViewById(R.id.img_set);
        layout_voice  = (ImageView) findViewById(R.id.layout_voice);
        
        thome = (TextView) findViewById(R.id.text_home);
        tset = (TextView) findViewById(R.id.text_set);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
        
        layout_voice.setOnClickListener(this);
        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.layout_voice:
			showCustomToast("请长按并语音");
			break;

		default:
			break;
		}
	}
	
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};
    
	 /* 页卡切换监听
	 */
	 public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(R.drawable.setting_checked));
				thome.setTextColor(thome.getResources().getColor(R.color.color_blue));
				if (currIndex == 1) {
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.setting_normal));
					tset.setTextColor(tset.getResources().getColor(R.color.color_hint));
				} 
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(R.drawable.setting_checked));
				tset.setTextColor(tset.getResources().getColor(R.color.color_blue));
				if (currIndex == 0) {
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.setting_normal));
					thome.setTextColor(thome.getResources().getColor(R.color.color_hint));
				}
				break;
			}
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
}
