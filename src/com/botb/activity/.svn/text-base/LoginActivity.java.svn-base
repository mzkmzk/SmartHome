package com.botb.activity;

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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.botb.BaseActivity;
import com.botb.activity.register.RegisterChooseActivity;
import com.botb.geturl.getURL;
import com.botb.service.PollingService;
import com.botb.service.PollingUtils;

public class LoginActivity extends BaseActivity {
	
	// 步骤1：声明交互类组件对象
	private TextView login_about;
	private Button  btnLogin,login_register;	
	private EditText txtAccount, txtPassword;
	private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
	private String responseMsg = "";//返回信息
	private RadioGroup rg_select;
	private String type =""; //登陆类型
	// 定位相关
	LocationClient mLocClient;
	LocationData locData = null;
	CustomProgressDialog progress;
	public MyLocationListenner myListener = new MyLocationListenner();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		
		this.btnLogin = (Button) this.findViewById(R.id.login_btn_login);
		this.txtAccount = (EditText) this.findViewById(R.id.login_et_account);
		this.txtPassword = (EditText) this.findViewById(R.id.login_et_pwd);
		this.rg_select = (RadioGroup)this.findViewById(R.id.rg_select);
		this.login_register = (Button) this.findViewById(R.id.login_register);
		//this.login_about = (TextView) this.findViewById(R.id.login_about);
		
		this.btnLogin.setOnClickListener(new ViewOcl());
		this.login_register.setOnClickListener(new ViewOcl());
		//this.login_about.setOnClickListener(new ViewOcl());
		
		SharedPreferences preferences = getSharedPreferences("publicData", MODE_PRIVATE);
		String account = preferences.getString("account", "");
		this.txtAccount.setText(account);

