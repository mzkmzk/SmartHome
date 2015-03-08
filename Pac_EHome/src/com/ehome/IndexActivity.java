package com.ehome;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ehome.AirControlActivity.ContralKongTiaoThread;
import com.ehome.utils.ModelServer;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 使用ActivityGroup来切换Activity和Layout
 * @author
 */
public class IndexActivity extends ActivityGroup {
	
	private Toast mToast;
	private static String TAG = "IatDemo";
	// 语音听写对象
	private SpeechRecognizer mIat;
	
    private  ScrollView container = null;
    public static final int MENU_EXIT = 1;
    private Context context;

    private LinearLayout layout_home,layout_set;
    private ImageView menu_home,menu_set,menu_voice;
	private TextView text_thome,text_tset;   
    private LocalActivityManager localActivityManager;
    
    private static final String HomeActivity = "HomeActivity";
    private static final String SetActivity = "SetActivity";
    
    protected static final int RESULT_SPEECH = 1;
    
    private int ret = 0;// 函数调用返回值
    
    private  void resumeActivity(Activity activity,Class<?> cls,String activityId){
    	
		container.removeAllViews();
		 
		Intent it = new Intent(this, cls);
		container.addView(getLocalActivityManager().startActivity(activityId,it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView());
    }
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 设置视图
        setContentView(R.layout.activity_tab);
        
        mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);

        // 初始化识别对象
     	mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
        
        context=this;
        localActivityManager = getLocalActivityManager();
        
        container = (ScrollView)findViewById(R.id.container_index);
        menu_home = (ImageView)findViewById(R.id.img_home);
        menu_set = (ImageView)findViewById(R.id.img_set);
        menu_voice = (ImageView) findViewById(R.id.layout_voice);
        text_thome = (TextView) findViewById(R.id.text_home);
        text_tset = (TextView) findViewById(R.id.text_set);
        layout_home = (LinearLayout) findViewById(R.id.layout_home);
        layout_set = (LinearLayout) findViewById(R.id.layout_set);
        
        
        //第一次载入
    	if(localActivityManager.getActivity("HomeActivity")==null){
	        Intent it = new Intent(this, HomeActivity.class);
	        container.addView(getLocalActivityManager().startActivity(HomeActivity,it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView());
    	}
        
    	//首页搜索
    	layout_home.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {
            	Activity forward_activity = localActivityManager.getActivity(HomeActivity);
            	resumeActivity(forward_activity,HomeActivity.class,HomeActivity);
            	menu_home.setImageDrawable(getResources().getDrawable(R.drawable.home_checked));
				text_thome.setTextColor(text_thome.getResources().getColor(R.color.color_blue));
				
				menu_set.setImageDrawable(getResources().getDrawable(R.drawable.setting_normal));
				text_tset.setTextColor(text_tset.getResources().getColor(R.color.color_hint));
            }
        });
        
    	layout_set.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
	         	Activity forward_activity = localActivityManager.getActivity(SetActivity);
	         	resumeActivity(forward_activity,SetActivity.class,SetActivity);
	         	
				menu_set.setImageDrawable(getResources().getDrawable(R.drawable.setting_checked));
				text_tset.setTextColor(text_tset.getResources().getColor(R.color.color_blue));   
				
				menu_home.setImageDrawable(getResources().getDrawable(R.drawable.home_normal));
				text_thome.setTextColor(text_thome.getResources().getColor(R.color.color_hint));
            }
        });
    	
    	menu_voice.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			
