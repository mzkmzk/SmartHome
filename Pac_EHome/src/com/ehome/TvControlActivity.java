package com.ehome;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ehome.AirControlActivity.ContralKongTiaoThread;
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

public class TvControlActivity extends BaseActivity implements OnClickListener{
	
	private Button btn_tv_down,btn_tv_up,btn_tv_left,btn_tv_right,btn_title_left,btn_tv_one,btn_tv_five,btn_tv_six;
	private ToggleButton btn_tv_switch;
	private TextView top_title;
	public  int status;//要传递的码
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tv_control);
		
		initViews();
		initEvents();
		
		top_title.setText("电视");
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		top_title = (TextView) findViewById(R.id.top_title);
		btn_tv_down = (Button) findViewById(R.id.btn_tv_down);
		btn_tv_up = (Button) findViewById(R.id.btn_tv_up);
		btn_tv_left = (Button) findViewById(R.id.btn_tv_left);
		btn_tv_right = (Button) findViewById(R.id.btn_tv_right);
		btn_tv_one = (Button) findViewById(R.id.btn_tv_one);
		btn_tv_five = (Button) findViewById(R.id.btn_tv_five);
		btn_tv_six = (Button) findViewById(R.id.btn_tv_six);
		
		btn_tv_switch = (ToggleButton) findViewById(R.id.btn_tv_switch);	
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		btn_title_left.setOnClickListener(this);
		btn_tv_down.setOnClickListener(this);
		btn_tv_up.setOnClickListener(this);
		btn_tv_left.setOnClickListener(this);
		btn_tv_right.setOnClickListener(this);
		btn_tv_one.setOnClickListener(this);
		btn_tv_five.setOnClickListener(this);
		btn_tv_six.setOnClickListener(this);
		
		btn_tv_switch.setOnCheckedChangeListener(tv_switch);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_tv_down:
			Thread contralKongTiaoThread2 = new Thread(new ContralKongTiaoThread());
			status=2;
			contralKongTiaoThread2.start();
			//showCustomToast("减音量");
			break;
		case R.id.btn_tv_up:
			Thread contralKongTiaoThread1 = new Thread(new ContralKongTiaoThread());
			status=1;
			contralKongTiaoThread1.start();
			//showCustomToast("加音量");
			break;
		case R.id.btn_tv_left:
			Thread contralKongTiaoThread4 = new Thread(new ContralKongTiaoThread());
			status=4;
			contralKongTiaoThread4.start();
			//showCustomToast("减频道");
			break;
		case R.id.btn_tv_right:
			Thread contralKongTiaoThread3 = new Thread(new ContralKongTiaoThread());
			status=3;
			contralKongTiaoThread3.start();
			//showCustomToast("加频道");
			break;
		case R.id.btn_tv_one:
			Thread contralKongTiaoThread5 = new Thread(new ContralKongTiaoThread());
			status=5;
			contralKongTiaoThread5.start();
			break;
		case R.id.btn_tv_five:
			Thread contralKongTiaoThread6 = new Thread(new ContralKongTiaoThread());
			status=6;
			contralKongTiaoThread6.start();
			break;
		case R.id.btn_tv_six:
			Thread contralKongTiaoThread7 = new Thread(new ContralKongTiaoThread());
			status=7;
			contralKongTiaoThread7.start();
			break;
		case R.id.btn_title_left:
			TvControlActivity.this.finish();
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		default:
			break;
		}
	}
	
	private ToggleButton.OnCheckedChangeListener tv_switch = new ToggleButton.OnCheckedChangeListener(){
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				Thread contralKongTiaoThread0 = new Thread(new ContralKongTiaoThread());
				status=0;
				contralKongTiaoThread0.start();
				//showCustomToast("开");
			}else{
				Thread contralKongTiaoThread8 = new Thread(new ContralKongTiaoThread());
				status=8;
				contralKongTiaoThread8.start();
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
