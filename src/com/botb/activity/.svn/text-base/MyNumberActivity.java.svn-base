package com.botb.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.botb.BaseActivity;
import com.botb.InnerListView;
import com.botb.Bean.shop;
import com.botb.Util.JuLiPaixU;
import com.botb.activity.util.Utility;
import com.botb.geturl.getURL;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyNumberActivity extends BaseActivity implements OnClickListener{

	public  final static String SER_KEY = "com.tutor.objecttran.ser"; 
	CustomProgressDialog progress;
	private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
	private String responseMsg = "";//返回信息
	public SimpleAdapter adapter;
	private List<shop> ss; //搜索得到的商店信息
	private  Utility u;;
	//集合用于存储数据；
	List<Map<String, Object>> data;
	InnerListView lv;
	 private SharedPreferences sharedata;
	private JSONObject jsonObject;
	private String uphone;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mynumber_spinner);
		 u=new Utility();
		initViews();
		initEvents();
		fillDate();
	}
	protected void onRestart() {
		fillDate();
		super.onRestart();
	}
	protected void initViews() {
		lv=(InnerListView)findViewById(R.id.lv_my);
	}
	protected void initEvents() {
	
	}
	

     private void fillDate() {
    	//加载客户信息
 		sharedata = getSharedPreferences("data", 0);
 		try {
 			jsonObject =new JSONObject(sharedata.getString("user", null)).getJSONObject("u");
 			uphone =jsonObject.getString("uphone");
 		} catch (JSONException e) {
 			e.printStackTrace();
 		}
    	 data= new ArrayList<Map<String,Object>>();

		 progress = new CustomProgressDialog(MyNumberActivity.this,"正在获取数据...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
          Thread myNumThread = new Thread(new MyNumThread());
          myNumThread.start();
          adapter =new SimpleAdapter(getApplicationContext(), data, R.layout.activity_lv, new String[]{"id","pic","title","content","distance"}, new int[]{R.id.lv_id,R.id.lv_pic,R.id.lv_title,R.id.lv_content,R.id.lv_distance});
        	//把适配器绑定给ListView
          lv.setAdapter(adapter);
          lv.setOnItemClickListener(new OnItemClickListener() {
         	   
              public void onItemClick(AdapterView<?> arg0, View arg1,
                      int position, long arg3) {
             	
  				Intent intent = new Intent(MyNumberActivity.this,MyNumberOneActivity.class);
  				Bundle  mBundel =new Bundle ();
  				mBundel.putSerializable(SER_KEY, ss.get(position));
  				intent.putExtras(mBundel);
  				startActivity(intent);
              }
          });
	}

   //MyNumThread线程类
     class MyNumThread implements Runnable
     {

         public void run() 
         {
         	try {
                 Log.i("12345","1234567999999999998"+uphone);
             //URL合法，但是这一步并不验证密码是否正确
             boolean MyNumValidate = MyNumServer(uphone);
             Log.i("12345","----------------------------bool is :"+MyNumValidate+"----------response:"+responseMsg);
             Log.i("12345","111111");
             Message msg = handler.obtainMessage();
             Log.i("12345","222222");
             if(MyNumValidate)
             {
             	JSONObject json;
 					json= new JSONObject(responseMsg);
 					Log.i("12345",json.getString("msg")+"111111");
 				
                 if(json.getString("msg").equals("success"))
                 {
                 	Log.i("12345","123456");
                     msg.what = 0;
                     Log.i("12345","1234567");
                     handler.sendMessage(msg);
                     Log.i("12345","12345678");
                  //   setSQL();
                     
                 }else
                 {
                 	Log.i("12345","123456222222222222222222222222222222");
                     msg.what = 1;
                     Log.i("12345","1234567222222222222222222222222222");
                     handler.sendMessage(msg);
                     Log.i("12345","1234567822222222222222222222222222222222");
                 }
                 
             }else
             {
                 msg.what = 2;
                 handler.sendMessage(msg);
             }
         	} catch (JSONException e) {
 				e.printStackTrace();
 			}
             
         }

     }
 	
   //Handler
     Handler handler = new Handler()
     {
         public void handleMessage(Message msg)
         {
         	Log.i("12345",msg+"");
             switch(msg.what)
             {
             case 0:
             	ss =new ArrayList<shop>();
             	try {
         		JSONArray array= new JSONObject(responseMsg).getJSONArray("ls");
         		 sharedata =getSharedPreferences("loc", 0);
            	GeoPoint locData =new GeoPoint((int)(Double.parseDouble(sharedata.getString("latitud", null))*1E6),(int)(Double.parseDouble(sharedata.getString("longitude", null))*1E6));
         		for(int i=0;i<array.length();i++){
         			JSONObject jsonObject =array.getJSONObject(i);
 						Log.i("12345",jsonObject.getInt("sid")+"");
         			Log.i("12345",jsonObject.getInt("SNow")+"");
         			Log.i("12345",jsonObject.getString("sname")+"");
         			Log.i("12345",jsonObject.getString("address")+"");
         			Log.i("12345",jsonObject.getString("sphone")+"");
         			Log.i("12345",jsonObject.getString("longtude")+"");
         			Log.i("12345",jsonObject.getString("dimensionality")+"");
         			Log.i("12345",jsonObject.getInt("type")+"");
         		   int sid = jsonObject.getInt("sid");
         		   int sNow= jsonObject.getInt("SNow");
         		   String address= jsonObject.getString("address");
         		   String sphone= jsonObject.getString("sphone");
         		   String sname= jsonObject.getString("sname");
         		   String longtude= jsonObject.getString("longtude");
         		   String dimensionality= jsonObject.getString("dimensionality");
         		   int type= jsonObject.getInt("type");
         		   String info =jsonObject.getString("info");
         		   String selectinfo =jsonObject.getString("selectInfo");
         		  JSONArray array2 = jsonObject.getJSONArray("selectInfo_2");
         		  String [] sss=new String[array2.length()];
      			for(int j=0;j<array2.length();j++){
      					Log.i("12345","jsonList!!!!"+array2.getString(j));
          				sss[j] =array2.getString(j);
      			}
         		   shop s=new shop();
         		   s.setSelectInfo_2(sss);
         		   s.setSid(sid);
         		   s.setSNow(sNow);
         		   s.setAddress(address);
         		   s.setSphone(sphone);
         		   s.setSname(sname);
         		   s.setLongtude(longtude);
         		   s.setDimensionality(dimensionality);
         		   s.setType(type);
         		   s.setInfo(info);
         		   s.setSelectInfo(selectinfo);
         			if(!s.getLongtude().equals("null")){
                   		GeoPoint loc_1 = new GeoPoint((int)(Double.parseDouble(s.getDimensionality())*1E6),(int)(Double.parseDouble(s.getLongtude())*1E6));
                   		double distance = DistanceUtil.getDistance(new GeoPoint((int)(locData.getLatitudeE6()),(int)(locData.getLongitudeE6())), loc_1);
                   	    s.setJuli((int)(distance));
                   	}
         		  ss.add(s);
         		 JuLiPaixU cs=new JuLiPaixU();
         		Collections.sort(ss,cs);
         		}
             	} catch (JSONException e) {
 					e.printStackTrace();
 				}
             	for(shop s:ss){
             		Map<String, Object> item=new HashMap<String, Object>();
              		item.put("id", 1);
              		//item.put("pic",R.drawable.ic_launcher); 
              		if(s.getType()==1){
             			item.put("pic",R.drawable.pic_index_ct); 
             		}else if(s.getType()==2){
             			item.put("pic",R.drawable.pic_index_yy); 
             		}else if(s.getType()==3){
             			item.put("pic",R.drawable.pic_index_yh); 
             		}
              		item.put("title", s.getSname());
              		item.put("content", s.getAddress());
              		if(!s.getLongtude().equals("null")){
                		if(s.getJuli()<=3000.0){
                			item.put("distance", s.getJuli()+"m");
                		}else if(s.getJuli()>=3000.0){
                			BigDecimal b= new BigDecimal(s.getJuli()/1000.0);
                			double b1=b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                			item.put("distance", b1+"km");
                		}
                	}else{
                		item.put("distance", "254km");
                	}
              		data.add(item);
     			}
             	Log.i("12345",msg+"11111");
             	//showCustomerToast(R.drawable.btn_check_buttonless_on,"搜索成功");
             	adapter.notifyDataSetChanged();
             	u.setListViewHeightBasedOnChildren(lv);
             	Log.i("12345",msg+"22222222222222");
             	progress.dismiss();
                 break;
             case 1:
            	 progress.dismiss();
                 showCustomerToast(android.R.drawable.ic_delete,"暂无数据");
                 break;
             case 2:
            	 progress.dismiss();
                 showCustomerToast(android.R.drawable.ic_delete, "网络错误,请检查网络状态");
                 break;
             
             }
             
         }
     };
     
     public boolean MyNumServer(String uphone)     //DT的连接
     {
         boolean suosouValidate = false;
         //使用apache HTTP客户端实现
         String urlStr = getURL.getUrl("FindShopMyIntentAction");
         Log.i("12345",urlStr);
         HttpPost request = new HttpPost(urlStr);
         //如果传递参数多的话，可以对传递的参数进行封装
         List<NameValuePair> params = new ArrayList<NameValuePair>();
         //添加用户名和密码
         params.add(new BasicNameValuePair("uphone",uphone));
         Log.i("12345",uphone);
         try
         {
             //设置请求参数项
         	Log.i("12345","1");
             request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
             Log.i("12345","2");
             HttpClient client = getHttpClient();
             Log.i("12345","3");
             //执行请求返回相应
             HttpResponse response = client.execute(request);
             Log.i("12345","5");
             //判断是否请求成功
             if(response.getStatusLine().getStatusCode()==200)
             {
             	Log.i("12345","4");
             	suosouValidate = true;
                 //获得响应信息
                 responseMsg = EntityUtils.toString(response.getEntity());
                 Log.i("12345",responseMsg);
             }
         }catch(Exception e)
         {
             e.printStackTrace();
         }
         return suosouValidate;
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

	
	public void onClick(View v) {
		switch (v.getId()) {
		default:
		}
	}
	
	
}
