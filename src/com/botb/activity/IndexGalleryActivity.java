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
import com.botb.activity.register.RegisterChooseActivity;
import com.botb.activity.util.Utility;
import com.botb.geturl.getURL;
import com.botb.service.PollingService;
import com.botb.service.PollingUtils;

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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class IndexGalleryActivity extends BaseActivity implements OnClickListener{
	
	private Button search_dialog_qb,search_dialog_ct,search_dialog_yh,search_dialog_yy;
    private EditText search_text;
    private ImageView index_search_sou;
    //private ImageButton right_btn;
    private RelativeLayout  search_spinner;
    public  final static String SER_KEY = "com.tutor.objecttran.ser"; 
    CustomProgressDialog progress;
	private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
	private String responseMsg = "";//返回信息
	public SimpleAdapter adapter;
	public ArrayAdapter<String> adapter2;
	private List<shop> ss; //搜索得到的商店信息
    private  Utility u;;
    private SharedPreferences sharedata;
    private int right_btn_num=0;
	//集合用于存储数据；
	List<Map<String, Object>> data;
	InnerListView lv;
    /** Called when the activity is first created. */
	public static int suosouLeiXing=0;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gallery);
        search_text=(EditText)findViewById(R.id.index_search_ev);
        initViews();
        initEvents();
//2.屏幕滚动备用方案
u=new Utility();
        //获取ListView;
        this.lv=(InnerListView) this.findViewById(R.id.lv);
        
        //调用填充数据的方法；
      	//fillDate();
        data= new ArrayList<Map<String,Object>>();
        //创建适配器
        
        //监听框输入时模拟键盘的enter
//        search_text.setOnEditorActionListener(new OnEditorActionListener() {
//			
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
// 				data= new ArrayList<Map<String,Object>>();
// 				mDialog = new ProgressDialog(IndexGalleryActivity.this);
//                 mDialog.setTitle("搜索");
//                 mDialog.setMessage("正在获取，请稍后...");
//                 mDialog.show();
//                 Thread SousuoThread = new Thread(new SousuoThread());
//                 SousuoThread.start();
//                 adapter =new SimpleAdapter(getApplicationContext(), data, R.layout.activity_lv, new String[]{"id","pic","title","content","distance"}, new int[]{R.id.lv_id,R.id.lv_pic,R.id.lv_title,R.id.lv_content,R.id.lv_distance});
//               	//把适配器绑定给ListView
//                 lv.setAdapter(adapter);
//                
//                 lv.setOnItemClickListener(new OnItemClickListener() {
//                	   
//                     public void onItemClick(AdapterView<?> arg0, View arg1,
//                             int position, long arg3) {
//                    	 SharedPreferences sharedata = getSharedPreferences("data", 0);
//         				Intent intent = new Intent(IndexGalleryActivity.this,RowNumberActivity.class);
//         				Bundle  mBundel =new Bundle ();
//         				mBundel.putSerializable(SER_KEY, ss.get(position));
//         				intent.putExtras(mBundel);
//         				startActivity(intent);
//                     }
//                 });
//				return false;
//			}
//		});
        
        suosouLeiXing=0;
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	return super.onCreateOptionsMenu(menu);
    }
    
  //SuosouThread线程类
    class SousuoThread implements Runnable
    {

        public void run() 
        {
        	try {
            String info = search_text.getText().toString();
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
            	//System.out.println(" checkstatus1  " +checkstatus + " checkstatus2  " +checkstatus2);
         //   	if(!checkstatus2) {
            //		mDialog.cancel();
            //	}
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
        		  ss.add(s);
        		}
        		JuLiPaixU cs=new JuLiPaixU();
        		Collections.sort(ss,cs);
            	} catch (JSONException e) {
					e.printStackTrace();
				}
            	
            	
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
                			item.put("distance", s.getJuli()/1000+"km");
                		}
                	}else{
                		item.put("distance", "254km");
                	}
                		
                	
             		
             		data.add(item);
    			}
            	Log.i("12345",msg+"11111");
            	adapter.notifyDataSetChanged();
