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

import com.botb.BaseActivity;
import com.botb.activity.CustomProgressDialog;
import com.botb.activity.LoginActivity;
import com.botb.activity.R;
import com.botb.activity.util.ClassPathResource;
import com.botb.geturl.getURL;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**注册界面activity*/
public class RegisterActivity extends BaseActivity implements android.view.View.OnClickListener{
	public static final int REGION_SELECT = 1;
	private TextView tv_QQ_Server,tv_region_show,tv_top_title;
	private Button btn_title_left,btn_send_code;
	private CheckBox chk_agree;
	private EditText et_mobileNo;
	private ProgressDialog mDialog;
	private String responseMsg = "";//返回信息
	private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
	private Bundle extras;
	CustomProgressDialog progress;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		initViews();
		initEvents();
	}

	protected void initViews(){
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		extras = getIntent().getExtras();
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_send_code = (Button) findViewById(R.id.btn_send_code);
		tv_QQ_Server = (TextView) findViewById(R.id.tv_QQ_Server);
		chk_agree = (CheckBox) findViewById(R.id.chk_agree);
		et_mobileNo = (EditText) findViewById(R.id.et_mobileNo);
		//et_mobileNo.setInputType(InputType.TYPE_CLASS_PHONE); 此方法使其只输入数字,英文也变数字
	}
	protected void initEvents() {
		tv_top_title.setText("注册账号");
		btn_title_left.setOnClickListener(this);
		btn_send_code.setOnClickListener(this);
		tv_QQ_Server.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	}
	
//	/**
//	 * 重写了onCreateDialog方法来创建一个列表对话框
//	 */
//	@Override
//	protected Dialog onCreateDialog(int id, Bundle args) {
//		// TODO Auto-generated method stub
//		switch(id){
//		case REGION_SELECT:
//			final Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("请选择所在地");
//			builder.setSingleChoiceItems(//第一个参数是要显示的列表，用数组展示；第二个参数是默认选中的项的位置；
//					//第三个参数是一个事件点击监听器
//					new String[]{"+86中国大陆","+853中国澳门","+852中国香港","+886中国台湾"},
//					0, 
//					new OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							switch(which){
//							case 0:
//								tv_region_show.setText("+86中国大陆");
//								break;
//							case 1:
//								tv_region_show.setText("+853中国澳门");
//								break;
//							case 2:
//								tv_region_show.setText("+852中国香港");
//								break;
//							case 3:
//								tv_region_show.setText("+886中国台湾");
//								break;
//							}
//							dismissDialog(REGION_SELECT);//想对话框关闭
//						}
//					});
//			return builder.create();
//		}
//		return null;
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_title_left:
			RegisterActivity.this.finish();
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		case R.id.btn_send_code:
			String mobiles = et_mobileNo.getText().toString();
			if(chk_agree.isChecked()== false)//若没勾选checkbox无法后续操作
				showCustomToast("请确认是否已经阅读《排号助手服务条款》");
			if(ClassPathResource.isMobileNO(mobiles)==false)//对手机号码严格验证，参见工具类中的正则表达式
				showCustomToast("请正确填写手机号");
			if(ClassPathResource.isMobileNO(mobiles)==true&&chk_agree.isChecked()==true){
				progress = new CustomProgressDialog(RegisterActivity.this,"正在验证手机号码，请稍后...");
				progress.setCanceledOnTouchOutside(false);
				progress.show();
//				mDialog = new ProgressDialog(RegisterActivity.this);
//	            mDialog.setTitle("验证手机号码");
//	            mDialog.setMessage("正在验证手机号码，请稍后...");
//	            mDialog.show();
	            Thread RegisterCheckPhoneThread = new Thread(new RegisterCheckPhoneThread());
	            RegisterCheckPhoneThread.start();
				//当勾选中且号码正确，点击进行下一步操作
				//Toast.makeText(this, "已经向您手机发送验证码，请查看", Toast.LENGTH_LONG).show();
				
			}
		}
		
	}
	
	//RegisterCheckPhoneThread线程类
    class RegisterCheckPhoneThread implements Runnable
    {
        public void run() 
        {
        	try {
            //URL合法，但是这一步并不验证密码是否正确
            boolean RegisterCheckPhoneValidate = RegisterCheckPhoneServer(et_mobileNo.getText().toString());
            Log.i("12345","----------------------------bool is :"+RegisterCheckPhoneValidate+"----------response:"+responseMsg);
            Log.i("12345","111111");
            Message msg = handler.obtainMessage();
            Log.i("12345","222222");
            if(RegisterCheckPhoneValidate)
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
    
    public boolean RegisterCheckPhoneServer(String phone)     //DT的连接
    {
        boolean registerValidate = false;
        //使用apache HTTP客户端实现
        String urlStr = getURL.getUrl("RegisterCheckPhoneAction");
        Log.i("12345",urlStr);
        HttpPost request = new HttpPost(urlStr);
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加用户名和密码
        params.add(new BasicNameValuePair("phone",phone));
        Log.i("12345",phone);
        params.add(new BasicNameValuePair("type", extras.getInt("Text")+""));
        Log.i("12345",extras.getInt("Text")+""+"type");
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
            	showCustomerToast(R.drawable.btn_check_buttonless_on,"已经向您手机发送验证码，请查看");
                Intent intent = new Intent(RegisterActivity.this, RegisterConfirmActivity.class);
				intent.putExtra("Text2", extras.getInt("Text"));
				intent.putExtra("phone", et_mobileNo.getText().toString());
				startActivityForResult(intent, 2);
				progress.dismiss();
                break;
            case 1:
            	progress.dismiss();
                showCustomerToast(android.R.drawable.ic_delete,"手机已经注册过,请选择别的手机号");
                break;
            case 2:
            	progress.dismiss();
                showCustomerToast(android.R.drawable.ic_delete, "网络错误,请检查网络状态");
                break;
            
            }
            
        }
    };

}
