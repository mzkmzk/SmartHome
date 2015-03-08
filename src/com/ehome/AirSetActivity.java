package com.ehome;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AirSetActivity extends BaseActivity implements OnClickListener {

	private Button btn_title_left;
	private TextView top_title,text_air_set;
	private RelativeLayout layout_air_set;
	private EditText dialog_edit;
	private static final int nick = 2;
	private ToggleButton toggleButton_air_set;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_air_set);
		
		initViews();
		initEvents();
		
		top_title.setText("空调设置");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		top_title = (TextView) findViewById(R.id.top_title);
		text_air_set = (TextView) findViewById(R.id.text_air_set);
		layout_air_set = (RelativeLayout) findViewById(R.id.layout_air_set);
		toggleButton_air_set = (ToggleButton) findViewById(R.id.toggleButton_air_set);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		btn_title_left.setOnClickListener(this);
		layout_air_set.setOnClickListener(this);
		toggleButton_air_set.setOnCheckedChangeListener(Button_air_set);
	}
	
	private ToggleButton.OnCheckedChangeListener Button_air_set = new ToggleButton.OnCheckedChangeListener(){
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("开");
			}else{
				showCustomToast("关");
			}
		}
		
	};
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_title_left:
			AirSetActivity.this.finish();
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		case R.id.layout_air_set:
			showDialog(nick);
			break;

		default:
			break;
		}
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
					text_air_set.setText(cushion_time);
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
}
