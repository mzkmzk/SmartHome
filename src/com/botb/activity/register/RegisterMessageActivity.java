package com.botb.activity.register;

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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.botb.activity.CustomProgressDialog;
import com.botb.activity.LoginActivity;
import com.botb.activity.R;
import com.botb.activity.LoginActivity.MyLocationListenner;
import com.botb.geturl.getURL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterMessageActivity extends Activity implements OnClickListener {

	private TextView tv_top_title;
	private Button btn_title_left,btn_Reg_submit;
	private static final String[] m={"银行","医院","热门餐厅"};
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private	LinearLayout hide_items;
	private EditText txt_Reg_Password,txt_ReReg_Password,txt_Reg_User,txt_Reg_Address,txt_Reg_message;
	private Bundle extras;
	CustomProgressDialog progress;
	private String responseMsg = "";//返回信息
	private String responseMsg_d = "";//返回信息
	private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
	// 定位相关
		LocationClient mLocClient;
		LocationData locData = null;
		public MyLocationListenner myListener = new MyLocationListenner();

		private int a =0;
		
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_message);
		initView();
	}
	
	private void initView(){
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_title.setText("注册账号");
		txt_Reg_Password=(EditText)findViewById(R.id.txt_Reg_Password);
		txt_ReReg_Password=(EditText)findViewById(R.id.txt_ReReg_Password);
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_left.setOnClickListener(this);
		btn_Reg_submit = (Button) findViewById(R.id.btn_Reg_submit);
		btn_Reg_submit.setOnClickListener(this);
		
		txt_Reg_User =(EditText)findViewById(R.id.txt_Reg_User);
		txt_Reg_Address =(EditText)findViewById(R.id.txt_Reg_Address);
		txt_Reg_message=(EditText)findViewById(R.id.txt_Reg_message);
		
		spinner = (Spinner) findViewById(R.id.txt_Reg_Spinner);
		//将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
		//设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//将adapter 添加到spinner中
		spinner.setAdapter(adapter);
		hide_items =(LinearLayout) findViewById(R.id.hide_items);
		
		
		 extras = getIntent().getExtras();
		if(extras.getInt("Text3")==1){
			hide_items.setVisibility(View.VISIBLE);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_left:
			RegisterMessageActivity.this.finish();
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

			break;
		case R.id.btn_Reg_submit:
			Log.i("12345","1234444444444444444444444");
			if(!checkForm()){
				break;
			}
			Log.i("12345","1234");

			 progress = new CustomProgressDialog(RegisterMessageActivity.this,"正在注册请稍后...");
			progress.setCanceledOnTouchOutside(false);
			progress.show();
            Thread RegisterThread = new Thread(new RegisterThread());
            RegisterThread.start();
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

		default:
			break;
		}
	}
	
	//RegisterThread线程类
    class RegisterThread implements Runnable
    {

        @Override
        public void run() 
        {
        	try {
        		String phone = extras.getString("phone");
    			String user =txt_Reg_User.getText().toString();
    			String passwd =txt_Reg_Password.getText().toString();
    			String address =txt_Reg_Address.getText().toString();
    			String type = spinner.getSelectedItem().toString();   
           Log.i("12345","phone="+phone+":type="+type);
                
            //URL合法，但是这一步并不验证密码是否正确
            boolean RegisterValidate = RegisterServer(phone, passwd,user,address,type);
            Log.i("12345","----------------------------bool is :"+RegisterValidate+"----------response:"+responseMsg);
            Log.i("12345","111111");
            Message msg = handler.obtainMessage();
            Log.i("12345","222222");
            if(RegisterValidate)
            {
            	JSONObject json;
            	
					json= new JSONObject(responseMsg);
					Log.i("12345",json.getString("msg")+"111111");
				
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
    
    public boolean RegisterServer(String phone, String passwd,String user ,String address ,String type)     //DT的连接
    {
        boolean registerValidate = false;
        //使用apache HTTP客户端实现
        String urlStr = getURL.getUrl("RegisterAction");
        Log.i("12345",urlStr);
        HttpPost request = new HttpPost(urlStr);
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加用户名和密码
        params.add(new BasicNameValuePair("phone",phone));
        Log.i("12345",phone);
        params.add(new BasicNameValuePair("passwd",passwd));
        Log.i("12345",passwd);
        params.add(new BasicNameValuePair("user", user));
        Log.i("12345",user+"user");
        params.add(new BasicNameValuePair("address", address));
        Log.i("12345",address+"address");
        params.add(new BasicNameValuePair("type", type));
        params.add(new BasicNameValuePair("info", txt_Reg_message.getText().toString()));
        Log.i("12345",type+"txt_Reg_message.getText().toString())");
        Log.i("12345",type);
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
            Log.i("12345",""+response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode()==200)
            {
            	Log.i("12345","4");
            	registerValidate = true;
                //获得响应信息
                responseMsg = EntityUtils.toString(response.getEntity());
                Log.i("12345",responseMsg);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return registerValidate;
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
            	 //定位初始化
                mLocClient = new LocationClient( RegisterMessageActivity.this );
                locData = new LocationData();
                mLocClient.registerLocationListener( myListener );
                LocationClientOption option = new LocationClientOption();
                option.setOpenGps(true);//打开gps
                option.setCoorType("bd09ll");     //设置坐标类型
                option.setScanSpan(1000);
                mLocClient.setLocOption(option);
                mLocClient.start();
               
            	showCustomerToast(R.drawable.btn_check_buttonless_on,"注册成功");
                Intent intent = new Intent(RegisterMessageActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
                startActivity(intent);
               
                progress.dismiss();
                break;
            case 1:
            	progress.dismiss();
                showCustomerToast(android.R.drawable.ic_delete,"企业名称已存在");
                break;
            case 2:
            	progress.dismiss();
                showCustomerToast(android.R.drawable.ic_delete, "网络错误,请检查网络状态");
                break;
            
            }
            
        }
    };
	private boolean checkForm() {
		Log.i("12345",this.txt_Reg_Password.getText().toString() );
		if (this.txt_Reg_Password.getText().toString() == null
				|| this.txt_Reg_Password.getText().toString().length() == 0) {
			showCustomerToast(android.R.drawable.ic_delete, "警告：登录密码不能为空");
			return false;
		}
		if (!(this.txt_ReReg_Password.getText().toString())
				.equals((this.txt_ReReg_Password.getText().toString()))) {
			showCustomerToast(android.R.drawable.ic_delete, "警告：两次密码不一致");
			return false;
		}
		if(extras.getInt("Text3")==1){
			if(this.txt_Reg_User.getText().toString() == null
					|| this.txt_Reg_User.getText().toString().length() == 0) {
				showCustomerToast(android.R.drawable.ic_delete, "警告：名称不能为空");
				return false;
			}
			if(this.txt_Reg_Address.getText().toString() == null
					|| this.txt_Reg_Address.getText().toString().length() == 0) {
				showCustomerToast(android.R.drawable.ic_delete, "警告：地址不能为空");
				return false;
			}
			if(this.txt_Reg_message.getText().toString() == null
					|| this.txt_Reg_message.getText().toString().length() == 0) {
				showCustomerToast(android.R.drawable.ic_delete, "警告：简介不能为空");
				return false;
			}
		}
		return true;
	}
	
	private void showCustomerToast(final int icon, final String message){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_customer, (ViewGroup) findViewById(R.id.toast_layout_root));
		
		ImageView toastIcon = (ImageView) layout.findViewById(R.id.toastIcon);
		toastIcon.setBackgroundResource(icon);
		
		TextView toastMessage = (TextView) layout.findViewById(R.id.toastMessage);
		toastMessage.setText(message);
		Toast toast = new Toast(getApplicationContext());
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
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
            if(a==0){
            	a++;
            	Thread thr =new Thread(new DingWeiThread());
            	thr.start();
            }
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
    
  //DingWeiThread线程类
    class DingWeiThread implements Runnable
    {

        @Override
        public void run() 
        {
        	try {
                
            boolean DingWeiValidate = DingWeiServer();
            Log.i("12345","----------------------------bool is :"+DingWeiValidate+"----------response:"+responseMsg_d);
            Log.i("12345","111111");
            Message msg = handler_1.obtainMessage();
            Log.i("12345","222222");
            if(DingWeiValidate)
            {
            	JSONObject json;
            	
					json= new JSONObject(responseMsg_d);
					Log.i("12345",json.getString("msg")+"111111");
				
                if(json.getString("msg").equals("success"))
                {
                    msg.what = 0;
                    handler_1.sendMessage(msg);
                }else
                {
                    msg.what = 1;
                    handler_1.sendMessage(msg);
                }
                
            }else
            {
                msg.what = 2;
                handler_1.sendMessage(msg);
            }
        	} catch (JSONException e) {
				e.printStackTrace();
			}
            
        }

    }
    
    public boolean DingWeiServer()     //DT的连接
    {
        boolean registerValidate = false;
        //使用apache HTTP客户端实现
        String urlStr = getURL.getUrl("DingWei_phoneAction");
        Log.i("12345",urlStr);
        HttpPost request = new HttpPost(urlStr);
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加用户名和密码
        params.add(new BasicNameValuePair("phone", extras.getString("phone") ));
        params.add(new BasicNameValuePair("longitude", locData.longitude+""));
        Log.i("12345",locData.longitude+"longitude");
        params.add(new BasicNameValuePair("dimensionality",locData.latitude+""));
        Log.i("12345",locData.latitude+""+"dimensionality");
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
            Log.i("12345",""+response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode()==200)
            {
            	Log.i("12345","4");
            	registerValidate = true;
                //获得响应信息
            	responseMsg_d = EntityUtils.toString(response.getEntity());
                Log.i("12345",responseMsg_d);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return registerValidate;
    }
	
  //Handler
    Handler handler_1 = new Handler()
    {
        public void handleMessage(Message msg)
        {
        	Log.i("12345",msg+"");
            switch(msg.what)
            {
            case 0:
            	showCustomerToast(R.drawable.btn_check_buttonless_on,"定位成功");
                break;
            case 1:
                showCustomerToast(android.R.drawable.ic_delete,"定位失败,请检查网络状态");
                break;
            case 2:
                showCustomerToast(android.R.drawable.ic_delete, "网络错误");
                break;
            }
        }
    };
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
