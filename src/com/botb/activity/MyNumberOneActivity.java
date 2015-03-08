package com.botb.activity;

import java.util.ArrayList;
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

import com.botb.BaseActivity;
import com.botb.Bean.indent;
import com.botb.Bean.shop;
import com.botb.activity.RowNumberActivity.queryMaxThread;
import com.botb.activity.SeverActivity.DataThread;
import com.botb.activity.SeverActivity.addKemuThread;
import com.botb.activity.SeverActivity.deleteKemuThread;
import com.botb.geturl.getURL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyNumberOneActivity extends BaseActivity implements OnClickListener{
	
	private TextView tv_top_title,mynumber_layout_name,mynumber_layout_address,mynumber_layout_difference,mynumber_layout_spinner,myumber_layout_rownum;
	private Button btn_title_left,btn_Reg_submit;
	private ImageButton btn_title_right;
	 private SharedPreferences sharedata;
	    private JSONObject jsonObject;
	    private shop s ;//商店
	    CustomProgressDialog progress;
		private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
		private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
	    private String responseMsg = "";//返回信息
	    private String uphone ;
	    private indent i ;
	    private String responseMsg_delIntent = "";//返回信息
	    private ImageView mynumber_riv_feed_ct,mynumber_riv_feed_yy,mynumber_riv_feed_yh;
	    private RelativeLayout mynumber_layout_feed;
	    public  final static String SER_KEY = "com.tutor.objecttran.ser"; 

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mynumber);
		initViews();
		initEvents();
		fillDate();
		if(s.getType()==1){
			mynumber_riv_feed_ct.setVisibility(View.VISIBLE);
		}else if(s.getType()==2){
			mynumber_riv_feed_yy.setVisibility(View.VISIBLE);
		}else if(s.getType()==3){
			mynumber_riv_feed_yh.setVisibility(View.VISIBLE);
		}
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
		 s = (shop)getIntent().getSerializableExtra(IndexGalleryActivity.SER_KEY);
		 mynumber_layout_name.setText(s.getSname());
		 mynumber_layout_address.setText(s.getAddress());
		 progress = new CustomProgressDialog(MyNumberOneActivity.this,"正在获取数据中...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
         Thread QueryIndentThread = new Thread(new DataThread());
         QueryIndentThread.start();
		
	}

	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_title_left:
			MyNumberOneActivity.this.finish();
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

			break;
		case R.id.btn_title_right:
			 progress = new CustomProgressDialog(MyNumberOneActivity.this,"正在获取数据中...");
				progress.setCanceledOnTouchOutside(false);
				progress.show();
	         Thread QueryIndentThread = new Thread(new DataThread());
	         QueryIndentThread.start();
             overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

			break;
		case R.id.btn_Reg_submit:
			 AlertDialog.Builder builder = new Builder(MyNumberOneActivity.this);
	         builder.setMessage("确定要删除吗?");
	         builder.setTitle("删除号码");
	         builder.setPositiveButton("确认",
	                 new android.content.DialogInterface.OnClickListener() {
	                     @Override
	                     public void onClick(DialogInterface dialog, int which) {
	                		 progress = new CustomProgressDialog(MyNumberOneActivity.this,"正在删除号码中...");
	                			progress.setCanceledOnTouchOutside(false);
	                			progress.show();
	                    	 Thread deleteKemuThread =new Thread(new deleteIntentThread ());
		                       deleteKemuThread.start();
	                         dialog.dismiss();
	                         
	                     }
	                 });
	         builder.setNegativeButton("取消",
	                 new android.content.DialogInterface.OnClickListener() {
	                     @Override
	                     public void onClick(DialogInterface dialog, int which) {
	                         dialog.dismiss();
	                     }
	                 });
	         builder.create().show();
	         break;
		case R.id.mynumber_layout_feed:
			Intent intent = new Intent(MyNumberOneActivity.this,RowNumberActivity.class);
				Bundle  mBundel =new Bundle ();
				mBundel.putSerializable(SER_KEY, s);
				intent.putExtras(mBundel);
				startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

	        break;
		default:
			break;
		}
		
	}

	protected void initViews() {
		mynumber_riv_feed_ct=(ImageView)findViewById(R.id.mynumber_riv_feed_ct);
		mynumber_riv_feed_yy=(ImageView)findViewById(R.id.mynumber_riv_feed_yy);
		mynumber_riv_feed_yh=(ImageView)findViewById(R.id.mynumber_riv_feed_yh);
		mynumber_layout_name=(TextView)findViewById(R.id.mynumber_layout_name);
		mynumber_layout_address=(TextView)findViewById(R.id.mynumber_layout_address);
		mynumber_layout_difference=(TextView)findViewById(R.id.mynumber_layout_difference);
	    mynumber_layout_spinner=(TextView)findViewById(R.id.mynumber_layout_spinner);
	    myumber_layout_rownum =(TextView) findViewById(R.id.mynumber_layout_rownum);
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_right=(ImageButton) findViewById(R.id.btn_title_right);
		btn_Reg_submit=(Button) findViewById(R.id.btn_Reg_submit);
		tv_top_title =(TextView) findViewById(R.id.tv_top_title);
		tv_top_title.setText("详情");
		mynumber_layout_feed=(RelativeLayout)findViewById(R.id.mynumber_layout_feed);
	}

	protected void initEvents() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		btn_Reg_submit.setOnClickListener(this);
		mynumber_layout_feed.setOnClickListener(this);
	}
	
	
	// DataThread线程类  获取indent=============================================================================
    class  DataThread implements Runnable
    {

        @Override
        public void run() 
        {
        	try {
        		
            //URL合法，但是这一步并不验证密码是否正确
        		int sid =s.getSid();
            boolean dataValidate = dataServer(sid,uphone);
            Log.i("12345","----------------------------bool is :"+dataValidate+"----------response:"+responseMsg);
            Message msg = handler.obtainMessage();
            if(dataValidate)
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
	
  //Handler
    Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
        	Log.i("12345",msg+"");
            switch(msg.what)
            {
            case 0:
            	try {
            		i=new indent();
            		JSONObject indent_i= new JSONObject(responseMsg).getJSONObject("i");
        		 i.setIid(indent_i.getInt("iid"));
        		  i.setSid(indent_i.getInt("sid"));
        		  i.setUphone(indent_i.getString("uphone"));
        		  i.setInfo(indent_i.getString("info"));
        		  i.setIno(indent_i.getInt("ino"));
        		  i.setDengdai(indent_i.getInt("dengdai"));
        		  Log.i("12345",i.getDengdai()+"");
        		  Log.i("12345",i.getInfo());
        		  mynumber_layout_difference.setText(i.getDengdai()+"");
        		  mynumber_layout_spinner.setText(i.getInfo());
        		  myumber_layout_rownum.setText(i.getIno()+"");
            	} catch (JSONException e) {
					e.printStackTrace();
				}
            	Log.i("12345",msg+"11111");
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
    
    public boolean dataServer(int sid ,String uphone)     //DT的连接
    {
        boolean suosouValidate = false;
        //使用apache HTTP客户端实现
        String urlStr = getURL.getUrl("IndentNumAction");
        
        Log.i("12345",urlStr);
        HttpPost request = new HttpPost(urlStr);
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加用户名和密码
        params.add(new BasicNameValuePair("sid",sid+""));
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
 //获取indent结束================================================================================
    
    
    //删除Intent开始============================================================================
	 // addKemuThread线程类
	    class  deleteIntentThread implements Runnable
	    {
	        @Override
	        public void run() 
	        {
	        	try {
     	                
	            //URL合法，但是这一步并不验证密码是否正确
	            boolean deleteIntentValidate = deleteIntentServer(i);
	            Log.i("12345","----------------------------bool is :"+deleteIntentValidate+"----------response:"+responseMsg_delIntent);
	            Message msg = handler_deleteIntent.obtainMessage();
	            if(deleteIntentValidate)
	            {
	            	JSONObject json;
						json= new JSONObject(responseMsg_delIntent);
						Log.i("12345",json.getString("msg")+"111111");
					
	                if(json.getInt("msg")==1)
	                {
	                    msg.what = 0;
	                    handler_deleteIntent.sendMessage(msg);
	                }else
	                {
	                    msg.what = 1;
	                    handler_deleteIntent.sendMessage(msg);
	                }
	                
	            }else
	            {
	                msg.what = 2;
	                handler_deleteIntent.sendMessage(msg);
	            }
	        	} catch (JSONException e) {
					e.printStackTrace();
				}
	            
	        }

	    }
		
	  //Handler
	    Handler handler_deleteIntent = new Handler()
	    {
	        public void handleMessage(Message msg)
	        {
	        	Log.i("12345",msg+"");
	            switch(msg.what)
	            {
	            case 0:
	            	progress.dismiss();
	            	 //showCustomerToast(R.drawable.btn_check_buttonless_on,"删除成功");
	            	 MyNumberOneActivity.this.finish();
	            	 break;
	            case 1:
	            	progress.dismiss();
	                showCustomerToast(android.R.drawable.ic_delete,"删除失败");
	                break;
	            case 2:
	            	progress.dismiss();
	                showCustomerToast(android.R.drawable.ic_delete, "网络错误,请检查网络状态");
	                break;
	            
	            }
	            
	        }
	    };
	    
	    public boolean deleteIntentServer(indent di)     //DT的连接
	    {
	        boolean kemuaddValidate = false;
	        //使用apache HTTP客户端实现
	        String urlStr = getURL.getUrl("DeleteNumberAction");
	        
	        Log.i("12345",urlStr);
	        HttpPost request = new HttpPost(urlStr);
	        //如果传递参数多的话，可以对传递的参数进行封装
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        //添加用户名和密码
	        params.add(new BasicNameValuePair("iid",di.getIid()+""));
	        params.add(new BasicNameValuePair("sid",di.getSid()+""));
	        params.add(new BasicNameValuePair("uphone",di.getUphone()+""));
	        params.add(new BasicNameValuePair("info",di.getInfo()));
	        params.add(new BasicNameValuePair("ino",di.getIno()+""));
	        Log.i("12345","2222222222222222222222++++++++++++"+di.getIno());
	        
	        Log.i("12345",di.getIid()+"");
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
	            	kemuaddValidate = true;
	                //获得响应信息
	            	responseMsg_delIntent = EntityUtils.toString(response.getEntity());
	                Log.i("12345",responseMsg_delIntent);
	            }
	        }catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	        return kemuaddValidate;
	    }
	 
	    //删除Intent结束============================================================================
 
}
