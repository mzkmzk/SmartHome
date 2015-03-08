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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.botb.Bean.shop;
import com.botb.Util.JuLiPaixU;
import com.botb.activity.ShakeListener.OnShakeListener;
import com.botb.geturl.getURL;

public class ShakeActivity extends Activity{
	
	ShakeListener mShakeListener = null;
	Vibrator mVibrator;
	private RelativeLayout mImgUp;
	private RelativeLayout mImgDn;
	private RelativeLayout mTitle;
	
	private SlidingDrawer mDrawer;
	private Button mDrawerBtn;
	private ProgressDialog mDialog;
	private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
	private String responseMsg = "";//返回信息
	private List<shop> ss; //搜索得到的商店信息
	public  final static String SER_KEY = "com.tutor.objecttran.ser"; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_yaoyiyao);
		//drawerSet ();//设置  drawer监听    切换 按钮的方向
		
		mVibrator = (Vibrator)getApplication().getSystemService(VIBRATOR_SERVICE);
		
		mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
		mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);
		//mTitle = (RelativeLayout) findViewById(R.id.shake_title_bar);
		
//		mDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
//        mDrawerBtn = (Button) findViewById(R.id.handle);
//        
//        mDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener(){	
//        	public void onDrawerOpened(){	
//				getResources().getDrawable(R.drawable.shake_report_dragger_down);
//				final TranslateAnimation titleup = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1.0f);
//				titleup.setDuration(200);
//				titleup.setFillAfter(true);
//				mTitle.startAnimation(titleup);
//			}
//		});
//		 /* 设定SlidingDrawer被关闭的事件处理 */
//		mDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener()
//		{	public void onDrawerClosed()
//			{	
//				getResources().getDrawable(R.drawable.shake_report_dragger_up);
//				final TranslateAnimation titledn = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1.0f,Animation.RELATIVE_TO_SELF,0f);
//				titledn.setDuration(200);
//				titledn.setFillAfter(false);
//				mTitle.startAnimation(titledn);
//			}
//		});
//		
		mShakeListener = new ShakeListener(this);
        mShakeListener.setOnShakeListener(new OnShakeListener() {
			public void onShake() {
				//Toast.makeText(getApplicationContext(), "抱歉，暂时没有找到在同一时刻摇一摇的人。\n再试一次吧！", Toast.LENGTH_SHORT).show();
				startAnim();  //开始 摇一摇手掌动画
				mShakeListener.stop();
				startVibrato(); //开始 震动
				new Handler().postDelayed(new Runnable(){
					@Override
					public void run(){
						//Toast.makeText(getApplicationContext(), "抱歉，暂时没有找到\n在同一时刻摇一摇的人。\n再试一次吧！", 500).setGravity(Gravity.CENTER,0,0).show();
						/*Toast mtoast;
						mtoast = Toast.makeText(getApplicationContext(),
							     "抱歉，暂时没有找到\n在同一时刻摇一摇的人。\n再试一次吧！", 10);
							   //mtoast.setGravity(Gravity.CENTER, 0, 0);
							   mtoast.show();*/
							   mVibrator.cancel();
							   mShakeListener.start();
							   Thread suosouThread = new Thread(new SousuoThread());
							   suosouThread.start();
					}
				}, 2000);
			}
		});
   }
	public void startAnim () {   //定义摇一摇动画动画
		AnimationSet animup = new AnimationSet(true);
		TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-0.5f);
		mytranslateanimup0.setDuration(1000);
		TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+0.5f);
		mytranslateanimup1.setDuration(1000);
		mytranslateanimup1.setStartOffset(1000);
		animup.addAnimation(mytranslateanimup0);
		animup.addAnimation(mytranslateanimup1);
		mImgUp.startAnimation(animup);
		
		AnimationSet animdn = new AnimationSet(true);
		TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+0.5f);
		mytranslateanimdn0.setDuration(1000);
		TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-0.5f);
		mytranslateanimdn1.setDuration(1000);
		mytranslateanimdn1.setStartOffset(1000);
		animdn.addAnimation(mytranslateanimdn0);
		animdn.addAnimation(mytranslateanimdn1);
		mImgDn.startAnimation(animdn);	
	}
	public void startVibrato(){		//定义震动
		mVibrator.vibrate( new long[]{500,200,500,200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
	}
	public void linshi(View v) {     //标题栏
		startAnim();
		 mVibrator.cancel();
		   mShakeListener.start();
		   Thread suosouThread = new Thread(new SousuoThread());
		   suosouThread.start();
      } 
	
	
	protected void onDestroy() {
		super.onDestroy();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}
	
	//SuosouThread线程类
    class SousuoThread implements Runnable
    {

        public void run() 
        {
        	try {
                
            //URL合法，但是这一步并不验证密码是否正确
            boolean SuosouValidate = SuosouServer();
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
            	try {
        		JSONArray array= new JSONObject(responseMsg).getJSONArray("ls");
        		for(int i=0;i<array.length();i++){
        			JSONObject jsonObject =array.getJSONObject(i);
						Log.i("12345",jsonObject.getInt("sid")+"");
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
               	}
        		  ss.add(s);
        		}
        		JuLiPaixU cs=new JuLiPaixU();
        		Collections.sort(ss,cs);
            	} catch (JSONException e) {
					e.printStackTrace();
				}
            	int i;
            	if(ss.size()>=10){
            		i=(int)(Math.random()*9);//产生0-9的双精度随机数　
            	}else {
            		i=(int)(Math.random()*(ss.size()-1));
            	}
            	
            	Intent intent = new Intent(ShakeActivity.this,RowNumberActivity.class);
 				Bundle  mBundel =new Bundle ();
 				mBundel.putSerializable(SER_KEY, ss.get(i));
 				intent.putExtras(mBundel);
 				startActivity(intent);
            	Log.i("12345",msg+"11111");
            	Log.i("12345",msg+"22222222222222");
              
                break;
            }
            
        }
    };
    
    public boolean SuosouServer()     //DT的连接
    {
        boolean suosouValidate = false;
        //使用apache HTTP客户端实现
        String urlStr = getURL.getUrl("FindShopAction");
        
        Log.i("12345",urlStr);
        HttpPost request = new HttpPost(urlStr);
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加用户名和密码
        params.add(new BasicNameValuePair("sinfo", ""));
        params.add(new BasicNameValuePair("type", 1+""));
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
    
	
}