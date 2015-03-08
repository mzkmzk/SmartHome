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
import com.botb.BaiDuMap.MapControlDemo;
import com.botb.Bean.indent;
import com.botb.Bean.kemu;
import com.botb.geturl.getURL;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
 	
public class SeverActivity extends BaseActivity implements OnTouchListener,
		GestureDetector.OnGestureListener {
	private boolean hasMeasured = false;// 是否Measured.
	private LinearLayout layout_right;
	private ImageView iv_set;
	private int sever_btn_num=0;
	private ImageButton ser_km_add,server_spinner_btn;
	private Button btn_shanchu,ser_add,ser_dingwei,ser_bianji,ser_shuaxin;
	private RelativeLayout server_spinner,layout_left;
	private SharedPreferences sharedata;
	private JSONObject jsonObject; //商店登陆信息
	CustomProgressDialog progress;
	private static final int REQUEST_TIMEOUT = 30*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 30*1000;  //设置等待数据超时时间10秒钟  
	private String responseMsg = "";//返回信息
	private String responseMsg_kemu = "";//返回信息
	private String responseMsg_addkemu = "";//返回信息
	private String responseMsg_delkemu = "";//返回信息
	private String responseMsg_addIntent = "";//返回信息
	private String responseMsg_delIntent = "";//返回信息
	private List<indent> ii; //搜索得到的商店信息
	private List<kemu> kml; //搜索得到的商店信息
	//集合用于存储数据；
    private	List<Map<String, Object>> data;
    private	List<Map<String, Object>> data_kemu;
    private ListView lvser;   //indent listview
    private ListView lv_set; //科目listview
    private MyKeMuAdapter adpater_2;// 科目适配器
    private  MyAdapter adapter_1; //indent适配器
    private String addKemuName;  //增加科目的名称
    private String deleteKemuName; //删除科目的名称
    private TextView kemu_name;
	/** 每次自动展开/收缩的范围 */
	private int MAX_WIDTH = 0;
	/** 每次自动展开/收缩的速度 */
	private final static int SPEED = 30;
	private String uphone ; //要增加的手机号
	private indent di ;//要删除的订单
	private int one=0;//判断是否为第一次进来

	private GestureDetector mGestureDetector;// 手势
	private boolean isScrolling = false;
	private float mScrollX; // 滑块滑动距离
	private int window_width;// 屏幕的宽度
	private String TAG = "jj";

	//private String title[] = { "内一科","内二科","眼科","口腔科","生殖科" };
	private String dataType="";
	
	/***
	 * 初始化view
	 */
	void InitView() {
		layout_left = (RelativeLayout) findViewById(R.id.layout_left);
		layout_right = (LinearLayout) findViewById(R.id.layout_right);
		iv_set = (ImageView) findViewById(R.id.iv_set);
		kemu_name=(TextView)findViewById(R.id.kemu_name);
		sharedata = getSharedPreferences("data", 0);
		try {
			jsonObject =new JSONObject(sharedata.getString("user", null)).getJSONObject("s");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ser_add =(Button) findViewById(R.id.ser_add);
		ser_dingwei=(Button) findViewById(R.id.ser_dingwei);			
		server_spinner=(RelativeLayout) findViewById(R.id.server_spinner);
		server_spinner_btn=(ImageButton) findViewById(R.id.server_spinner_btn);
		ser_bianji=(Button) findViewById(R.id.ser_bianji);
		ser_shuaxin= (Button) findViewById(R.id.ser_shuaxin);
		
		ser_shuaxin.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				 progress = new CustomProgressDialog(SeverActivity.this,"正在刷新中...");
				progress.setCanceledOnTouchOutside(false);
				progress.show();
				
				 Thread DataThread = new Thread(new DataThread());
		            DataThread.start();
	                server_spinner.setVisibility(View.GONE);
	                sever_btn_num--;
			}
		});
		
		server_spinner_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				switch (arg0.getId()) {
				case R.id.server_spinner_btn:
					if(sever_btn_num==0){
						server_spinner.setVisibility(View.VISIBLE);
						sever_btn_num++;
					}else{
						server_spinner.setVisibility(View.GONE);
						sever_btn_num--;
					}
					break;
				default:
					break;
				}
			}
		});
		
		ser_bianji.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
	            startActivity(SeverBjActivity.class);
	            server_spinner.setVisibility(View.GONE);
	            sever_btn_num--;
			}
		});
		
		ser_dingwei.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
                startActivity(MapControlDemo.class);
                server_spinner.setVisibility(View.GONE);
                sever_btn_num--;
			}
		});
		
		ser_km_add=(ImageButton) findViewById(R.id.ser_km_add);
		ser_km_add.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				 LayoutInflater inflater = LayoutInflater.from(SeverActivity.this);  
			        final View textEntryView = inflater.inflate(  
			                R.layout.dialoglayout2, null);  
			        final EditText edtInput=(EditText)textEntryView.findViewById(R.id.edtInput2);  
			        final AlertDialog.Builder builder = new AlertDialog.Builder(SeverActivity.this);  
			        builder.setCancelable(false);  
			        builder.setIcon(R.drawable.pic_index_logo2);  
			        builder.setTitle("添加类型");  
			        builder.setView(textEntryView);  
			        builder.setPositiveButton("确认",  
			                new DialogInterface.OnClickListener() {  
			                   public void onClick(DialogInterface dialog, int whichButton) {  
			      				 progress = new CustomProgressDialog(SeverActivity.this,"正在添加类型中...");
			     				progress.setCanceledOnTouchOutside(false);
			     				progress.show();
			                       addKemuName=edtInput.getText().toString();
			                       Thread addKemuThread =new Thread(new addKemuThread());
			                       addKemuThread.start();
			                    }  
			                });  
			        builder.setNegativeButton("取消",  
			                new DialogInterface.OnClickListener() {  
			                    public void onClick(DialogInterface dialog, int whichButton) {  
			                        setTitle("");  
			                    }  
			                });  
			        builder.show();  
			}
		});
		ser_add.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				 LayoutInflater inflater = LayoutInflater.from(SeverActivity.this);  
			        final View textEntryView = inflater.inflate(  
			                R.layout.dialoglayout, null);  
			        final EditText edtInput=(EditText)textEntryView.findViewById(R.id.edtInput);  
			        final AlertDialog.Builder builder = new AlertDialog.Builder(SeverActivity.this);  
			        builder.setCancelable(false);  
			        builder.setIcon(R.drawable.pic_index_logo2);  
			        builder.setTitle("添加排号");  
			        builder.setView(textEntryView);  
			        builder.setPositiveButton("确认",  
			                new DialogInterface.OnClickListener() {  
			                   public void onClick(DialogInterface dialog, int whichButton) {
			                	   uphone=edtInput.getText().toString();
			                	   Thread addIndentThread_ =new Thread(new addIndentThread() );
			                	   addIndentThread_.start();
			                    }  
			                });  
			        builder.setNegativeButton("取消",  
			                new DialogInterface.OnClickListener() {  
			                    public void onClick(DialogInterface dialog, int whichButton) {  
			                        setTitle("");  
			                    }  
			                });  
			        builder.show(); 
			        server_spinner.setVisibility(View.GONE);
	                sever_btn_num--;
			}
		});
		btn_shanchu = (Button) findViewById(R.id.btn_shanchu);
		lv_set = (ListView) findViewById(R.id.lv_set);
		/*lv_set.setAdapter(new ArrayAdapter<String>(this, R.layout.item,
				R.id.tv_item, title));*/
		adpater_2 = new MyKeMuAdapter(this);
		//this.lvs
		/*lv_set.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(SeverActivity.this, title[position], 1).show();
			}
		});*/
		layout_left.setOnTouchListener(this);
		//iv_set.setOnTouchListener(this);
		mGestureDetector = new GestureDetector(this);
		// 禁用长按监听
		mGestureDetector.setIsLongpressEnabled(false);
		getMAX_WIDTH();
		
		//获取ListView;
		this.lvser=(ListView) this.findViewById(R.id.lv);
		adapter_1 = new MyAdapter(this);
		kml =new ArrayList<kemu>();
		data_kemu=new ArrayList<Map<String,Object>>();
		fillDate();
		this.lvser.setAdapter(adapter_1);
		lv_set.setAdapter(adpater_2);
	}
	
	
	//indent列表适配器=========================================================================开始
	 class MyAdapter extends BaseAdapter {
		 private LayoutInflater mInflater;
		 
		 public MyAdapter(Context context) {
				this.mInflater = LayoutInflater.from(context);
			}
	      
		public int getCount() {
			return data.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater
						.inflate(R.layout.activity_lvser, null);
				holder.number = (TextView) convertView.findViewById(R.id.lvser_num);
				holder.phone = (TextView) convertView.findViewById(R.id.lvser_phone);
				holder.viewBtn = (Button) convertView.findViewById(R.id.btn_shanchu);
				holder.listBtn =(LinearLayout) convertView.findViewById(R.id.lvser_list_1);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.phone.setText((String) data.get(position).get("phone"));
			holder.number.setText(data.get(position).get("number")+"");
			holder.viewBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					 AlertDialog.Builder builder = new Builder(SeverActivity.this);
			         builder.setMessage("确定要删除吗?");
			         builder.setIcon(R.drawable.pic_index_logo2);  
			         builder.setTitle("删除号码");
			         builder.setPositiveButton("确认",
			                 new android.content.DialogInterface.OnClickListener() {
			                     @Override
			                     public void onClick(DialogInterface dialog, int which) {
			        				 progress = new CustomProgressDialog(SeverActivity.this,"正在删除中...");
			        					progress.setCanceledOnTouchOutside(false);
			        					progress.show();
			     					   di=ii.get(position);
			     					   Thread deleteIndentThread_ =new Thread(new deleteIntentThread() );
			     					   deleteIndentThread_.start();
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
					
					
					}
			});
			

			return convertView;
		}
		
	}
		public final class ViewHolder {
			public TextView phone;
			public TextView number;
			public Button viewBtn;
			public LinearLayout  listBtn;
		}
	 //indent列表适配器结束====================================================================================
		
		//科目列表适配器开始===================================================================================
		class MyKeMuAdapter extends BaseAdapter {
			 private LayoutInflater mInflater;
			 
			 public MyKeMuAdapter(Context context) {
					this.mInflater = LayoutInflater.from(context);
				}
		      
			@Override
			public int getCount() {
				return data_kemu.size();
			}

			@Override
			public Object getItem(int arg0) {
				return null;
			}

			@Override
			public long getItemId(int arg0) {
				return 0;
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				 ViewHolder_kemu holder_kemu = null;
				if (convertView == null) {
					holder_kemu = new ViewHolder_kemu();
					convertView = mInflater
							.inflate(R.layout.activity_lvkemu, null);
					holder_kemu.kemu=(TextView)convertView.findViewById(R.id.lvser_kemu);
					holder_kemu.viewBtn = (Button) convertView.findViewById(R.id.btn_shanchu_kemu);
					holder_kemu.listBtn =(RelativeLayout) convertView.findViewById(R.id.lvser_list_kemu_child);
					convertView.setTag(holder_kemu);
				} else {
					holder_kemu = (ViewHolder_kemu) convertView.getTag();
				}
				holder_kemu.kemu.setText((String) data_kemu.get(position).get("kmname"));
				holder_kemu.listBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						one=1;
						kemu_name.setText(kml.get(position).getKmname());
						 dataType=kml.get(position).getKmname();
						 Thread DataThread = new Thread(new DataThread());
				            DataThread.start();
				            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
			        				.getLayoutParams();
			                new AsynMove().execute(SPEED);
			                layout_left.setLayoutParams(layoutParams);
					}
				});
				holder_kemu.viewBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						one=1;
						if(position!=0){
							dataType=kml.get(position-1).getKmname();
							kemu_name.setText(kml.get(position-1).getKmname());
							deleteKemuName =kml.get(position).getKmname();
							dialog();
						}else if(data_kemu.size()!=1){
							Log.i("12345",data.size()+"");
							dataType=kml.get(position+1).getKmname();
							kemu_name.setText(kml.get(position+1).getKmname());
							deleteKemuName =kml.get(position).getKmname();
							dialog();
						}else {
							showCustomerToast(android.R.drawable.ic_delete, "最后一门不能删除");
						}
						
						/*LayoutInflater inflater = LayoutInflater.from(SeverActivity.this);  
						 final AlertDialog.Builder builder = new AlertDialog.Builder(SeverActivity.this);  
						 final View textEntryView = inflater.inflate(  
					                R.layout.dialoglayout, null);  
					        builder.setCancelable(false);  
					       // builder.setIcon(R.drawable.pic_index_logo);  
					        builder.setTitle("删除科目");  
					        builder.setView(textEntryView);  
					        builder.setPositiveButton("确认",  
					                new DialogInterface.OnClickListener() {  
					                   public void onClick(DialogInterface dialog, int whichButton) {  
					                	   deleteKemuName =kml.get(position).getKmname();
					                	   mDialog = new ProgressDialog(SeverActivity.this);
					                       mDialog.setTitle("删除分类");
					                       mDialog.setMessage("删除添加，请稍后...");
					                       mDialog.show();
					                       Thread deleteKemuThread =new Thread(new deleteKemuThread ());
					                       deleteKemuThread.start();
					                    }  
					                });  
					        builder.setNegativeButton("取消",  
					                new DialogInterface.OnClickListener() {  
					                    public void onClick(DialogInterface dialog, int whichButton) {  
					                        setTitle("");  
					                    }  
					                });  
					        builder.show();  */
					}
				});

				return convertView;
			}
			
		}
			public final class ViewHolder_kemu {
				public TextView kemu;
				public Button viewBtn;
				public RelativeLayout  listBtn;
			}
		//科目列表适配器结束===================================================================================
	// 填充数据;
		private void fillDate(){
			
			data= new ArrayList<Map<String,Object>>();   
			 progress = new CustomProgressDialog(SeverActivity.this,"正在加载中...");
			progress.setCanceledOnTouchOutside(false);
			progress.show();
           
            Thread kemThread =new Thread(new kemuThread());
             kemThread.start();
                /* Thread DataThread = new Thread(new DataThread());
                 DataThread.start();*/
		}
		
		// DataThread线程类  获取indent=============================================================================
	    class  DataThread implements Runnable
	    {

	        @Override
	        public void run() 
	        {
	        	try {
	        		
	            //URL合法，但是这一步并不验证密码是否正确
	        		Log.i("12345",jsonObject.getInt("sid")+"");
	        		int sid =jsonObject.getInt("sid");
	            boolean dataValidate = dataServer(sid,dataType);
	            Log.i("12345",dataType+"我是更新后逇DataType!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
	            	data= new ArrayList<Map<String,Object>>();  
	            	ii =new ArrayList<indent>();
	            	try {
	        		JSONArray array= new JSONObject(responseMsg).getJSONArray("li");
	        		for(int i=0;i<array.length();i++){
	        			JSONObject jsonObject =array.getJSONObject(i);
	        		   indent indent_one=new indent();
	        		  indent_one.setIid(jsonObject.getInt("iid"));
	        		  indent_one.setSid(jsonObject.getInt("sid"));
	        		  indent_one.setUphone(jsonObject.getString("uphone"));
	        		  indent_one.setInfo(jsonObject.getString("info"));
	        		  indent_one.setIno(jsonObject.getInt("ino"));
	        		  ii.add(indent_one);
	        		}
	            	} catch (JSONException e) {
						e.printStackTrace();
					}
	            	for(indent iii:ii){
	            		Map<String, Object> item=new HashMap<String, Object>();
	             		item.put("id", 1);
	             		item.put("phone",iii.getUphone()); 
	             		item.put("number",iii.getIno());
	             		data.add(item);
	    			}
	            	Log.i("12345",msg+"11111");
	            	Log.i("12345",msg+"22222222222222");
	                adapter_1.notifyDataSetChanged();
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
	    
	    public boolean dataServer(int sid ,String dataType)     //DT的连接
	    {
	        boolean suosouValidate = false;
	        //使用apache HTTP客户端实现
	        String urlStr = getURL.getUrl("FindIndentAction");
	        
	        Log.i("12345",urlStr);
	        HttpPost request = new HttpPost(urlStr);
	        //如果传递参数多的话，可以对传递的参数进行封装
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        //添加用户名和密码
	        params.add(new BasicNameValuePair("sid",sid+""));
	        params.add(new BasicNameValuePair("dataType",dataType));
	        Log.i("12345",dataType);
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
	 //获取indent结束================================================================================
	    
	    //获取科目开始============================================================================
	 // kemuThread线程类
	    class  kemuThread implements Runnable
	    {
	        @Override
	        public void run() 
	        {
	        	try {
	        		kml =new ArrayList<kemu>();
	        		data_kemu=new ArrayList<Map<String,Object>>();
	            //URL合法，但是这一步并不验证密码是否正确
	        		Log.i("12345",jsonObject.getInt("sid")+"");
	        		int sid =jsonObject.getInt("sid");
	            boolean kemuValidate = kemuServer(sid);
	            Log.i("12345","----------------------------bool is :"+kemuValidate+"----------response:"+responseMsg_kemu);
	            Message msg = handler_kemu.obtainMessage();
	            if(kemuValidate)
	            {
	            	JSONObject json;
	            	
						json= new JSONObject(responseMsg_kemu);
						Log.i("12345",json.getString("msg")+"111111");
					
	                if(json.getString("msg").equals("success"))
	                {
	                    msg.what = 0;
	                    handler_kemu.sendMessage(msg);
	                }else
	                {
	                    msg.what = 1;
	                    handler_kemu.sendMessage(msg);
	                }
	                
	            }else
	            {
	                msg.what = 2;
	                handler_kemu.sendMessage(msg);
	            }
	        	} catch (JSONException e) {
					e.printStackTrace();
				}
	            
	        }

	    }
		
	  //Handler
	    Handler handler_kemu = new Handler()
	    {
	        public void handleMessage(Message msg)
	        {
	            switch(msg.what)
	            {
	            case 0:
	            	Log.i("12345",msg+"111111111111111111111111111111111111111111111111111111111");
	            	
	            	try {
	        		JSONArray array= new JSONObject(responseMsg_kemu).getJSONArray("lk");
	        		for(int i=0;i<array.length();i++){
	        			JSONObject jsonObject =array.getJSONObject(i);
							Log.i("12345",jsonObject.getString("kmname")+"");
							Log.i("12345",jsonObject.getInt("kmid")+"");
							Log.i("12345",jsonObject.getInt("sid")+"");
	        		   kemu km=new kemu();
	        		   km.setKmid(jsonObject.getInt("kmid"));
	        		   km.setSid(jsonObject.getInt("sid"));
	        		   km.setKmname(jsonObject.getString("kmname"));
	        		   kml.add(km);
	        		}
	            	} catch (JSONException e) {
						e.printStackTrace();
					}
	            	data_kemu=new ArrayList<Map<String,Object>>();
	            	for(kemu iii:kml){
	            		Map<String, Object> item=new HashMap<String, Object>();
	             		item.put("id", iii.getKmid());
	             		item.put("sid", iii.getSid());
	             		item.put("kmname",iii.getKmname()); 
	             		Log.i("12345",iii.getKmname());
	             		data_kemu.add(item);
	    			}
	            	Log.i("12345",dataType+"我是科目!!!!!!!!!!!!!!!!!!!!1");
	            	if(one==0){
	            		dataType=kml.get(0).getKmname();
	            		kemu_name.setText(dataType);
	            	}
	            	adpater_2.notifyDataSetChanged();
	            	adapter_1.notifyDataSetChanged();
	            	 Thread DataThread = new Thread(new DataThread());
	                 DataThread.start();
	                break;
	            case 1:
	                showCustomerToast(android.R.drawable.ic_delete,"暂无数据");
	                break;
	            case 2:
	                showCustomerToast(android.R.drawable.ic_delete, "URL验证失败");
	                break;
	                default:
	                	showCustomerToast(android.R.drawable.ic_delete, "URL验证失败");
	                	Log.i("12345","caonima");
	                	break;
	            }
	            
	        }
	    };
	    
	    public boolean kemuServer(int sid)     //DT的连接
	    {
	        boolean kemuValidate = false;
	        //使用apache HTTP客户端实现
	        String urlStr = getURL.getUrl("FindKeMuAction");
	        
	        Log.i("12345",urlStr);
	        HttpPost request = new HttpPost(urlStr);
	        //如果传递参数多的话，可以对传递的参数进行封装
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        //添加用户名和密码
	        params.add(new BasicNameValuePair("sid",sid+""));
	        Log.i("12345",sid+"");
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
	            	Log.i("12345","411");
	            	kemuValidate = true;
	                //获得响应信息
	                responseMsg_kemu = EntityUtils.toString(response.getEntity());
	                Log.i("12345",responseMsg_kemu);
	            }
	        }catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	        return kemuValidate;
	    }
	 
	    //获取科目结束============================================================================
	    //增加科目开始============================================================================
		 // addKemuThread线程类
		    class  addKemuThread implements Runnable
		    {
		        @Override
		        public void run() 
		        {
		        	try {
	      	                
		            //URL合法，但是这一步并不验证密码是否正确
		        		Log.i("12345",jsonObject.getInt("sid")+"");
		        		int sid =jsonObject.getInt("sid");
		            boolean addKemuValidate = addKemuServer(sid,addKemuName);
		            Log.i("12345","----------------------------bool is :"+addKemuValidate+"----------response:"+responseMsg_addkemu);
		            Message msg = handler_addkemu.obtainMessage();
		            if(addKemuValidate)
		            {
		            	JSONObject json;
		            	
							json= new JSONObject(responseMsg_addkemu);
							Log.i("12345",json.getString("msg")+"111111");
						
		                if(json.getString("msg").equals("success"))
		                {
		                	Log.i("12345","123456");
		                    msg.what = 0;
		                    Log.i("12345","1234567");
		                    handler_addkemu.sendMessage(msg);
		                    Log.i("12345","12345678");
		                }else
		                {
		                    msg.what = 1;
		                    handler_addkemu.sendMessage(msg);
		                }
		                
		            }else
		            {
		                msg.what = 2;
		                handler_addkemu.sendMessage(msg);
		            }
		        	} catch (JSONException e) {
						e.printStackTrace();
					}
		            
		        }

		    }
			
		  //Handler
		    Handler handler_addkemu = new Handler()
		    {
		        public void handleMessage(Message msg)
		        {
		        	Log.i("12345",msg+"");
		            switch(msg.what)
		            {
		            case 0:
		            	 showCustomerToast(R.drawable.btn_check_buttonless_on,"添加成功");
		            	 Thread kemThread =new Thread(new kemuThread());
		                 kemThread.start();
		                 break;
		            case 1:
		                showCustomerToast(android.R.drawable.ic_delete,"已有名字一样的类型");
		                progress.dismiss();
		                break;
		            case 2:
		                showCustomerToast(android.R.drawable.ic_delete, "URL验证失败");
		                progress.dismiss();
		                break;
		            
		            }
		            
		        }
		    };
		    
		    public boolean addKemuServer(int sid,String kemuname)     //DT的连接
		    {
		        boolean kemuaddValidate = false;
		        //使用apache HTTP客户端实现
		        String urlStr = getURL.getUrl("KeMuAddAction");
		        
		        Log.i("12345",urlStr);
		        HttpPost request = new HttpPost(urlStr);
		        //如果传递参数多的话，可以对传递的参数进行封装
		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		        //添加用户名和密码
		        params.add(new BasicNameValuePair("sid",sid+""));
		        params.add(new BasicNameValuePair("kmname",kemuname+""));
		        
		        Log.i("12345",sid+"");
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
		            	responseMsg_addkemu = EntityUtils.toString(response.getEntity());
		                Log.i("12345",responseMsg_addkemu);
		            }
		        }catch(Exception e)
		        {
		            e.printStackTrace();
		        }
		        return kemuaddValidate;
		    }
		 
		    //增加科目结束============================================================================
		    
		    //删除科目开始============================================================================
			 // addKemuThread线程类
			    class  deleteKemuThread implements Runnable
			    {
			        @Override
			        public void run() 
			        {
			        	try {
		      	                
			            //URL合法，但是这一步并不验证密码是否正确
			        		Log.i("12345",jsonObject.getInt("sid")+"");
			        		int sid =jsonObject.getInt("sid");
			            boolean deleteKemuValidate = deleteKemuServer(sid,deleteKemuName);
			            Log.i("12345","----------------------------bool is :"+deleteKemuValidate+"----------response:"+responseMsg_delkemu);
			            Message msg = handler_deletekemu.obtainMessage();
			            if(deleteKemuValidate)
			            {
			            	JSONObject json;
								json= new JSONObject(responseMsg_delkemu);
								Log.i("12345",json.getString("msg")+"111111");
							
			                if(json.getString("msg").equals("success"))
			                {
			                    msg.what = 0;
			                    handler_deletekemu.sendMessage(msg);
			                }else
			                {
			                    msg.what = 1;
			                    handler_deletekemu.sendMessage(msg);
			                }
			                
			            }else
			            {
			                msg.what = 2;
			                handler_deletekemu.sendMessage(msg);
			            }
			        	} catch (JSONException e) {
							e.printStackTrace();
						}
			            
			        }

			    }
				
			  //Handler
			    Handler handler_deletekemu = new Handler()
			    {
			        public void handleMessage(Message msg)
			        {
			        	Log.i("12345",msg+"");
			            switch(msg.what)
			            {
			            case 0:
			            	 showCustomerToast(R.drawable.btn_check_buttonless_on,"删除成功");
			            	 Thread kemThread =new Thread(new kemuThread());
			                 kemThread.start();
			                 break;
			            case 1:
			                showCustomerToast(android.R.drawable.ic_delete,"删除失败");
			                progress.dismiss();
			                break;
			            case 2:
			                showCustomerToast(android.R.drawable.ic_delete, "URL验证失败");
			                progress.dismiss();
			                break;
			            
			            }
			            
			        }
			    };
			    
			    public boolean deleteKemuServer(int sid,String kemuname)     //DT的连接
			    {
			        boolean kemuaddValidate = false;
			        //使用apache HTTP客户端实现
			        String urlStr = getURL.getUrl("KeMuDeleteAction");
			        
			        Log.i("12345",urlStr);
			        HttpPost request = new HttpPost(urlStr);
			        //如果传递参数多的话，可以对传递的参数进行封装
			        List<NameValuePair> params = new ArrayList<NameValuePair>();
			        //添加用户名和密码
			        params.add(new BasicNameValuePair("sid",sid+""));
			        params.add(new BasicNameValuePair("kmname",kemuname+""));
			        
			        Log.i("12345",sid+"");
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
			            	responseMsg_delkemu = EntityUtils.toString(response.getEntity());
			                Log.i("12345",responseMsg_delkemu);
			            }
			        }catch(Exception e)
			        {
			            e.printStackTrace();
			        }
			        return kemuaddValidate;
			    }
			 
			    //删除科目结束============================================================================
			    
			    //增加indent开始============================================================================
				 // addKemuThread线程类
				    class  addIndentThread implements Runnable
				    {
				        @Override
				        public void run() 
				        {
				        	try {
			      	                
				            //URL合法，但是这一步并不验证密码是否正确
				        		Log.i("12345",jsonObject.getInt("sid")+"");
				        		int sid =jsonObject.getInt("sid");
				            boolean addIndentValidate = addIntentServer(sid,dataType,uphone);
				            Log.i("12345","----------------------------bool is :"+addIndentValidate+"----------response:"+responseMsg_addIntent);
				            Message msg = handler_addIntent.obtainMessage();
				            if(addIndentValidate)
				            {
				            	JSONObject json;
				            	
									json= new JSONObject(responseMsg_addIntent);
									Log.i("12345",json.getString("msg")+"111111");
								
				                if(json.getInt("msg")==1)
				                {
				                	Log.i("12345","123456");
				                    msg.what = 0;
				                    Log.i("12345","1234567");
				                    handler_addIntent.sendMessage(msg);
				                    Log.i("12345","12345678");
				                }else
				                {
				                    msg.what = 1;
				                    handler_addIntent.sendMessage(msg);
				                }
				                
				            }else
				            {
				                msg.what = 2;
				                handler_addIntent.sendMessage(msg);
				            }
				        	} catch (JSONException e) {
								e.printStackTrace();
							}
				            
				        }

				    }
					
				  //Handler
				    Handler handler_addIntent = new Handler()
				    {
				        public void handleMessage(Message msg)
				        {
				        	Log.i("12345",msg+"");
				            switch(msg.what)
				            {
				            case 0:
				            	 showCustomerToast(R.drawable.btn_check_buttonless_on,"添加成功");
				            	 Thread dataThread =new Thread(new DataThread());
				            	 dataThread.start();
				                break;
				            case 1:
				                showCustomerToast(android.R.drawable.ic_delete,"该手机号已经排过本商店且未删除");
				                break;
				            case 2:
				                showCustomerToast(android.R.drawable.ic_delete, "URL验证失败");
				                break;
				            
				            }
				            
				        }
				    };
				    
				    public boolean addIntentServer(int sid,String kemuname,String uphone)     //DT的连接
				    {
				        boolean IntentaddValidate = false;
				        //使用apache HTTP客户端实现
				        String urlStr = getURL.getUrl("AccessNumberAction");
				        
				        Log.i("12345",urlStr);
				        HttpPost request = new HttpPost(urlStr);
				        //如果传递参数多的话，可以对传递的参数进行封装
				        List<NameValuePair> params = new ArrayList<NameValuePair>();
				        //添加用户名和密码
				        params.add(new BasicNameValuePair("sid",sid+""));
				        params.add(new BasicNameValuePair("info",kemuname+""));
				        params.add(new BasicNameValuePair("uphone",uphone+""));
				        Log.i("12345",sid+"");
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
				            	IntentaddValidate = true;
				                //获得响应信息
				            	responseMsg_addIntent = EntityUtils.toString(response.getEntity());
				                Log.i("12345",responseMsg_addIntent);
				            }
				        }catch(Exception e)
				        {
				            e.printStackTrace();
				        }
				        return IntentaddValidate;
				    }
				 
				    //增加Intent结束============================================================================
				    //删除Intent开始============================================================================
					 // addKemuThread线程类
					    class  deleteIntentThread implements Runnable
					    {
					        @Override
					        public void run() 
					        {
					        	try {
				      	                
					            //URL合法，但是这一步并不验证密码是否正确
					        		Log.i("12345",jsonObject.getInt("sid")+"");
					        		int sid =jsonObject.getInt("sid");
					            boolean deleteIntentValidate = deleteIntentServer(di);
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
					            	 showCustomerToast(R.drawable.btn_check_buttonless_on,"删除成功");
					            	 Thread dataThread =new Thread(new DataThread());
					            	 dataThread.start();
					            	 break;
					            case 1:
					                showCustomerToast(android.R.drawable.ic_delete,"删除失败");
					                break;
					            case 2:
					                showCustomerToast(android.R.drawable.ic_delete, "URL验证失败");
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
			    
	    //初始化HttpClient，并设置超时
	    public HttpClient getHttpClient()
	    {
	        BasicHttpParams httpParams = new BasicHttpParams();
	        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
	        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
	        HttpClient client = new DefaultHttpClient(httpParams);
	        return client;
	    }
			
	/***
	 * 获取移动距离 移动的距离其实就是layout_left的宽度
	 */
	void getMAX_WIDTH() {
		ViewTreeObserver viewTreeObserver = layout_left.getViewTreeObserver();
		// 获取控件宽度
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (!hasMeasured) {
					window_width = getWindowManager().getDefaultDisplay()
							.getWidth();
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
							.getLayoutParams();
					layoutParams.width = window_width;
					layout_left.setLayoutParams(layoutParams);
					MAX_WIDTH = layout_right.getWidth();
					Log.v(TAG, "MAX_WIDTH=" + MAX_WIDTH + "width="
							+ window_width);
					hasMeasured = true;
				}
				return true;
			}
		});

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_server);
		InitView();
		initViews();
		initEvents();
	}


		public boolean onTouch(View v, MotionEvent event) {
			// 松开的时候要判断，如果不到三分之一屏幕位子则缩回去，
			if (MotionEvent.ACTION_UP == event.getAction() && isScrolling == true) {
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
						.getLayoutParams();
				// 缩回去
				if (layoutParams.leftMargin < -window_width /3) {
					new AsynMove().execute(-SPEED);
				} else {
					new AsynMove().execute(SPEED);
				}
			}
	
			return mGestureDetector.onTouchEvent(event);
		}
	

	@Override
	public boolean onDown(MotionEvent e) {
		mScrollX = 0;
		isScrolling = false;
		// 将之改为true，不然事件不会向下传递.
		return true;
	}

	public void onShowPress(MotionEvent e) {

	}

	/***
	 * 点击松开执行
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
				.getLayoutParams();
		// 左移动
		if (layoutParams.leftMargin >= 0) {
			new AsynMove().execute(-SPEED);
		} else {
			// 右移动
			new AsynMove().execute(SPEED);
		}

		return true;
	}

	/***
	 * 滑动监听 就是一个点移动到另外一个点. distanceX=后面点x-前面点x，如果大于0，说明后面点在前面点的右边及向右滑动
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		isScrolling = true;
		mScrollX += distanceX;// distanceX:向左为正，右为负
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
				.getLayoutParams();
		layoutParams.leftMargin -= mScrollX;
		if (layoutParams.leftMargin >= 0) {
			isScrolling = false;// 拖过头了不需要再执行AsynMove了
			layoutParams.leftMargin = 0;

		} else if (layoutParams.leftMargin <= -MAX_WIDTH) {
			// 拖过头了不需要再执行AsynMove了
			isScrolling = false;
			layoutParams.leftMargin = -MAX_WIDTH;
		}
		layout_left.setLayoutParams(layoutParams);
		return false;
	}

	public void onLongPress(MotionEvent e) {

	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	class AsynMove extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			int times = 0;
			if (MAX_WIDTH % Math.abs(params[0]) == 0)// 整除
				times = MAX_WIDTH / Math.abs(params[0]);
			else
				times = MAX_WIDTH / Math.abs(params[0]) + 1;// 有余数

			for (int i = 0; i < times; i++) {
				publishProgress(params[0]);
				try {
					Thread.sleep(Math.abs(params[0]));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		/**
		 * update UI
		 */
		protected void onProgressUpdate(Integer... values) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			// 右移动
			if (values[0] > 0) {
				layoutParams.leftMargin = Math.min(layoutParams.leftMargin
						+ values[0], 0);
				//Log.v(TAG, "移动右" + layoutParams.rightMargin);
			} else {
				// 左移动
				layoutParams.leftMargin = Math.max(layoutParams.leftMargin
						+ values[0], -MAX_WIDTH);
				//Log.v(TAG, "移动左" + layoutParams.rightMargin);
			}
			layout_left.setLayoutParams(layoutParams);

		}

	}
	
	 protected void dialog() {
         AlertDialog.Builder builder = new Builder(SeverActivity.this);
         builder.setMessage("确定要删除吗?");
         builder.setIcon(R.drawable.pic_index_logo2);  
         builder.setTitle("删除类型");
         builder.setPositiveButton("确认",
                 new android.content.DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                    	 Thread deleteKemuThread =new Thread(new deleteKemuThread ());
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
     }
	 
	
		protected void initViews() {

		}

		protected void initEvents() {

		}
		
		public boolean onTouchEvent(MotionEvent event){
			server_spinner.setVisibility(View.GONE);
			//finish();
			return true;
		}
}