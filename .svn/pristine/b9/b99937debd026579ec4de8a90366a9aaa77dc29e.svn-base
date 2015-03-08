package com.ehome;

import com.ehome.AirControlActivity.ContralKongTiaoThread;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DoorActivity extends BaseActivity{

	private Button btn_title_left;
	private TextView top_title;
	private ToggleButton toggleButton_door_nfc,toggleButton_door_face;
	private RelativeLayout layout_door_face;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_control);
        
        initViews();
        initEvents();
        
        top_title.setText("门");
	}
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		top_title = (TextView) findViewById(R.id.top_title);
		toggleButton_door_nfc = (ToggleButton) findViewById(R.id.toggleButton_door_nfc);
		toggleButton_door_face = (ToggleButton) findViewById(R.id.toggleButton_door_face);
		layout_door_face = (RelativeLayout) findViewById(R.id.layout_door_face);
		
		toggleButton_door_nfc.setOnCheckedChangeListener(door_nfc);
		toggleButton_door_face.setOnCheckedChangeListener(door_face);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		btn_title_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	DoorActivity.this.finish();
            	overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });
		layout_door_face.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(DoorfaceActivity.class);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
		});
		
	}
	private ToggleButton.OnCheckedChangeListener door_nfc = new ToggleButton.OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				
			}else{
				
			}
		}
		
	};
	private ToggleButton.OnCheckedChangeListener door_face = new ToggleButton.OnCheckedChangeListener(){
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				
			}else{
				
			}
		}
		
	};
}
