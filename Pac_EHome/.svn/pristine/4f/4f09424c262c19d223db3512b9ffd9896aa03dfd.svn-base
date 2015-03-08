package com.ehome;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class SetActivity extends BaseActivity implements OnClickListener{
	
	private RelativeLayout layout_leavehome,layout_athome,layout_voice,layout_defend,layout_running_jiaju;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        
        initViews();
        initEvents();
	}
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		layout_leavehome = (RelativeLayout) findViewById(R.id.layout_leavehome);
		layout_athome = (RelativeLayout) findViewById(R.id.layout_athome);
		layout_voice = (RelativeLayout) findViewById(R.id.layout_voice);
		layout_defend = (RelativeLayout) findViewById(R.id.layout_defend);
		layout_running_jiaju = (RelativeLayout) findViewById(R.id.layout_running_jiaju);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		layout_leavehome.setOnClickListener(this);
		layout_athome.setOnClickListener(this);
		layout_voice.setOnClickListener(this);
		layout_defend.setOnClickListener(this);
		layout_running_jiaju.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.layout_leavehome:
			startActivity(LeaveHomeActivity.class);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		case R.id.layout_athome:
			startActivity(AtHomeActivity.class);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);			
			break;
		case R.id.layout_voice:
			startActivity(VoiceActivity.class);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);			
			break;
		case R.id.layout_defend:
			startActivity(DefendActivity.class);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);			
			break;
		case R.id.layout_running_jiaju:
			startActivity(RunningJiaJuActivity.class);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);			
			break;

		default:
			break;
		}
	}
	
}
