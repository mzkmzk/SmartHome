package com.ehome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.ehome.utils.ModelServer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class HumitureControlActivity extends BaseActivity{
	private Button btn_title_left;
	private TextView top_title,text_temperature,text_humidity,text_air_quality,text_carbon;
	private RelativeLayout tab_humiture,tab2_humiture,tab3_humiture,layout_humidity,layout_temperature,layout_air_quality,layout_carbon;
	private LinearLayout humiture_control;
	private ToggleButton toggleButton_humiture;
	
	private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒 
	 CustomProgressDialog progress ;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humiture_control);
        
        initViews();
        initEvents();
        
        progress = new CustomProgressDialog(HumitureControlActivity.this,"正在获取数据中...");
        selectThread TuiSong7 = new selectThread();
		TuiSong7.deviceid=7;
		Thread thread7 = new Thread(TuiSong7); 
		thread7.start();
		
		selectThread TuiSong8 = new selectThread();
		TuiSong8.deviceid=8;
		Thread thread8 = new Thread(TuiSong8); 
		thread8.start();
        
        top_title.setText("室内数据");
	}
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		top_title = (TextView) findViewById(R.id.top_title);
		
		text_temperature = (TextView) findViewById(R.id.text_temperature);
		text_humidity = (TextView) findViewById(R.id.text_humidity);
		text_air_quality = (TextView) findViewById(R.id.text_air_quality);
		text_carbon = (TextView) findViewById(R.id.text_carbon);
		
		tab_humiture = (RelativeLayout) findViewById(R.id.tab_humiture);
		tab2_humiture = (RelativeLayout) findViewById(R.id.tab2_humiture);
		tab3_humiture = (RelativeLayout) findViewById(R.id.tab3_humiture);
		
		layout_temperature = (RelativeLayout) findViewById(R.id.layout_temperature);
		layout_humidity = (RelativeLayout) findViewById(R.id.layout_humidity);
		layout_air_quality = (RelativeLayout) findViewById(R.id.layout_air_quality);
		layout_carbon = (RelativeLayout) findViewById(R.id.layout_carbon);
		
		toggleButton_humiture = (ToggleButton) findViewById(R.id.toggleButton_humiture);
		humiture_control = (LinearLayout) findViewById(R.id.humiture_control);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		toggleButton_humiture.setOnCheckedChangeListener(Button_humiture_set);
		btn_title_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	HumitureControlActivity.this.finish();
            	overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });
		tab_humiture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(HumitureTabActivity.class);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
		});
		tab2_humiture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(HumitureTab2Activity.class);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
		});
		tab3_humiture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(HumitureTab3Activity.class);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
		});
		
		layout_temperature.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				selectThread TuiSong7 = new selectThread();
//				TuiSong7.deviceid=7;
//				Thread thread7 = new Thread(TuiSong7); 
//				thread7.start();
//				progress.setCanceledOnTouchOutside(false);
//				progress.show();
				//showCustomToast("当前温度");
				
				//获取随机值
				progress.setCanceledOnTouchOutside(false);
				progress.show();
				
				new Handler().postDelayed(new Runnable(){    
			          
		            @Override   
		            public void run() {    
		            	progress.dismiss();
		            	String temperature = String.valueOf((int)(Math.random() * 3+28)) ;
		            	text_temperature.setText(temperature+"℃");
		            }    
		                   
		        }, SPLASH_DISPLAY_LENGHT); 
			}
		});
		
		layout_humidity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				selectThread TuiSong8 = new selectThread();
//				TuiSong8.deviceid=8;
//				Thread thread8 = new Thread(TuiSong8); 
//				thread8.start();
//				progress.setCanceledOnTouchOutside(false);
//				progress.show();
				//showCustomToast("当前湿度");
				
				progress.setCanceledOnTouchOutside(false);
				progress.show();
				
				new Handler().postDelayed(new Runnable(){    
			          
		            @Override   
		            public void run() {    
		            	progress.dismiss();
		            	String humidity = String.valueOf((int)(Math.random() * 3+68)) ;
		            	text_humidity.setText(humidity+"%");
		            }    
		                   
		        }, SPLASH_DISPLAY_LENGHT); 
			}
		});
		
		layout_air_quality.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				progress.setCanceledOnTouchOutside(false);
				progress.show();
				
				new Handler().postDelayed(new Runnable(){    
					
					@Override   
					public void run() {    
						progress.dismiss();
						String air_quality = String.valueOf((int)(Math.random() * 3+60)) ;
						text_air_quality.setText(air_quality+"");
					}    
					
				}, SPLASH_DISPLAY_LENGHT); 
			}
		});
		
		layout_carbon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				progress.setCanceledOnTouchOutside(false);
				progress.show();
				
				new Handler().postDelayed(new Runnable(){    
					
					@Override   
					public void run() {    
						progress.dismiss();
						String carbon = String.valueOf((int)(Math.random() * 3+29)) ;
						text_carbon.setText(carbon+"ppm");
					}    
					
				}, SPLASH_DISPLAY_LENGHT); 
			}
		});
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
	
	//Handler
    Handler queryMaxhandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
            case 0:
            	Log.i("12345",msg+"11111");
            	//showCustomerToast(R.drawable.btn_check_buttonless_on,"加载成功");
            	Log.i("12345",msg+"22222222222222");
            	if(device_quanju==7)
            	text_temperature.setText(status_quanju+"℃");
            	else if(device_quanju ==8){
            		text_humidity.setText(status_quanju+"%");
            	}
            	progress.dismiss();
                break;
            
            }
            
        }
    };
	
	
	boolean isChecked = true;
	
	private ToggleButton.OnCheckedChangeListener Button_humiture_set = new ToggleButton.OnCheckedChangeListener(){
		
		
		
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				showCustomToast("已关闭");
				humiture_control.setVisibility(View.GONE);
			}else{
				showCustomToast("已启用");
				humiture_control.setVisibility(View.VISIBLE);
			}
		}
		
	};
}
