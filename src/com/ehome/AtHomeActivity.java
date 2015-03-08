package com.ehome;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AtHomeActivity extends BaseActivity{
	
	private Button btn_title_left;
	private ToggleButton toggleButton_sound,toggleButton_air,toggleButton_camera,toggleButton_computer,
			toggleButton_curtain,toggleButton_cushion,toggleButton_door,toggleButton_fan,toggleButton_light,toggleButton_tv;
	private TextView top_title,text_athome_distance;
	private RelativeLayout layout_athome_distance;
	private EditText dialog_edit;
	private static final int nick = 2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athome);
        
        initViews();
        initEvents();
        
        top_title.setText("回家模式");
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
		layout_athome_distance = (RelativeLayout) findViewById(R.id.layout_athome_distance);
		text_athome_distance = (TextView) findViewById(R.id.text_athome_distance);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		        //toggleButton_sound.setOnCheckedChangeListener(Button_sound);
		toggleButton_air.setOnCheckedChangeListener(Button_air);
		         //toggleButton_camera.setOnCheckedChangeListener(Button_camera);
		         //toggleButton_computer.setOnCheckedChangeListener(Button_computer);
		toggleButton_curtain.setOnCheckedChangeListener(Button_curtain);
		       // toggleButton_cushion.setOnCheckedChangeListener(Button_cushion);
		           // toggleButton_door.setOnCheckedChangeListener(Button_door);
		toggleButton_fan.setOnCheckedChangeListener(Button_fan);
		toggleButton_light.setOnCheckedChangeListener(Button_light);
		toggleButton_tv.setOnCheckedChangeListener(Button_tv);
		
		btn_title_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	AtHomeActivity.this.finish();
            	overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });
		layout_athome_distance.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	showDialog(nick);
            }
        });
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		/***
		 * 用switch语句的好处：
		 * 1、逻辑清晰，方便阅读代码
		 * 2、一个Activity里或许要用到很多dialog，都放在这里方便管理
		 */
		LayoutInflater factory = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		
		switch (id) {
		case nick:
			final View customViewnick = factory.inflate(R.layout.alert_dialog_nick, null);
			dialog_edit = (EditText)customViewnick.findViewById(R.id.dialog_edit);
			final Dialog ad2 = new Dialog(this, R.style.Theme_CustomDialog2);
			ad2.setContentView(customViewnick);
			ad2.setCancelable(false);//有可能你调不出这个方法，可以通过ad.setOnKeyListener()方法来实现屏蔽back键
			ad2.setCanceledOnTouchOutside(false);//当点击除对话框外屏幕其他区域时对话框不消失，默认是消失的

			Button buttonnickyes = (Button)customViewnick.findViewById(R.id.yes);
			buttonnickyes.setOnClickListener(new Button.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					String cushion_time = dialog_edit.getText().toString();
					text_athome_distance.setText(cushion_time);
					ad2.dismiss();
				}
			});
			Button buttonnickno = (Button)customViewnick.findViewById(R.id.no);
			buttonnickno.setOnClickListener(new Button.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					ad2.dismiss();
				}
			});
			return ad2;			
		}
		return null;
	}	
	
	private ToggleButton.OnCheckedChangeListener Button_sound = new ToggleButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("Button_sound");
			}else{

			}
		}
	};
	
	private ToggleButton.OnCheckedChangeListener Button_air = new ToggleButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("Button_air");
			}else{
				
			}
		}
	};
	
	private ToggleButton.OnCheckedChangeListener Button_camera = new ToggleButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("Button_camera");
			}else{
				
			}
		}
	};
	
	private ToggleButton.OnCheckedChangeListener Button_computer = new ToggleButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("Button_computer");
			}else{
				
			}
		}
	};
	
	private ToggleButton.OnCheckedChangeListener Button_curtain = new ToggleButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("Button_curtain");
			}else{
				
			}
		}
	};
	
	private ToggleButton.OnCheckedChangeListener Button_cushion = new ToggleButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("Button_cushion");
			}else{
				
			}
		}
	};
	
	private ToggleButton.OnCheckedChangeListener Button_door = new ToggleButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("Button_door");
			}else{
				
			}
		}
	};
	
	private ToggleButton.OnCheckedChangeListener Button_fan = new ToggleButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("Button_fan");
			}else{
				
			}
		}
	};
	
	private ToggleButton.OnCheckedChangeListener Button_light = new ToggleButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("Button_light");
			}else{
				
			}
		}
	};
	private ToggleButton.OnCheckedChangeListener Button_tv = new ToggleButton.OnCheckedChangeListener(){
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("Button_tv");
			}else{
				
			}
		}
	};
	
}
