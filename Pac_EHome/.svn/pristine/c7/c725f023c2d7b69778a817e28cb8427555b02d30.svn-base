package com.ehome;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.ehome.utils.ModelServer;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AirControlActivity extends BaseActivity implements OnClickListener{

	public TextView text_air_WenDu,text_air_speed,btn_air_GanZao,btn_air_SaoFeng,text_air_FengSuView,top_airset;
	private Button btn_air_modal,btn_air_down,btn_air_up,btn_air_speed,btn_air_dry,btn_title_left;
	private ToggleButton btn_air_windsweeper,btn_air_switch;
	public  int status;//要传递的码
	public int WenDu=25;//初始化现在的温度
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_air_control);
		
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		
		text_air_WenDu = (TextView)findViewById(R.id.text_air_WenDu);
		text_air_speed = (TextView)findViewById(R.id.text_air_speed);
		btn_air_GanZao = (TextView)findViewById(R.id.btn_air_GanZao);
		btn_air_SaoFeng = (TextView)findViewById(R.id.btn_air_SaoFeng);
		text_air_FengSuView = (TextView)findViewById(R.id.text_air_FengSuView);
		top_airset = (TextView)findViewById(R.id.top_airset);
		
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_air_modal = (Button) findViewById(R.id.btn_air_modal);
		btn_air_down = (Button) findViewById(R.id.btn_air_down);
		btn_air_up = (Button) findViewById(R.id.btn_air_up);
		btn_air_speed = (Button) findViewById(R.id.btn_air_speed);
		btn_air_dry = (Button) findViewById(R.id.btn_air_dry);
		
		btn_air_windsweeper = (ToggleButton) findViewById(R.id.btn_air_windsweeper);
		btn_air_switch = (ToggleButton) findViewById(R.id.btn_air_switch);	
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		btn_title_left.setOnClickListener(this);
		btn_air_modal.setOnClickListener(this);
		btn_air_down.setOnClickListener(this);
		btn_air_up.setOnClickListener(this);
		btn_air_speed.setOnClickListener(this);
		btn_air_dry.setOnClickListener(this);
		top_airset.setOnClickListener(this);
		
		btn_air_windsweeper.setOnCheckedChangeListener(air_windsweeper);
		btn_air_switch.setOnCheckedChangeListener(air_switch);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_air_modal:
			showCustomToast("模式改变");
			break;
		case R.id.btn_air_down:
			 Thread contralKongTiaoThread2 = new Thread(new ContralKongTiaoThread());
			status=2;
			contralKongTiaoThread2.start();
			WenDu--;
			text_air_WenDu.setText(WenDu+"℃");
			//showCustomToast("减温度");
			break;
		case R.id.btn_air_up:
			Thread contralKongTiaoThread1 = new Thread(new ContralKongTiaoThread());
			 status=1;
			contralKongTiaoThread1.start();
			WenDu++;
			text_air_WenDu.setText(WenDu+"℃");
			//showCustomToast("加温度");
			break;
		case R.id.btn_air_speed:
			if(text_air_speed.getText().toString().equals("低速")){
				/*Thread contralKongTiaoThread3 = new Thread(new ContralKongTiaoThread());
				 status=3;
				contralKongTiaoThread3.start();*/
				text_air_speed.setText("中速");
			}else if(text_air_speed.getText().toString().equals("中速")){
				/*Thread contralKongTiaoThread10 = new Thread(new ContralKongTiaoThread());
				 status=0;
				contralKongTiaoThread10.start();*/
				text_air_speed.setText("高速");
			}else{
				Thread contralKongTiaoThread3 = new Thread(new ContralKongTiaoThread());
				status=3;
				contralKongTiaoThread3.start();
				text_air_speed.setText("低速");
			}
			//showCustomToast("风速");
			break;
		case R.id.btn_air_dry:
			if(btn_air_GanZao.getVisibility()==View.VISIBLE){
				/* status=0;
				contralKongTiaoThread.start();*/
				btn_air_GanZao.setVisibility(View.GONE);
			}else{
				Thread contralKongTiaoThread5 = new Thread(new ContralKongTiaoThread());
				 status=5;
				contralKongTiaoThread5.start();
				btn_air_GanZao.setVisibility(View.VISIBLE);
			}
			//showCustomToast("干燥");
			break;
		case R.id.btn_title_left:
			AirControlActivity.this.finish();
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		case R.id.top_airset:
			startActivity(AirSetActivity.class);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		default:
			break;
		}
	}
	
	private ToggleButton.OnCheckedChangeListener air_windsweeper = new ToggleButton.OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			btn_air_SaoFeng.setVisibility(View.VISIBLE);
			if(btn_air_SaoFeng.getText().toString().equals("左右扫风")){
				Thread contralKongTiaoThread4 = new Thread(new ContralKongTiaoThread());
				 status=4;
				contralKongTiaoThread4.start();
				btn_air_SaoFeng.setText("上下扫风");
			}else{
				/* status=0;
				contralKongTiaoThread.start();*/
				btn_air_SaoFeng.setText("左右扫风");
			}
			/*if(isChecked){
				showCustomToast("上下扫风");
			}else{
				showCustomToast("左右扫风");
			}*/
		}
		
	};
	
	private ToggleButton.OnCheckedChangeListener air_switch = new ToggleButton.OnCheckedChangeListener(){
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				Thread contralKongTiaoThread0 = new Thread(new ContralKongTiaoThread());
				 status=0;
				contralKongTiaoThread0.start();
				text_air_WenDu.setVisibility(View.VISIBLE);
				text_air_speed.setVisibility(View.VISIBLE);
				//btn_air_GanZao.setVisibility(View.VISIBLE);
				//btn_air_SaoFeng.setVisibility(View.VISIBLE);
				text_air_FengSuView.setVisibility(View.VISIBLE);
				//showCustomToast("开");
			}else{
				Thread contralKongTiaoThread6 = new Thread(new ContralKongTiaoThread());
				 status=6;
				contralKongTiaoThread6.start();
				text_air_WenDu.setVisibility(View.GONE);
				text_air_speed.setVisibility(View.GONE);
				btn_air_GanZao.setVisibility(View.GONE);
				btn_air_SaoFeng.setVisibility(View.GONE);
				text_air_FengSuView.setVisibility(View.GONE);
				//showCustomToast("关");
			}
		}
		
	};

	//控制空调线程类
    class ContralKongTiaoThread implements Runnable
    {
        @Override
		public void run() 
        {
        	Log.i("12345","空调传过去的码是 : "+status);
			   List<NameValuePair> params = new ArrayList<NameValuePair>();
			   //添加设备号和状态传输
			   params.add(new BasicNameValuePair("deviceid",1+""));
			   params.add(new BasicNameValuePair("status",status+""));
			   ModelServer modelHttp =new ModelServer();
			   modelHttp.Model_server(params, "updateKongTiao");
			   
        }
    }
    

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
