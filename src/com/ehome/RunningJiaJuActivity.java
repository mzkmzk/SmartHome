package com.ehome;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class RunningJiaJuActivity extends BaseActivity{
	
	private Button btn_title_left;
	private ToggleButton toggleButton_sound,toggleButton_air,toggleButton_camera,toggleButton_computer,
			toggleButton_curtain,toggleButton_cushion,toggleButton_door,toggleButton_fan,toggleButton_light,toggleButton_tv;
	private TextView top_title;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_jiaju);
        
        initViews();
        initEvents();
        
        top_title.setText("正在运行的家电");
	}
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		top_title  = (TextView) findViewById(R.id.top_title);
		toggleButton_air= (ToggleButton) findViewById(R.id.toggleButton_air);
		//toggleButton_camera = (ToggleButton) findViewById(R.id.toggleButton_camera);
		toggleButton_curtain = (ToggleButton) findViewById(R.id.toggleButton_curtain);
		toggleButton_cushion = (ToggleButton) findViewById(R.id.toggleButton_cushion);
		toggleButton_fan = (ToggleButton) findViewById(R.id.toggleButton_fan);
		toggleButton_light = (ToggleButton) findViewById(R.id.toggleButton_light);
		toggleButton_tv = (ToggleButton) findViewById(R.id.toggleButton_tv);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		btn_title_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	RunningJiaJuActivity.this.finish();
            	overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });
	}
	
}
