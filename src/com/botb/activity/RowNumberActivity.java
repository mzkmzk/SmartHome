package com.botb.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.botb.sharesdk.demo.MainActivity;
import com.botb.sharesdk.demo.ShareContentCustomizeDemo;
import com.botb.sharesdk.onekeyshare.OnekeyShare;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
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
import com.botb.BaiDuMap.MapControlDemo;
import com.botb.BaiDuMap.RoutePlanDemo;
import com.botb.Bean.shop;
import com.botb.activity.register.RegisterMessageActivity;
import com.botb.geturl.getURL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class RowNumberActivity extends BaseActivity implements OnClickListener {

	private Button btn_fanhui,btn_row_submit;
	private TextView xq_name,xq_address,xq_phone,MaxNum,mynumber_layout_jieshao;
	//private static final String[] pop={"1~2人","3~5人","5~8人","9~12人","12~16人"};
	private static  String[] pop=null;
	//private static final String[] sub={"内一科","内二科","眼科","口腔科","生殖科"};
	private static  String[] sub=null;
	private static  String[] bus=null;
	
	private Spinner spinnerpop,spinnersub,spinnerbus;
	private LinearLayout table_pop,table_sub,table_bus;
	private ArrayAdapter<String> adapter;
	CustomProgressDialog progress;
	private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
    private String responseMsg = "";//返回信息
    private String responseMsg_MaxNum = "";//返回信息MaxNum
    private shop s ;//商店
    private SharedPreferences sharedata;
    private JSONObject jsonObject;
    private int max=-1;
    private ImageView business_riv_feed_ct,business_riv_feed_yy,business_riv_feed_yh;
	private ImageButton popBtn;
	private View mPopView;
	private RelativeLayout mCanversLayout;
	private PopupWindow mPopupWindow;
	private ImageView mfenxiang,btn_map;
    
    public  final static String SER_KEY = "com.tutor.objecttran.ser"; 
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rownumber);
		initViews();
		initEvents();
		if(s.getType()==1){
			business_riv_feed_ct.setVisibility(View.VISIBLE);
		}else if(s.getType()==2){
			business_riv_feed_yy.setVisibility(View.VISIBLE);
		}else if(s.getType()==3){
			business_riv_feed_yh.setVisibility(View.VISIBLE);
		}
		
		mPopView = LayoutInflater.from(RowNumberActivity.this).inflate(R.layout.fragment_news_pop, null);
		mCanversLayout = (RelativeLayout) findViewById(R.id.rl_canvers);
		mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		
		mfenxiang = (ImageView) mPopView.findViewById(R.id.pop_fenxiang);
		mfenxiang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