//3。使页面滚动
u.setListViewHeightBasedOnChildren(lv);
            	Log.i("12345",msg+"22222222222222");
                //if(checkstatus2) {
                //	finish();
                //}
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
        params.add(new BasicNameValuePair("type", suosouLeiXing+""));
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
    
    
 // 填充数据;
 	private void fillDate(){
 	//	data= new ArrayList<Map<String,Object>>();
 		
 	}
 	 protected void dialog() {
         AlertDialog.Builder builder = new Builder(IndexGalleryActivity.this);
         builder.setMessage("确定要退出吗?");
         builder.setTitle("提示");
         builder.setPositiveButton("确认",
                 new android.content.DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                         PollingUtils.stopPollingService(IndexGalleryActivity.this, PollingService.class, PollingService.ACTION);
                     }
                 });
         builder.setNegativeButton("取消",
                 new android.content.DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                     }
                 });
         builder.create().show();
     }
  
     /**
      * 监听返回键事件
      */
     @Override
     public boolean onKeyDown(int keyCode,KeyEvent event){
         if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
       //  dialog();
        	  Intent intent = new Intent();
        	    intent.setAction("android.intent.action.MAIN");
        	     intent.addCategory("android.intent.category.HOME");
        	      startActivity(intent);
        	// onStop();
       return false;
       }
       return false;
       }
   /*  @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
     // menuUtils.createTwoDispatcher(event);
     if (event.getKeyCode() == KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0) {
    
     }
     return false;
     }*/
     /** 点击右上角的按钮触发弹出框(额外界面法)
	    public void searchtopright(View v) {
	    	//startActivity(SearchTopRightDialog.class);
	    }
	    public void startchat(View v) {
	    	startActivity(SearchTopRightDialog.class);
	    }
     */
     
     /** 点击右上角的按钮触发弹出框(备选法)  */
	protected void initViews() {
		//search_spinner=(RelativeLayout) findViewById(R.id.search_spinner);
		//right_btn=(ImageButton) findViewById(R.id.right_btn);
//		search_dialog_qb=(Button) findViewById(R.id.search_dialog_qb);
//		search_dialog_ct=(Button) findViewById(R.id.search_dialog_ct);
//		search_dialog_yy=(Button) findViewById(R.id.search_dialog_yy);
//		search_dialog_yh=(Button) findViewById(R.id.search_dialog_yh);
		index_search_sou=(ImageView) findViewById(R.id.index_search_sou);
	}

	protected void initEvents() {
		//right_btn.setOnClickListener(this);
//		search_dialog_ct.setOnClickListener(this);
//		search_dialog_yy.setOnClickListener(this);
//		search_dialog_yh.setOnClickListener(this);
//		search_dialog_qb.setOnClickListener(this);
		index_search_sou.setOnClickListener(this);
	}
//	public boolean onTouchEvent(MotionEvent event){
//    	search_spinner.setVisibility(View.GONE);
//		//finish();
//		return true;
//	}
	public void onClick(View arg0) {
		switch (arg0.getId()) {
//		case R.id.right_btn:
//			if(right_btn_num==0){
//		    	search_spinner.setVisibility(View.VISIBLE);
//		    	right_btn_num++;
//			}else{
//		    	search_spinner.setVisibility(View.GONE);
//				right_btn_num--;
//			}
//			break;
//		case R.id.search_dialog_qb:
//			suosouLeiXing=0;
//			showCustomToast("搜索全部");
//	    	search_spinner.setVisibility(View.GONE);
//	    	right_btn_num--;
//			break;
//		case R.id.search_dialog_yy:
//			suosouLeiXing=2;
//			showCustomToast("搜索医院");
//			search_spinner.setVisibility(View.GONE);
//			right_btn_num--;
//			break;
//		case R.id.search_dialog_yh:
//			suosouLeiXing=3;
//			showCustomToast("搜索银行");
//			search_spinner.setVisibility(View.GONE);
//			right_btn_num--;
//			break;
//		case R.id.search_dialog_ct:
//			suosouLeiXing=1;
//			showCustomToast("搜索热门餐厅");
//			search_spinner.setVisibility(View.GONE);
//			right_btn_num--;
//			break;
		case R.id.index_search_sou:
			if(this.search_text.getText().toString() == null
					|| this.search_text.getText().toString().length() == 0) {
				showCustomerToast(android.R.drawable.ic_delete, "警告：请输入关键字搜索");
				break;
			}
			 
			data= new ArrayList<Map<String,Object>>();
			
			 progress = new CustomProgressDialog(IndexGalleryActivity.this,"正在获取中。。。");
			progress.setCanceledOnTouchOutside(false);
			progress.show();
             Thread SousuoThread = new Thread(new SousuoThread());
             SousuoThread.start();
             adapter =new SimpleAdapter(getApplicationContext(), data, R.layout.activity_lv, new String[]{"id","pic","title","content","distance"}, new int[]{R.id.lv_id,R.id.lv_pic,R.id.lv_title,R.id.lv_content,R.id.lv_distance});
           	//把适配器绑定给ListView
             lv.setAdapter(adapter);
            
             lv.setOnItemClickListener(new OnItemClickListener() {
            	   
                 public void onItemClick(AdapterView<?> arg0, View arg1,
                         int position, long arg3) {
                	 SharedPreferences sharedata = getSharedPreferences("data", 0);
     				Intent intent = new Intent(IndexGalleryActivity.this,RowNumberActivity.class);
     				Bundle  mBundel =new Bundle ();
     				mBundel.putSerializable(SER_KEY, ss.get(position));
     				intent.putExtras(mBundel);
     				startActivity(intent);
                 }
             });
             break;
		default:
			break;
		}
	}
 	
}