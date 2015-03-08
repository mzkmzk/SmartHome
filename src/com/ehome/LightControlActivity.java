package com.ehome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.ehome.AirControlActivity.ContralKongTiaoThread;
import com.ehome.HumitureControlActivity.selectThread;
import com.ehome.utils.ModelServer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class LightControlActivity extends BaseActivity implements OnClickListener{
	private Button btn_title_left;
	private TextView top_title;
	private ToggleButton toggleButton_light1,toggleButton_light2,toggleButton_light3,toggleButton_light4;
	public int status =0;//设置灯是否亮
	public int deviceid =0; //设备号
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_control);
        
        initViews();
        initEvents();
        
       final  Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // 在此处添加执行的代码
            	//showCustomToast("开");
            	selectThread TuiSong2 = new selectThread();
				//TuiSong2.deviceid=3;
            	TuiSong2.deviceid=2;
				Thread thread0 = new Thread(TuiSong2); 
				thread0.start();
                handler.postDelayed(this, 1000);// 50是延时时长
            } 
        }; 
        handler.postDelayed(runnable, 1000);// 打开定时器，执行操作
        
        top_title.setText("灯光");
	}
	
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		top_title = (TextView) findViewById(R.id.top_title);
		
		toggleButton_light1 = (ToggleButton) findViewById(R.id.toggleButton_light1);
		toggleButton_light2 = (ToggleButton) findViewById(R.id.toggleButton_light2);
		toggleButton_light3 = (ToggleButton) findViewById(R.id.toggleButton_light3);
		toggleButton_light4 = (ToggleButton) findViewById(R.id.toggleButton_light4);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		btn_title_left.setOnClickListener(this);
		
		toggleButton_light1.setOnCheckedChangeListener(light1);
		toggleButton_light2.setOnCheckedChangeListener(light2);
		toggleButton_light3.setOnCheckedChangeListener(light3);
		toggleButton_light4.setOnCheckedChangeListener(light4);
	}
	
	private ToggleButton.OnCheckedChangeListener light1 = new ToggleButton.OnCheckedChangeListener(){
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if(isChecked){
				
				ContralKongTiaoThread light1Open = new ContralKongTiaoThread();
				light1Open.deviceid=1;
				light1Open.status="1";
				Thread contralKongTiaoThread1 = new Thread(light1Open);
				contralKongTiaoThread1.start();
				showCustomToast("已开启一号灯");
			}else{
				ContralKongTiaoThread light1Open = new ContralKongTiaoThread();
				light1Open.deviceid=1;
				light1Open.status="0";
				Thread contralKongTiaoThread1 = new Thread(light1Open);
				contralKongTiaoThread1.start();
				showCustomToast("已关闭一号灯");
				/*Thread contralKongTiaoThread1 = new Thread(new ContralKongTiaoThread());
				 status=1;
				contralKongTiaoThread1.start();*/
				//showCustomToast("关");
			}
		}
		
	};
	
	private ToggleButton.OnCheckedChangeListener light2 = new ToggleButton.OnCheckedChangeListener(){
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				ContralKongTiaoThread light1Open = new ContralKongTiaoThread();
				light1Open.deviceid=2;
				light1Open.status="1";
				Thread contralKongTiaoThread1 = new Thread(light1Open);
				contralKongTiaoThread1.start();
				showCustomToast("已开启二号灯");
				//showCustomToast("开");
			}else{
				ContralKongTiaoThread light1Open = new ContralKongTiaoThread();
				light1Open.deviceid=2;
				light1Open.status="0";
				Thread contralKongTiaoThread1 = new Thread(light1Open);
				contralKongTiaoThread1.start();
				showCustomToast("已关闭二号灯");
				
				status_quanju="0";
				panduan=-1;
				//Thread contralKongTiaoThread3 = new Thread(new ContralKongTiaoThread());
				 //status=3;
				//contralKongTiaoThread3.start();
				//showCustomToast("关");
			}
		}
		
	};
	