		 //定位初始化
        mLocClient = new LocationClient( this );
        locData = new LocationData();
        mLocClient.registerLocationListener( myListener );
        
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        PollingUtils.stopPollingService(LoginActivity.this, PollingService.class, PollingService.ACTION);
	}

	private class ViewOcl implements View.OnClickListener{

		public void onClick(View v) {
			Log.i("12345",v.getId()+"");
			switch (v.getId()) {
	
			case R.id.login_btn_login:
				if(!checkForm()){
					break;
				}
				
				 progress = new CustomProgressDialog(LoginActivity.this,"正在登录中...");
				progress.setCanceledOnTouchOutside(false);
				progress.show();
				
//				mDialog = new ProgressDialog(LoginActivity.this);
//               mDialog.setTitle("登陆");
//               mDialog.setMessage("正在登陆服务器，请稍后...");
//                mDialog.show();
                Thread loginThread = new Thread(new LoginThread());
                loginThread.start();

				break;
			case R.id.login_register:
				startActivity(RegisterChooseActivity.class);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				break;
//			case R.id.login_about:
//				startActivity(Viewpager.class);
//				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
//				break;
			}
		}
	}
	
	private boolean checkForm() {
		Log.i("12345",this.txtAccount.getText().toString() );
		if (this.txtAccount.getText().toString() == null
				|| this.txtAccount.getText().toString().length() == 0) {
			showCustomerToast(android.R.drawable.ic_delete, "警告：账号不能为空");
			return false;
		}
		if (this.txtPassword.getText().toString() == null
				|| this.txtPassword.getText().toString().length() == 0) {
			showCustomerToast(android.R.drawable.ic_delete, "警告：登录密码不能为空");
			return false;
		}

		return true;
	}
	public boolean loginServer(String username, String password)     //DT的连接
    {
        boolean loginValidate = false;
        //使用apache HTTP客户端实现
        int select = rg_select.getCheckedRadioButtonId();
        String urlStr ="";
        if(select==R.id.geren){
        	urlStr = getURL.getUrl("LoginAction");
        }else{
        	urlStr = getURL.getUrl("LoginShopAction");
        }
        Log.i("12345",urlStr);
        HttpPost request = new HttpPost(urlStr);
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加用户名和密码
        params.add(new BasicNameValuePair("uphone",username));
        params.add(new BasicNameValuePair("passwd",password));
        try
        {
            //设置请求参数项
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpClient client = getHttpClient();
            //执行请求返回相应
            HttpResponse response = client.execute(request);
            //判断是否请求成功
            if(response.getStatusLine().getStatusCode()==200)
            {
                loginValidate = true;
                //获得响应信息
                responseMsg = EntityUtils.toString(response.getEntity());
                Log.i("12345",responseMsg);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return loginValidate;
    }
	
	//LoginThread线程类
    class LoginThread implements Runnable
    {
        public void run() 
        {
        	try {
            String username = txtAccount.getText().toString();
            String password = txtPassword.getText().toString();    
           Log.i("12345","username="+username+":password="+password);
            //URL合法，但是这一步并不验证密码是否正确
            boolean loginValidate = loginServer(username, password);
            Log.i("12345","----------------------------bool is :"+loginValidate+"----------response:"+responseMsg);
            Log.i("12345","111111");
            Message msg = handler.obtainMessage();
            Log.i("12345","222222");
            if(loginValidate)
            {
            	JSONObject json;
					json= new JSONObject(responseMsg);
					Log.i("12345",json.getString("msg")+"111111");
					Log.i("12345",json.getString("type")+"login type");
					type = json.getString("type");
                if(json.getString("msg").equals("success"))
                {
                    msg.what = 0;
                    handler.sendMessage(msg);
                 //   setSQL();
                }else
                {
                    msg.what = 1;
                    handler.sendMessage(msg);
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
	
    //初始化HttpClient，并设置超时
    public HttpClient getHttpClient()
    {
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
        HttpClient client = new DefaultHttpClient(httpParams);
        return client;
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
            	//System.out.println(" checkstatus1  " +checkstatus + " checkstatus2  " +checkstatus2);
         //   	if(!checkstatus2) {
            //		mDialog.cancel();
            //	}
            	Log.i("12345",msg+"11111");
            	
            	//showCustomerToast(R.drawable.btn_check_buttonless_on,"欢迎登录");
            	Log.i("12345",msg+"22222222222222");
                Editor sharedata = getSharedPreferences("data", 0).edit();  
                sharedata.putString("user",responseMsg);  
                sharedata.commit();
                //if(checkstatus2) {
                //	finish();
                //}
               // mDialog.cancel();
                progress.dismiss();
                if(type.equals("shop")){
                	 Intent intent = new Intent(LoginActivity.this,SeverActivity.class);
                     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
                     PollingUtils.stopPollingService(LoginActivity.this, PollingService.class, PollingService.ACTION);
                     startActivity(intent);
                     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                }else{
                	Intent intent = new Intent(LoginActivity.this,CommActivityGroup.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                }
                break;
            case 1:
            	//mDialog.cancel();
            	progress.dismiss();
                showCustomerToast(android.R.drawable.ic_delete,"账号或密码错误");
                break;
            case 2:
                //mDialog.cancel();
            	progress.dismiss();
                showCustomerToast(android.R.drawable.ic_delete,"网络错误,请检查网络状态");
                break;
            }
            
        }
    };
    
	protected void initViews() {
	}
	protected void initEvents() {
	}
	
	/**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            //如果不显示定位精度圈，将accuracy赋值为0即可
            locData.accuracy = location.getRadius();
            locData.direction = location.getDerect();
            Editor sharedata = getSharedPreferences("loc", 0).edit();  
            sharedata.putString("latitud",locData.latitude+"");  
            sharedata.putString("longitude",locData.longitude+"");  
            sharedata.commit();
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
    @Override
    protected void onDestroy() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
    	 */
    	//退出时销毁定位
        if (mLocClient != null)
            mLocClient.stop();
        super.onDestroy();
    }
}
