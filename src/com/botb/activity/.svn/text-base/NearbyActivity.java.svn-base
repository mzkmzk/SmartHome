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
import com.botb.activity.MyNumberActivity.MyNumThread;
import com.botb.activity.util.Utility;
import com.botb.geturl.getURL;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class NearbyActivity extends BaseActivity {
	
	public  final static String SER_KEY = "com.tutor.objecttran.ser"; 
	CustomProgressDialog progress;

	private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
	private String responseMsg = "";//返回信息
	
	private  Utility u;;
	private SharedPreferences sharedata;
	private JSONObject jsonObject;
	private String uphone;
	//热门餐厅
	public SimpleAdapter adapter;
	private List<shop> ss; //搜索得到的商店信息
	//集合用于存储数据；
	List<Map<String, Object>> data;
	ListView lv;
	//医院
	public SimpleAdapter adapter_yy;
	private List<shop> ss_yy; //搜索得到的商店信息
	//集合用于存储数据；
	List<Map<String, Object>> data_yy;
	ListView lv_yy;
	//银行
	public SimpleAdapter adapter_yh;
	private List<shop> ss_yh; //搜索得到的商店信息
	//集合用于存储数据；
	List<Map<String, Object>> data_yh;
	ListView lv_yh;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearbytabs);
        
        View tab1 = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);  
        TextView text1 = (TextView) tab1.findViewById(R.id.tab_label);  
        text1.setText("热门餐厅");  
          
        View tab2 = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);  
        TextView text2 = (TextView) tab2.findViewById(R.id.tab_label);  
        text2.setText("银行");  
          
        View tab3 = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);  
        TextView text3 = (TextView) tab3.findViewById(R.id.tab_label);  
        text3.setText("医院"); 
        
        //不继承TabActivity的第二种方法
        TabHost tabhost=(TabHost)findViewById(android.R.id.tabhost);
        tabhost.setup();
        TabWidget tabWidget=(TabWidget) findViewById(android.R.id.tabs);
        
        
        
        //继承tabActivity的第一种方法
        //获取TabHost组件
        //TabHost tabhost=getTabHost();
        //创建第一个Tab页
        //.setContent(new Intent(this, FirstActivity.class)));
        //添加第一个标签页
        tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator(tab1).setContent(R.id.tab1));
        tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator(tab2).setContent(R.id.tab2));
        tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator(tab3).setContent(R.id.tab3));
        
        tabhost.setCurrentTab(0); 
        
        lv=(InnerListView)findViewById(R.id.lv_near_tab1);
        lv_yy=(InnerListView)findViewById(R.id.lv_near_tab3);
        lv_yh=(InnerListView)findViewById(R.id.lv_near_tab2);
      //2.屏幕滚动备用方案
        /**
        u=new Utility();
        for (int i =0; i <tabWidget.getChildCount(); i++) {
        	
             //设置高度、宽度，不过宽度由于设置为fill_parent，在此对它没效果
             
            //tabWidget.getChildAt(i).getLayoutParams().height = 60;
            //tabWidget.getChildAt(i).getLayoutParams().width = 45;
	         // 设置tab中标题文字的颜色，不然默认为黑色
	         
	         final TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
	         tv.setPadding(0, 0, 0, 20);
	         tv.setTextColor(this.getResources().getColorStateList(android.R.color.background_light));
        }
	*/
        fillDate();
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
    	 data_yy= new ArrayList<Map<String,Object>>();
    	 data_yh= new ArrayList<Map<String,Object>>();
		 progress = new CustomProgressDialog(NearbyActivity.this,"正在获取数据中...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
          Thread myNumThread = new Thread(new SousuoThread());
          myNumThread.start();
          adapter =new SimpleAdapter(getApplicationContext(), data, R.layout.activity_lv, new String[]{"id","pic","title","content","distance"}, new int[]{R.id.lv_id,R.id.lv_pic,R.id.lv_title,R.id.lv_content,R.id.lv_distance});
          adapter_yy =new SimpleAdapter(getApplicationContext(), data_yy, R.layout.activity_lv, new String[]{"id","pic","title","content","distance"}, new int[]{R.id.lv_id,R.id.lv_pic,R.id.lv_title,R.id.lv_content,R.id.lv_distance});
          adapter_yh =new SimpleAdapter(getApplicationContext(), data_yh, R.layout.activity_lv, new String[]{"id","pic","title","content","distance"}, new int[]{R.id.lv_id,R.id.lv_pic,R.id.lv_title,R.id.lv_content,R.id.lv_distance});
        	//把适配器绑定给ListView
          lv.setAdapter(adapter);
          lv.setOnItemClickListener(new OnItemClickListener() {
         	   
              public void onItemClick(AdapterView<?> arg0, View arg1,
                      int position, long arg3) {
             	
  				Intent intent = new Intent(NearbyActivity.this,RowNumberActivity.class);
  				Bundle  mBundel =new Bundle ();
  				mBundel.putSerializable(SER_KEY, ss.get(position));
  				intent.putExtras(mBundel);
  				startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

              }
          });
          
          lv_yy.setAdapter(adapter_yy);
          lv_yy.setOnItemClickListener(new OnItemClickListener() {
         	   
              public void onItemClick(AdapterView<?> arg0, View arg1,
                      int position, long arg3) {
             	
  				Intent intent = new Intent(NearbyActivity.this,RowNumberActivity.class);
  				Bundle  mBundel =new Bundle ();
  				mBundel.putSerializable(SER_KEY, ss_yy.get(position));
  				intent.putExtras(mBundel);
  				startActivity(intent);
              }
          });
          
          lv_yh.setAdapter(adapter_yh);
          lv_yh.setOnItemClickListener(new OnItemClickListener() {
         	   
              public void onItemClick(AdapterView<?> arg0, View arg1,
                      int position, long arg3) {
             	
  				Intent intent = new Intent(NearbyActivity.this,RowNumberActivity.class);
  				Bundle  mBundel =new Bundle ();
  				mBundel.putSerializable(SER_KEY, ss_yh.get(position));
  				intent.putExtras(mBundel);
  				startActivity(intent);
              }
          });
	}
	//SuosouThread线程类
    class SousuoThread implements Runnable
    {

        public void run() 
        {
        	try {
            //String info = search_text.getText().toString();
        		String info = "";
           Log.i("12345","info="+info);
                
            //URL合法，但是这一步并不验证密码是否正确
            boolean SuosouValidate = SuosouServer(info);
            Log.i("12345","----------------------------bool is :"+SuosouValidate+"----------response:"+responseMsg);
            Log.i("12345","111111");
            Message msg = handler.obtainMessage();
            Log.i("12345","222222");
            if(SuosouValidate)
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
            	ss_yy =new ArrayList<shop>();
            	ss_yh =new ArrayList<shop>();
            	try {
        		JSONArray array= new JSONObject(responseMsg).getJSONArray("ls");
        		 sharedata =getSharedPreferences("loc", 0);
         		//Toast.makeText(IndexGalleryActivity.this, sharedata.getString("latitud", "1234"), 1).show();
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
        			Log.i("12345",jsonObject.getJSONArray("selectInfo_2")+"");
        			JSONArray array2 = jsonObject.getJSONArray("selectInfo_2");
        			String [] sss=new String[array2.length()];
        			for(int j=0;j<array2.length();j++){
        					Log.i("12345","jsonList!!!!"+array2.getString(j));
            				sss[j] =array2.getString(j);
        			}
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
               	if(s.getType()==1){
               		ss.add(s);
               	}else if(s.getType()==2){
               		ss_yy.add(s);
               	}else if(s.getType()==3){
               		ss_yh.add(s);
               	}
        		}
        		JuLiPaixU cs=new JuLiPaixU();
        		Collections.sort(ss,cs);
        		Collections.sort(ss_yh,cs);
        		Collections.sort(ss_yy,cs);
            	} catch (JSONException e) {
					e.printStackTrace();
				}
            	//餐厅
            	for(shop s:ss){
            		Map<String, Object> item=new HashMap<String, Object>();
             		item.put("id", 1);
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
            	//医院
            	for(shop s:ss_yy){
            		Map<String, Object> item=new HashMap<String, Object>();
             		item.put("id", 1);
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
             		data_yy.add(item);
    			}
            	//银行
            	for(shop s:ss_yh){
            		Map<String, Object> item=new HashMap<String, Object>();
             		item.put("id", 1);
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
             		data_yh.add(item);
    			}
            	Log.i("12345",msg+"11111");
            	adapter.notifyDataSetChanged();
            	adapter_yy.notifyDataSetChanged();
            	adapter_yh.notifyDataSetChanged();
//3。使页面滚动
u.setListViewHeightBasedOnChildren(lv);
u.setListViewHeightBasedOnChildren(lv_yy);
u.setListViewHeightBasedOnChildren(lv_yh);
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
    
    public boolean SuosouServer(String info)     //DT的连接
    {
        boolean suosouValidate = false;
        //使用apache HTTP客户端实现
        String urlStr = getURL.getUrl("FindShopAction");
        
        Log.i("12345",urlStr);
        HttpPost request = new HttpPost(urlStr);
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加用户名和密码
        params.add(new BasicNameValuePair("sinfo",info));
        params.add(new BasicNameValuePair("type", 0+""));
        Log.i("12345",info);
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


	protected void initViews() {
		
	}

	protected void initEvents() {
		
	}
}