private ToggleButton.OnCheckedChangeListener light3 = new ToggleButton.OnCheckedChangeListener(){
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				ContralKongTiaoThread light1Open = new ContralKongTiaoThread();
				light1Open.deviceid=3;
				light1Open.status="1";
				Thread contralKongTiaoThread1 = new Thread(light1Open);
				contralKongTiaoThread1.start();
				showCustomToast("已开启三号灯");
				//showCustomToast("开");
			}else{
				ContralKongTiaoThread light1Open = new ContralKongTiaoThread();
				light1Open.deviceid=3;
				light1Open.status="0";
				Thread contralKongTiaoThread1 = new Thread(light1Open);
				contralKongTiaoThread1.start();
				showCustomToast("已关闭三号灯");
				//Thread contralKongTiaoThread3 = new Thread(new ContralKongTiaoThread());
				 //status=3;
				//contralKongTiaoThread3.start();
				//showCustomToast("关");
			}
		}
		
	};
	
	//控制电灯线程类
    class ContralKongTiaoThread implements Runnable
    {
    	
    	public int deviceid;
		public String status;
        @Override
		public void run() 
        {
        	Log.i("12345","设备号 : " +deviceid+"空调传过去的码是 : "+status);
			   List<NameValuePair> params = new ArrayList<NameValuePair>();
			   //添加设备号和状态传输
			   params.add(new BasicNameValuePair("deviceid",deviceid+""));
			   params.add(new BasicNameValuePair("status",status+""));
			   ModelServer modelHttp =new ModelServer();
			   modelHttp.Model_server(params, "updateKongTiao");
			   
        }
    }
	
	int device_quanju;
	String status_quanju;
	class selectThread implements Runnable {

		public int deviceid;
		public String status;
		@Override
		public void run() {
			Log.i("12345"," 设备号 :"+deviceid);
			   List<NameValuePair> params = new ArrayList<NameValuePair>();
			   //添加设备号
			   params.add(new BasicNameValuePair("deviceid",deviceid+""));
			   ModelServer modelHttp =new ModelServer();
			   Map<Boolean,String> result = modelHttp.Model_server(params, "queryKongTiao");
			   try {
				  if(!result.isEmpty()){
				JSONObject json= new JSONObject(result.get(true));
				//status =json.getString("status");
				//给Message传递消息
				device_quanju=deviceid;
				status_quanju =json.getString("status");
				 Message msg = queryMaxhandler.obtainMessage();
				 msg.what=0;
				 queryMaxhandler.sendMessage(msg);
				
				Log.i("12345","status :" +status);
				 }
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
	
	int panduan=-1;
	int ifstart=0;
	//Handler
    Handler queryMaxhandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
            case 0:
            	Log.i("12345","二号等自动获取状态 :" +status_quanju+"上次状态为"+panduan+"是否为初次"+ifstart);
            	if(status_quanju.equals("1")&&panduan!=-1){
            	panduan=1;
            	toggleButton_light2.performClick();
            	}
            	//确定初始状态
//            	 if(ifstart==0){
//            		 ifstart=1;
//            		 panduan=Integer.parseInt(status_quanju);
//            		 if(panduan==1){
//            			 toggleButton_light2.performClick();
//            			 break;
//            		 }
//            	 }
//            	if(status_quanju.equals("1")&&!status_quanju.equals(panduan+"")){
//            		
//            		toggleButton_light2.performClick();
//            		panduan=1;
//            		//panduan++;
//            		
//            	}
//            	if(panduan==1){
//            		showCustomToast("二号灯已开启");
//            		
//            	}
                break;
            
            }
            
        }
    };
	
	
//	private ToggleButton.OnCheckedChangeListener light3 = new ToggleButton.OnCheckedChangeListener(){
//		
//		@Override
//		public void onCheckedChanged(CompoundButton buttonView,
//				boolean isChecked) {
//			// TODO Auto-generated method stub
//			if(isChecked){
//				showCustomToast("开");
//			}else{
//				showCustomToast("关");
//			}
//		}
//		
//	};
	
	private ToggleButton.OnCheckedChangeListener light4 = new ToggleButton.OnCheckedChangeListener(){
		
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
			LightControlActivity.this.finish();
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;

		default:
			break;
		}
	}

}