//    			Google语音配置
//    			Intent intent = new Intent(
//						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//
//				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
//
//				try {
//					startActivityForResult(intent, RESULT_SPEECH);
//				} catch (ActivityNotFoundException a) {
//					showCustomToast("Ops! Your device doesn't support Speech to Text");
//				}
    			
    			//showCustomToast("请长按并语音");
    			
    			ret = mIat.startListening(recognizerListener);
    			if(ret != ErrorCode.SUCCESS){
    				showTip("听写失败,错误码：" + ret);
    			}else {
    				showTip(getString(R.string.text_begin));
    			}
    			
    		}
    	});
    }
    
    /**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败,错误码："+code);
        	}
		}
	};
	/**
	 * 听写监听器。
	 */
	private RecognizerListener recognizerListener=new RecognizerListener(){

		@Override
		public void onBeginOfSpeech() {	
//			showTip("开始说话");
		}


		@Override
		public void onError(SpeechError error) {
			showCustomToast(error.getPlainDescription(true));
		}

		@Override
		public void onEndOfSpeech() {
//			showTip("结束说话");
		}



		@Override
		public void onResult(RecognizerResult results, boolean isLast) {		
			Log.d("Result:",results.getResultString ());
			String text = JsonParser.parseIatResult(results.getResultString());
			showTip(text);
		}

		@Override
		public void onVolumeChanged(int volume) {
			showTip("当前正在说话，音量大小：" + volume);
		}


		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
		}
	};
	
    String status ; 
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				showCustomToast(text.get(0));
				Log.i("12345","识别的语音是!!!!!!!"+text.get(0)+"语句是!!!!!"+text.get(0).indexOf("开"));
				if(text.get(0).indexOf("开")!=-1){
					ContralKongTiaoThread light3Open = new ContralKongTiaoThread();
					light3Open.deviceid=3;
					light3Open.status="1";
					Thread contralKongTiaoThread3 = new Thread(light3Open);
					contralKongTiaoThread3.start();
					//Thread contralKongTiaoThread6 = new Thread(new ContralKongTiaoThread());
					//status ="0";
					//contralKongTiaoThread6.start();
				}else if(text.get(0).indexOf("关三")!=-1){
					
					ContralKongTiaoThread light3Open = new ContralKongTiaoThread();
					light3Open.deviceid=3;
					light3Open.status="0";
					Thread contralKongTiaoThread3 = new Thread(light3Open);
					contralKongTiaoThread3.start();
					//Thread contralKongTiaoThread5 = new Thread(new ContralKongTiaoThread());
					//status ="6";
					//contralKongTiaoThread5.start();
				}else if(text.get(0).indexOf("关")!=-1){
					//Thread contralKongTiaoThread2 = new Thread(new ContralKongTiaoThread());
					//status ="2";
					//contralKongTiaoThread2.start();
					//showCustomToast("请长按并语音");
				}else if(text.get(0).indexOf("开1号灯")!=-1){
					status ="6";
				}else if(text.get(0).indexOf("关电视")!=-1){
					status ="6";
				}else if(text.get(0).indexOf("开电视")!=-1){
					status ="6";
				}
			}
			break;
		}

		}
	}
    
    //控制空调线程类
  //控制空调线程类
    class ContralKongTiaoThread implements Runnable
    {
    	
    	public int deviceid;
		public String status;
        public void run() 
        {
        	Log.i("12345","空调传过去的码是 : "+status);
			   List<NameValuePair> params = new ArrayList<NameValuePair>();
			   //添加设备号和状态传输
			   params.add(new BasicNameValuePair("deviceid",1+""));
			   params.add(new BasicNameValuePair("status",status+""));
			   ModelServer modelHttp =new ModelServer();
			   modelHttp.Model_server(params, "updateKongTiao");
			   
        }
    }
    
	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(IndexActivity.this).inflate(R.layout.common_toast, null);
		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(IndexActivity.this);
		mToast.setGravity(Gravity.CENTER, 0, 500);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}
	
	private void showTip(final String str)
	{
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				View toastRoot = LayoutInflater.from(IndexActivity.this).inflate(R.layout.common_toast, null);
				((TextView) toastRoot.findViewById(R.id.toast_text)).setText(str);
				mToast.setGravity(Gravity.CENTER, 0, 500);
				mToast.setDuration(Toast.LENGTH_SHORT);
				mToast.setView(toastRoot);
				mToast.show();
			}
		});
	}
}