//				showShare(false, "cn.sharesdk.sina.weibo.SinaWeibo@40d5f820", false);
//				showShare(true,null,false);
				showShare(false,null,false);
				
			}
		});

		btn_map = (ImageView) mPopView.findViewById(R.id.btn_map);
		btn_map.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
				//showCustomToast("地图");
				Intent intent = new Intent(RowNumberActivity.this,RoutePlanDemo.class);
				intent.putExtra("longitude", s.getLongtude());
				intent.putExtra("dimensionality", s.getDimensionality());
	            startActivity(intent);
			}
		});
		
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mCanversLayout.setVisibility(View.GONE);
			}
		});
	}
	
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_fanhui:
			RowNumberActivity.this.finish();
			break;
		case R.id.btn_row_submit:
			 progress = new CustomProgressDialog(RowNumberActivity.this,"正在努力排号中...");
			progress.setCanceledOnTouchOutside(false);
			progress.show();
            Thread RowNumberThread = new Thread(new RowNumberThread());
            RowNumberThread.start();
			break;
		case R.id.popBtn:
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E9E9E9")));
			mPopupWindow.showAsDropDown(popBtn, 0, 15);
			mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
			mPopupWindow.setFocusable(true);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.update();
			
			mCanversLayout.setVisibility(View.VISIBLE);
			break;
		/**	
		case R.id.btn_map:
			
			Intent intent = new Intent(RowNumberActivity.this,RoutePlanDemo.class);
			intent.putExtra("longitude", s.getLongtude());
			intent.putExtra("dimensionality", s.getDimensionality());
            startActivity(intent);
			break;
			*/
		default:
			break;
		}
	}
	
	//排号线程开始============================================================
	
	//RowNumberThread线程类
    class RowNumberThread implements Runnable
    {

        @Override
        public void run() 
        {
        	Log.i("12345",spinnersub.getSelectedItem().toString());
        	String type;
        	try {
        		
        		Log.i("12345",spinnersub.getSelectedItem().toString()+"2222222222222222222222222222222");
        		Log.i("12345",jsonObject.getInt("uid")+"");
        		String uphone =jsonObject.getString("uphone");
            int sid = s.getSid();
            if(s.getType()==1){
            	type  = spinnerpop.getSelectedItem().toString(); 
            }else if(s.getType()==2){
            	type =spinnersub.getSelectedItem().toString();
            }else if(s.getType()==3){
            	type = spinnerbus.getSelectedItem().toString();
            }else{
            	type ="";
            }
           Log.i("12345","sid="+sid+"uphone="+uphone +":type="+type);
                
            //URL合法，但是这一步并不验证密码是否正确
            boolean RowNumberValidate = RowNumberServer(sid, uphone,type);
            Log.i("12345","----------------------------bool is :"+RowNumberValidate+"----------response:"+responseMsg_MaxNum);
            Log.i("12345","111111");
            Message msg = handler.obtainMessage();
            Log.i("12345","222222");
            if(RowNumberValidate){
            	JSONObject json;
					json= new JSONObject(responseMsg);
					Log.i("12345",json.getString("msg")+"111111");
                if(json.getInt("msg")!=0){
                	Log.i("12345","123456");
                    msg.what = 0;
                    Log.i("12345","1234567");
                    handler.sendMessage(msg);
                    Log.i("12345","12345678");
                 //   setSQL();
                    
                }else{
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
                
            }else {
                msg.what = 2;
                handler.sendMessage(msg);
             }
        	} catch (JSONException e) {
				e.printStackTrace();
			}
            
        }

    }
    
    public boolean RowNumberServer(int sid , String uphone , String type)     //DT的连接
    {
        boolean loginValidate = false;
        //使用apache HTTP客户端实现
        String urlStr = getURL.getUrl("AccessNumberAction");
        
        Log.i("12345",urlStr);
        HttpPost request = new HttpPost(urlStr);
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加用户名和密码
        params.add(new BasicNameValuePair("sid",sid+""));
        Log.i("12345",sid+"");
        params.add(new BasicNameValuePair("uphone",uphone+""));
        Log.i("12345",uphone+"");
        params.add(new BasicNameValuePair("info",type));
        Log.i("12345",type+"");
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
            	Log.i("12345",msg+"11111");
            	showCustomerToast(R.drawable.btn_check_buttonless_on,"排号成功");
            	Log.i("12345",msg+"22222222222222");
            	progress.dismiss();
                Thread queryMaxThread = new Thread(new queryMaxThread());
	            queryMaxThread.start();
	            Intent intent = new Intent(RowNumberActivity.this,MyNumberOneActivity.class);
                Bundle  mBundel =new Bundle ();
        		mBundel.putSerializable(SER_KEY, s);
        		intent.putExtras(mBundel);
        		startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

                break;
            case 1:
            	progress.dismiss();
                showCustomerToast(android.R.drawable.ic_delete,"您已排过该企业的号,撤消后才能重排");
                break;
            case 2:
            	progress.dismiss();
                showCustomerToast(android.R.drawable.ic_delete, "网络错误,请检查网络状态");
                break;
            
            }
            
        }
    };
    
  //排号线程结束============================================================
    
    
    
  //获取MAx号线程开始============================================================
  //queryMaxThread线程类
    class queryMaxThread implements Runnable
    {
        public void run() 
        {
        	Log.i("12345",spinnersub.getSelectedItem().toString());
        	String type;
        	try {
        		int uid =jsonObject.getInt("uid");
            int sid = s.getSid();
            if(s.getType()==1){
            	type  = spinnerpop.getSelectedItem().toString(); 
            }else if(s.getType()==2){
            	type =spinnersub.getSelectedItem().toString();
            }else if(s.getType()==3){
            	type = spinnerbus.getSelectedItem().toString();
            }else{
            	type ="";
            }
           Log.i("12345","sid="+sid+"uid="+uid +":type="+type);
                
            //URL合法，但是这一步并不验证密码是否正确
            boolean queryMaxValidate = queryMaxServer(sid, uid,type);
            Log.i("12345","----------------------------bool is :"+queryMaxValidate+"----------response:"+responseMsg_MaxNum);
            Log.i("12345","111111");
            Message msg = queryMaxhandler.obtainMessage();
            Log.i("12345","222222");
            if(queryMaxValidate)
            {
            	JSONObject json;
					json= new JSONObject(responseMsg_MaxNum);
					Log.i("12345",json.getString("max")+"111111");
					max=json.getInt("max");
                if(json.getInt("max")!=-1)
                {
                    msg.what = 0;
                    queryMaxhandler.sendMessage(msg);
                }else
                {
                    msg.what = 1;
                    queryMaxhandler.sendMessage(msg);
                }
                
            }else
            {
                msg.what = 2;
                queryMaxhandler.sendMessage(msg);
            }
        	} catch (JSONException e) {
				e.printStackTrace();
			}
            
        }

    }
    
    public boolean queryMaxServer(int sid , int uid , String type)     //DT的连接
    {
        boolean loginValidate = false;
        //使用apache HTTP客户端实现
        String urlStr = getURL.getUrl("FindShopMaxIndentAction");
        Log.i("12345",urlStr);
        HttpPost request = new HttpPost(urlStr);
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sid",sid+""));
        params.add(new BasicNameValuePair("uid",uid+""));
        params.add(new BasicNameValuePair("info",type));
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
                responseMsg_MaxNum = EntityUtils.toString(response.getEntity());
                Log.i("12345",responseMsg);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return loginValidate;
    }
	
    
    //Handler
    Handler queryMaxhandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
            case 0:
            	Log.i("12345",msg+"11111");
            	//showCustomerToast(R.drawable.btn_check_buttonless_on,"加载成功");
            	Log.i("12345",msg+"22222222222222");
            	MaxNum.setText(max+"");
            	progress.dismiss();
                break;
            case 1:
            	progress.dismiss();
                showCustomerToast(android.R.drawable.ic_delete,"加载错误");
                break;
            case 2:
            	progress.dismiss();
                showCustomerToast(android.R.drawable.ic_delete, "网络错误,请检查网络状态");
                break;
            }
            
        }
    };
    
  //获取MAx号线程结束============================================================

	protected void initViews() {
		MaxNum= (TextView)findViewById(R.id.business_layout_MaxNum);
		btn_fanhui = (Button) findViewById(R.id.btn_fanhui);
		btn_fanhui.setOnClickListener(this);
		xq_name =(TextView)findViewById(R.id.business_layout_name);
		xq_address =(TextView)findViewById(R.id.business_layout_address);
		xq_phone =(TextView)findViewById(R.id.business_layout_phone);
		table_pop=(LinearLayout)findViewById(R.id.table_pop);
		table_sub=(LinearLayout)findViewById(R.id.table_sub);
		table_bus=(LinearLayout) findViewById(R.id.table_bus);
		business_riv_feed_ct=(ImageView)findViewById(R.id.business_riv_feed_ct);
		business_riv_feed_yy=(ImageView)findViewById(R.id.business_riv_feed_yy);
		business_riv_feed_yh=(ImageView)findViewById(R.id.business_riv_feed_yh);
		mynumber_layout_jieshao=(TextView)findViewById(R.id.mynumber_layout_jieshao);
		
		popBtn = (ImageButton) findViewById(R.id.popBtn);
		popBtn.setOnClickListener(this);
	}

	protected void initEvents() {
		//加载客户信息
				sharedata = getSharedPreferences("data", 0);
				try {
					jsonObject =new JSONObject(sharedata.getString("user", null)).getJSONObject("u");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				s = (shop)getIntent().getSerializableExtra(IndexGalleryActivity.SER_KEY);
				xq_name.setText(s.getSname());
				xq_address.setText(s.getAddress());
				xq_phone.setText(s.getSphone());
				mynumber_layout_jieshao.setText(s.getInfo());
				sub=s.getSelectInfo_2();
				pop=s.getSelectInfo_2();
				bus=s.getSelectInfo_2();
				btn_row_submit =(Button) findViewById(R.id.btn_row_submit);
				btn_row_submit.setOnClickListener(this);
				//热门餐厅
				spinnerpop = (Spinner) findViewById(R.id.row_pop_Spinner);
				adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pop);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerpop.setAdapter(adapter);
				spinnerpop.setOnItemSelectedListener(new OnItemSelectedListener()  {
					 public void  onItemSelected(AdapterView<?> arg0, View arg1,
				              int arg2, long arg3) {
						 progress = new CustomProgressDialog(RowNumberActivity.this,"正在努力获取数据中...");
							progress.setCanceledOnTouchOutside(false);
							progress.show();
				            Thread queryMaxThread = new Thread(new queryMaxThread());
				            queryMaxThread.start();
					 }
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				 });
				//银行
						spinnerbus = (Spinner) findViewById(R.id.row_bus_Spinner);
						adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bus);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinnerbus.setAdapter(adapter);
						spinnerbus.setOnItemSelectedListener(new OnItemSelectedListener()  {
							 public void  onItemSelected(AdapterView<?> arg0, View arg1,
						              int arg2, long arg3) {
								 progress = new CustomProgressDialog(RowNumberActivity.this,"正在努力获取数据中...");
									progress.setCanceledOnTouchOutside(false);
									progress.show();
						            Thread queryMaxThread = new Thread(new queryMaxThread());
						            queryMaxThread.start();
							 }
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
						 });
				//医院
				spinnersub = (Spinner) findViewById(R.id.row_sub_Spinner);
				adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sub);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnersub.setAdapter(adapter);
				xq_phone.setText(s.getSphone());
				spinnersub.setOnItemSelectedListener(new OnItemSelectedListener()  {
					 public void  onItemSelected(AdapterView<?> arg0, View arg1,
				              int arg2, long arg3) {
						 progress = new CustomProgressDialog(RowNumberActivity.this,"正在努力获取数据中...");
							progress.setCanceledOnTouchOutside(false);
							progress.show();
				            Thread queryMaxThread = new Thread(new queryMaxThread());
				            queryMaxThread.start();
					 }
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				 });
				Log.i("12345","type =  "+s.getType());
				if(s.getType()==1){
					table_pop.setVisibility(View.VISIBLE);
				}else if(s.getType()==2){
					table_sub.setVisibility(View.VISIBLE);
				}else if(s.getType()==3){
					table_bus.setVisibility(View.VISIBLE);
				}
	}
		
	// 使用快捷分享完成分享（请务必仔细阅读位于SDK解压目录下Docs文件夹中OnekeyShare类的JavaDoc）
			/**ShareSDK集成方法有两种</br>
			 * 1、第一种是引用方式，例如引用onekeyshare项目，onekeyshare项目再引用mainlibs库</br>
			 * 2、第二种是把onekeyshare和mainlibs集成到项目中，本例子就是用第二种方式</br>
			 * 请看“ShareSDK 使用说明文档”，SDK下载目录中 </br>
			 * 或者看网络集成文档 http://wiki.sharesdk.cn/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97
			 * 3、混淆时，把sample或者本例子的混淆代码copy过去，在proguard-project.txt文件中
			 *
			 *
			 * 平台配置信息有三种方式：
			 * 1、在我们后台配置各个微博平台的key
			 * 2、在代码中配置各个微博平台的key，http://sharesdk.cn/androidDoc/cn/sharesdk/framework/ShareSDK.html
			 * 3、在配置文件中配置，本例子里面的assets/ShareSDK.conf,
			 */
			private void showShare(boolean silent, String platform, boolean captureView) {
				final OnekeyShare oks = new OnekeyShare();
				oks.setNotification(R.drawable.ic_launcher, RowNumberActivity.this.getString(R.string.app_name));
				oks.setAddress("12345678901");
				oks.setTitle(RowNumberActivity.this.getString(R.string.evenote_title));
				oks.setTitleUrl("http://android.myapp.com/myapp/detail.htm?apkName=com.botb.activity");
				//初始文字
				oks.setText("#排号快手#"+xq_name.getText().toString()+"，这家店很不错，我很中意！");
				//图片
				/*if (captureView) {
					oks.setViewToShare(getPage());
				} else {
					oks.setImagePath(MainActivity.TEST_IMAGE);
					oks.setImageUrl(MainActivity.TEST_IMAGE_URL);
				}*/
				oks.setUrl("http://android.myapp.com/myapp/detail.htm?apkName=com.botb.activity");
				oks.setFilePath(MainActivity.TEST_IMAGE);
				oks.setComment(RowNumberActivity.this.getString(R.string.share));
				oks.setSite(RowNumberActivity.this.getString(R.string.app_name));
				oks.setSiteUrl("http://android.myapp.com/myapp/detail.htm?apkName=com.botb.activity");
				oks.setVenueName("排号快手");
				oks.setVenueDescription("This is a beautiful place!");
//				oks.setLatitude(23.056081f);
//				oks.setLongitude(113.385708f);
				oks.setSilent(silent);
				if (platform != null) {
					oks.setPlatform(platform);
				}

				// 令编辑页面显示为Dialog模式
				oks.setDialogMode();

				// 在自动授权时可以禁用SSO方式
				oks.disableSSOWhenAuthorize();

				// 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
//				oks.setCallback(new OneKeyShareCallback());
				oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

				// 去除注释，演示在九宫格设置自定义的图标
//				Bitmap logo = BitmapFactory.decodeResource(menu.getResources(), R.drawable.ic_launcher);
//				String label = menu.getResources().getString(R.string.app_name);
//				OnClickListener listener = new OnClickListener() {
//					public void onClick(View v) {
//						String text = "Customer Logo -- ShareSDK " + ShareSDK.getSDKVersionName();
//						Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
//						oks.finish();
//					}
//				};
//				oks.setCustomerLogo(logo, label, listener);

				// 去除注释，则快捷分享九宫格中将隐藏新浪微博和腾讯微博
//				oks.addHiddenPlatform(SinaWeibo.NAME);
//				oks.addHiddenPlatform(TencentWeibo.NAME);

				// 为EditPage设置一个背景的View
//				oks.setEditPageBackground(getPage());

				oks.show(RowNumberActivity.this);
			}

			public void onComplete(Platform plat, int action,
					HashMap<String, Object> res) {

				Message msg = new Message();
				msg.arg1 = 1;
				msg.arg2 = action;
				msg.obj = plat;
				UIHandler.sendMessage(msg, (Callback) this);
			}

			public void onCancel(Platform palt, int action) {
				Message msg = new Message();
				msg.arg1 = 3;
				msg.arg2 = action;
				msg.obj = palt;
				UIHandler.sendMessage(msg, (Callback) this);
			}

			public void onError(Platform palt, int action, Throwable t) {
				t.printStackTrace();

				Message msg = new Message();
				msg.arg1 = 2;
				msg.arg2 = action;
				msg.obj = palt;
				UIHandler.sendMessage(msg, (Callback) this);
			}

			public boolean handleMessage(Message msg) {
				Platform plat = (Platform) msg.obj;
				String text = MainActivity.actionToString(msg.arg2);
				switch (msg.arg1) {
					case 1: {
						// 成功
						text = plat.getName() + " completed at " + text;
					}
					break;
					case 2: {
						// 失败
						text = plat.getName() + " caught error at " + text;
					}
					break;
					case 3: {
						// 取消
						text = plat.getName() + " canceled at " + text;
					}
					break;
				}

				Toast.makeText(RowNumberActivity.this, text, Toast.LENGTH_SHORT).show();
				return false;
			}
}
