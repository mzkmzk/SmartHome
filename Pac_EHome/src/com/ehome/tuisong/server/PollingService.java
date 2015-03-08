package com.ehome.tuisong.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RadioGroup;

import com.ehome.AirControlActivity;
import com.ehome.Animation_hint;
import com.ehome.HomeActivity;
import com.ehome.HumitureControlActivity;
import com.ehome.R;
import com.ehome.Thread_.updateThread;
import com.ehome.cammonitor.ServerAct;
import com.ehome.utils.ModelServer;
/**
 * Polling service
 * @Author mzk
 * @Create 2013-7-13 上午10:18:44
 */
public class PollingService extends Service {
	  private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
	    private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
	    private String responseMsg = "";//返回信息
	    private RadioGroup rg_select;
	    private String type =""; //登陆类型
	public static final String ACTION = "com.botb.service.PollingService";
	private SharedPreferences sharedata;
	private JSONObject jsonObject;
	private String uphone;
	private String stx;//提醒字符
	//private shop s;
	public  final static String SER_KEY = "com.tutor.objecttran.ser"; 
	private Notification mNotification;
	private NotificationManager mManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		initNotifiManager();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		/*
		 * 暂时取消推送消息 还有Home界面的OnCreate方法里的启动方法也注释了
		 * selectThread TuiSong7 = new selectThread();
		TuiSong7.deviceid=7;
		Thread thread7 = new Thread(TuiSong7); 
		thread7.start();
		
		//坐垫
		selectThread TuiSong5 = new selectThread();
		TuiSong5.deviceid=5;
		Thread thread5 = new Thread(TuiSong5); 
		thread5.start();
		
		//坐垫
				selectThread TuiSong6 = new selectThread();
				TuiSong6.deviceid=6;
				Thread thread6 = new Thread(TuiSong6); 
				thread6.start();*/
		
		//人体
		
		selectThread TuiSong6 = new selectThread();
		TuiSong6.deviceid=6;
		Thread thread6 = new Thread(TuiSong6); 
		thread6.start();
	}
	
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
				status =json.getString("status");
				Log.i("12345"," 状态!!!!!!!!!!!!status :" +status+"设备id!!!!!!!!!!!!!!!!1"+deviceid);
				//温度传感器推送 家里着火;
				if(deviceid==7&&Integer.parseInt(status)>=32){
					mNotification.tickerText="家里温度过高";//设置提示文字
					showNotification(deviceid);
					PollingUtils.stopPollingService(PollingService.this, PollingService.class, PollingService.ACTION);  
				}else if(deviceid==6&&Integer.parseInt(status)>=1){
					Log.i("12345","家里有人进入开启");
					mNotification.tickerText="家里有人进入";//设置提示文字
					showNotification(deviceid); //家里有人
					PollingUtils.stopPollingService(PollingService.this, PollingService.class, PollingService.ACTION);  
				}else if(deviceid==5&&Integer.parseInt(status)==1){
					mNotification.tickerText="坐太久,该起来运动运动了 ";//设置提示文字
					showNotification(deviceid); //坐垫坐太久了
					 PollingUtils.stopPollingService(PollingService.this, PollingService.class, PollingService.ACTION);  
				}
				   }
			   try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
	

	private void initNotifiManager() {
		
		mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		int icon = R.drawable.smart_home;
		//int icon = R.drawable.air;
		mNotification = new Notification();
		mNotification.icon = icon;
		mNotification.tickerText = "New Message";
		mNotification.defaults |= Notification.DEFAULT_SOUND;
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotification.defaults |= Notification.DEFAULT_VIBRATE;
		 mNotification.defaults |= Notification.DEFAULT_SOUND;  
	}

	private void showNotification(int deviceid) {
		mNotification.when = System.currentTimeMillis();
		//Navigator to the new activity when click the notification title
		//设置跳转的页面
		Intent i;
		if(deviceid==7){
			i = new Intent(this, HumitureControlActivity.class);//温度过高 跳转到温度显示界面
		}else if(deviceid == 5){
			i = new Intent(this, Animation_hint.class);//坐垫太久了
		}else if(deviceid == 6){
			i = new Intent(this, ServerAct.class);//家里有别人进入
		}else{
			i = new Intent(this, Animation_hint.class);//温度过高 跳转到温度显示界面
		}
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
				Intent.FLAG_ACTIVITY_NEW_TASK);
		mNotification.setLatestEventInfo(this,
				getResources().getString(R.string.app_name), mNotification.tickerText, pendingIntent);
		mManager.notify(0, mNotification);
	}


	
	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("Service:onDestroy");
	}

}
