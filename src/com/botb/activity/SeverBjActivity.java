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

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.botb.BaseActivity;
import com.botb.geturl.getURL;

public class SeverBjActivity extends BaseActivity implements OnClickListener {
	
	private EditText txt_message,txt_User,txt_Address;
	private Button btn_title_left,btn_sever_submit;
	private SharedPreferences sharedata;
	private JSONObject jsonObject; //商店登陆信息
	  private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
			private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
		    private String responseMsg = "";//返回信息
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_bianji);
		initViews();
		initEvents();
		fileData();
	}

	protected void initViews() {
		btn_title_left=(Button) findViewById(R.id.btn_title_left);
		btn_sever_submit=(Button) findViewById(R.id.btn_sever_submit);
		txt_Address=(EditText) findViewById(R.id.txt_server_Address);
		txt_message=(EditText) findViewById(R.id.txt_server_message);
		txt_User=(EditText) findViewById(R.id.txt_server_User);
	}

	protected void initEvents() {
		btn_title_left.setOnClickListener(this);
		btn_sever_submit.setOnClickListener(this);
	}

	private void fileData() {
		sharedata = getSharedPreferences("data", 0);
		try {
			jsonObject =new JSONObject(sharedata.getString("user", null)).getJSONObject("s");
			txt_message.setText(jsonObject.getString("info"));
		txt_Address.setText(jsonObject.getString("address"));
		txt_User.setText(jsonObject.getString("sname"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_title_left:
			SeverBjActivity.this.finish();
			break;
		case R.id.btn_sever_submit:
			 Thread updateShop = new Thread(new UpdateShop());
			 updateShop.start();
			break;
		default:
			break;
		}
	}
	
	//UpdateShop线程类
    class UpdateShop implements Runnable
    {

        @Override
        public void run() 
        {
        	try {
                
            //URL合法，但是这一步并不验证密码是否正确
            boolean updateValidate = UpdateServer();
            Log.i("12345","----------------------------bool is :"+updateValidate+"----------response:"+responseMsg);
            Log.i("12345","111111");
            Message msg = handler.obtainMessage();
            Log.i("12345","222222");
            if(updateValidate)
            {
            	JSONObject json;
            	
					json= new JSONObject(responseMsg);
					Log.i("12345",json.getString("msg")+"111111");
				
                if(json.getString("msg").equals("success"))
                {
                    msg.what = 0;
                    handler.sendMessage(msg);
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
    
    public boolean UpdateServer()     //DT的连接
    {
        boolean registerValidate = false;
        //使用apache HTTP客户端实现
        String urlStr = getURL.getUrl("UpdateSeverDaoAction");
        Log.i("12345",urlStr);
        HttpPost request = new HttpPost(urlStr);
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加用户名和密码
        params.add(new BasicNameValuePair("sname", txt_User.getText().toString()));
        params.add(new BasicNameValuePair("address",txt_Address.getText().toString()));
        params.add(new BasicNameValuePair("info",txt_message.getText().toString()));
        try {
			jsonObject =new JSONObject(sharedata.getString("user", null)).getJSONObject("s");
			 params.add(new BasicNameValuePair("sid",jsonObject.getInt("sid")+""));
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
                Editor sharedata = getSharedPreferences("data", 0).edit();  
                sharedata.putString("user",responseMsg);  
                sharedata.commit();
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
            	//showCustomToast("保存成功！");
            	
    			SeverBjActivity.this.finish();
                break;
            case 1:
                showCustomerToast(android.R.drawable.ic_delete,"保存失败,请检查网络状态");
                break;
            case 2:
                showCustomerToast(android.R.drawable.ic_delete, "网络错误");
                break;
            
            }
            
        }
    };

}
