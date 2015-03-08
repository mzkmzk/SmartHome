package com.ehome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DoorfaceActivity extends BaseActivity{

	private Button btn_title_left;
	private TextView top_title,top_faceset;
	private LinearLayout layout_facegone,layout_facegone1;
	CustomProgressDialog progress;
	
	private final int SPLASH_DISPLAY_LENGHT = 2000; //延迟三秒 
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_facerecord);
        
        initViews();
        initEvents();
        
		
        
		top_title.setText("识别记录");
		
		progress = new CustomProgressDialog(DoorfaceActivity.this,"正在获取数据中...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		
		new Handler().postDelayed(new Runnable(){    
	          
            @Override   
            public void run() {    
            	progress.dismiss();
            	layout_facegone1.setVisibility(View.VISIBLE);
            }    
                   
           }, SPLASH_DISPLAY_LENGHT);   
	}
	
	
	
    @Override
	protected void initViews() {
		// TODO Auto-generated method stub
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		top_title = (TextView) findViewById(R.id.top_title);		
		top_faceset = (TextView) findViewById(R.id.top_faceset);		
		layout_facegone = (LinearLayout) findViewById(R.id.layout_facegone);		
		layout_facegone1 = (LinearLayout) findViewById(R.id.layout_facegone1);		
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		btn_title_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	DoorfaceActivity.this.finish();
            	overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });		
		top_faceset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				progress.show();
				
				new Handler().postDelayed(new Runnable(){    
			          
		            @Override   
		            public void run() {    
		            	progress.dismiss();
		            	layout_facegone.setVisibility(View.VISIBLE);
		            }    
		                   
		           }, SPLASH_DISPLAY_LENGHT); 
				
			}
		});		
	}
}
