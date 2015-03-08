package com.botb.service;

import java.util.ArrayList;
import java.util.List;

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

import com.botb.Bean.shop;
import com.botb.activity.MyNumberActivity;
import com.botb.activity.MyNumberOneActivity;
import com.botb.activity.R;
import com.botb.geturl.getURL;
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
	private shop s;
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
		new PollingThread().start();
	}

	private void initNotifiManager() {
		sharedata = getSharedPreferences("data", 0);
		try {
			jsonObject =new JSONObject(sharedata.getString("user", null)).getJSONObject("u");
			uphone =jsonObject.getString("uphone");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		int icon = R.drawable.pic_index_logo2;
		mNotification = new Notification();
		mNotification.icon = icon;
		mNotification.tickerText = "New Message";
		mNotification.defaults |= Notification.DEFAULT_SOUND;
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotification.defaults |= Notification.DEFAULT_VIBRATE;
		 mNotification.defaults |= Notification.DEFAULT_SOUND;  
	}

	private void showNotification() {
		mNotification.when = System.currentTimeMillis();
		//Navigator to the new activity when click the notification title
		Intent i = new Intent(this, MyNumberOneActivity.class);
		Bundle  mBundel =new Bundle ();
		mBundel.putSerializable(SER_KEY, s);
		i.putExtras(mBundel);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
				Intent.FLAG_ACTIVITY_NEW_TASK);
		mNotification.setLatestEventInfo(this,
				getResources().getString(R.string.app_name), mNotification.tickerText, pendingIntent);
		mManager.notify(0, mNotification);
	}
	 //初始化HttpClient，并设置超时
    public HttpClient getHttpClient()
    {
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
        HttpClient client = new DefaultHttpClient(httpParams);
        return client;
    }
	/**
	 * Polling thread
	 * @Author Ryan
	 * @Create 2013-7-13 上午10:18:34
	 */
	int count = 0;
	class PollingThread extends Thread {
		@Override
		public void run() {
			System.out.println("Polling...");
			count ++;
			if (count % 3 == 0) {
				String url =getURL.getUrl("TongZhiIndentAction");
				Log.i("12345",url);
				HttpPost request = new HttpPost(url);
		        //如果传递参数多的话，可以对传递的参数进行封装
		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		        //添加用户名和密码
		        params.add(new BasicNameValuePair("uphone",uphone));
		        try
		        {
		            //设置请求参数项
		            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		            HttpClient client = getHttpClient();
		            //执行请求返回相应
		            HttpResponse response = client.execute(request);
		            //判断是否请求成功
		            Log.i("12345","1");
		            if(response.getStatusLine().getStatusCode()==200)
		            {
		            	Log.i("12345","2");
		                //获得响应信息
		                responseMsg = EntityUtils.toString(response.getEntity());
		                Log.i("12345",responseMsg);
		            }
		        }catch(Exception e)
		        {
		            e.printStackTrace();
		        }
		        JSONObject jsonObject1;
		        JSONObject jsonObject2;
				try {
					jsonObject1 = new JSONObject(responseMsg).getJSONObject("s");
					jsonObject2 = new JSONObject(responseMsg).getJSONObject("i");
					s=new shop();
					 s.setSid(jsonObject1.getInt("sid"));
	         		   s.setSNow(jsonObject1.getInt("SNow"));
	         		   s.setAddress(jsonObject1.getString("address"));
	         		   s.setSphone(jsonObject1.getString("sphone"));
	         		   s.setSname(jsonObject1.getString("sname"));
	         		   s.setLongtude(jsonObject1.getString("longtude"));
	         		   s.setDimensionality(jsonObject1.getString("dimensionality"));
	         		   s.setType(jsonObject1.getInt("type"));
	         		   s.setInfo(jsonObject1.getString("info"));
	         		   s.setSelectInfo(jsonObject1.getString("selectInfo"));
					 if(jsonObject1!=null)
						 mNotification.tickerText=jsonObject1.getString("sname")+"的号快到您啦!!!!!";
							showNotification();
				} catch (JSONException e) {
					e.printStackTrace();
				}
		       
			}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("Service:onDestroy");
	}

}
