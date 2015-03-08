package com.ehome;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class VoiceActivity extends BaseActivity{
	private Button btn_title_left;
	private TextView top_title;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voiceset);
        
        initViews();
        initEvents();
        
        top_title.setText("语音功能设置");
	}
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		top_title = (TextView) findViewById(R.id.top_title);		
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		btn_title_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	VoiceActivity.this.finish();
            	overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });
	}

}
