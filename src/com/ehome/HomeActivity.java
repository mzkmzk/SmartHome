package com.ehome;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ehome.AirControlActivity.ContralKongTiaoThread;
import com.ehome.BaiDuMap.MapControlDemo;
import com.ehome.BaiDuMap.RoutePlanDemo;
import com.ehome.cammonitor.ServerAct;
import com.ehome.tuisong.server.PollingService;
import com.ehome.tuisong.server.PollingUtils;
import com.ehome.utils.ModelServer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class HomeActivity extends BaseActivity implements OnClickListener{
	private ToggleButton btn_defend,btn_modal;
	private Button btn_humiture,btn_air,btn_door,btn_tv,btn_light,btn_camera,btn_cushion,btn_map;
	private TextView tv_top_map;
	public int status;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        initViews();
        initEvents();
        
        //开启消息推送
        PollingUtils.startPollingService(this, 5, PollingService.class, PollingService.ACTION);  
	}
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		btn_defend = (ToggleButton) findViewById(R.id.btn_defend);
		btn_modal = (ToggleButton) findViewById(R.id.btn_modal);
		btn_air = (Button) findViewById(R.id.btn_air);
		btn_door = (Button) findViewById(R.id.btn_door);
		btn_tv = (Button) findViewById(R.id.btn_tv);
		btn_light = (Button) findViewById(R.id.btn_light);
		btn_camera = (Button) findViewById(R.id.btn_camera);
		btn_cushion = (Button) findViewById(R.id.btn_cushion);
		btn_humiture = (Button) findViewById(R.id.btn_humiture);
		btn_map = (Button) findViewById(R.id.btn_map);
		//tv_top_map = (TextView) findViewById(R.id.tv_top_map);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		btn_defend.setOnCheckedChangeListener(defend);
		btn_modal.setOnCheckedChangeListener(modal);
		
		btn_air.setOnClickListener(this);
		btn_door.setOnClickListener(this);
		btn_tv.setOnClickListener(this);
		btn_light.setOnClickListener(this);
		btn_camera.setOnClickListener(this);
		btn_cushion.setOnClickListener(this);
		btn_humiture.setOnClickListener(this);
		btn_map.setOnClickListener(this);
		//tv_top_map.setOnClickListener(this);
	}

	private ToggleButton.OnCheckedChangeListener defend = new ToggleButton.OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				
				showCustomToast("开启一键多控家居");
			}else{
				showCustomToast("关闭一键多控家居");
			}
		}
		
	};
	
	private ToggleButton.OnCheckedChangeListener modal = new ToggleButton.OnCheckedChangeListener(){
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				//Thread contralKongTiaoThread0 = new Thread(new ContralKongTiaoThread());
				 //status=0;
				//contralKongTiaoThread0.start();
				
				ContralKongTiaoThread light1Open = new ContralKongTiaoThread();
				light1Open.deviceid=1;
				light1Open.status="0";
				Thread contralKongTiaoThread1 = new Thread(light1Open);
				contralKongTiaoThread1.start();
				
				ContralKongTiaoThread light2Open = new ContralKongTiaoThread();
				light2Open.deviceid=2;
				light2Open.status="0";
				Thread contralKongTiaoThread2 = new Thread(light2Open);
				contralKongTiaoThread2.start();
				
				ContralKongTiaoThread light3Open = new ContralKongTiaoThread();
				light3Open.deviceid=3;
				light3Open.status="0";
				Thread contralKongTiaoThread3 = new Thread(light3Open);
				contralKongTiaoThread3.start();
				
				ContralKongTiaoThread light4Open = new ContralKongTiaoThread();
				light4Open.deviceid=4;
				light4Open.status="1";
				Thread contralKongTiaoThread4= new Thread(light4Open);
				contralKongTiaoThread4.start();
				
				//控制是否推送家里来人了;
				ContralKongTiaoThread light6Open = new ContralKongTiaoThread();
				light6Open.deviceid=6;
				light6Open.status="1";
				Thread contralKongTiaoThread6= new Thread(light6Open);
				contralKongTiaoThread6.start();
				
				showCustomToast("开启离家模式");
			}else{
//				final CharSequence[] colors = {"起床模式","睡眠模式"};
//				AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
//				//builder.setTitle("一键多控");
//				builder.setItems(colors, new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//				
//				}
//				});
//				builder.create().show();
				
				
				ContralKongTiaoThread light1Open = new ContralKongTiaoThread();
				light1Open.deviceid=1;
				light1Open.status="1";
				Thread contralKongTiaoThread1 = new Thread(light1Open);
				contralKongTiaoThread1.start();
				
				ContralKongTiaoThread light2Open = new ContralKongTiaoThread();
				light2Open.deviceid=2;
				light2Open.status="1";
				Thread contralKongTiaoThread2 = new Thread(light2Open);
				contralKongTiaoThread2.start();
				
				ContralKongTiaoThread light3Open = new ContralKongTiaoThread();
				light3Open.deviceid=3;
				light3Open.status="1";
				Thread contralKongTiaoThread3 = new Thread(light3Open);
				contralKongTiaoThread3.start();
				
				//控制热释红外是否有效 1为有效
				ContralKongTiaoThread light4Open = new ContralKongTiaoThread();
				light4Open.deviceid=4;
				light4Open.status="0";
				Thread contralKongTiaoThread4= new Thread(light4Open);
				contralKongTiaoThread4.start();
				
				//控制是否推送家里来人了;
				ContralKongTiaoThread light6Open = new ContralKongTiaoThread();
				light6Open.deviceid=6;
				light6Open.status="0";
				Thread contralKongTiaoThread6= new Thread(light6Open);
				contralKongTiaoThread6.start();

				showCustomToast("开启在家模式");
			}
		}
		
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_air:
			startActivity(AirControlActivity.class);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		case R.id.btn_tv:
			startActivity(TvControlActivity.class);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		case R.id.btn_light:
			startActivity(LightControlActivity.class);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		case R.id.btn_cushion:
			startActivity(CushionControlActivity.class);	
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		case R.id.btn_humiture:
			startActivity(HumitureControlActivity.class);	
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);			
			break;	
		case R.id.btn_door:
			startActivity(DoorActivity.class);	
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;	
		case R.id.btn_camera:
			startActivity(new Intent(HomeActivity.this, ServerAct.class));	
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		case R.id.btn_map:
			startActivity(new Intent(HomeActivity.this, RoutePlanDemo.class));
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			//startActivity(new Intent(HomeActivity.this, Animation_hint.class));	
			//overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			//需要活动的页面
			
			break;
		default:
			break;
		}
	}
	
	//控制空调线程类
    class ContralKongTiaoThread implements Runnable
    {
    	public int deviceid;
		public String status;
        @Override
		public void run() 
        {
        	Log.i("12345","空调传过去的码是 : "+status);
			   List<NameValuePair> params = new ArrayList<NameValuePair>();
			   //添加设备号和状态传输
			   params.add(new BasicNameValuePair("deviceid",deviceid+""));
			   params.add(new BasicNameValuePair("status",status+""));
			   ModelServer modelHttp =new ModelServer();
			   modelHttp.Model_server(params, "updateKongTiao");
			   
        }
    }
}