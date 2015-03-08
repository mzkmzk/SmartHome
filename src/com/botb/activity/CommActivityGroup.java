package com.botb.activity;

import com.botb.core.ATManager;
import com.botb.core.Const;
import com.botb.service.PollingService;
import com.botb.service.PollingUtils;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 使用ActivityGroup来切换Activity和Layout
 * @author
 */
public class CommActivityGroup extends ActivityGroup {
	
    private  ScrollView container = null;
    public static final int MENU_EXIT = 1;
    private Context context;

    private ImageView menu_ss,menu_fl,menu_fj,menu_sc;
    private LinearLayout layoutmenu_ss,layoutmenu_fl,layoutmenu_fj,layoutmenu_sc;
    private TextView cx_text_ss,cx_text_fl,cx_text_fj,cx_text_sc;
    
    private LocalActivityManager localActivityManager;
    
    private static final String ShakeActivity = "ShakeActivity";
    private static final String MyNumberActivity = "MyNumberActivity";
    private static final String IndexGalleryActivity = "IndexGalleryActivity";
    private static final String NearbyActivity = "NearbyActivity";
    private  void resumeActivity(Activity activity,Class<?> cls,String activityId){
    	
//		boolean status = false;
		
		container.removeAllViews();
		 
//		if(activity!=null){
//			status = activity.getIntent().getBooleanExtra("status", false);
//		}
		
		Log.d(Const.TAG, "CommActivityGroup.resumeActivity|activity="+activity+",activityId="+activityId);
		
//		if(activity!=null && status){
//    		View activityView = activity.getWindow().getDecorView();
//			container.addView(activityView);
//		}else{
			Intent it = new Intent(this, cls);
			
			if(MyNumberActivity.equals(activityId)){
				Log.i("12345",activityId);
				container.addView(getLocalActivityManager().startActivity(activityId,it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView());
			}else{
				container.addView(getLocalActivityManager().startActivity(activityId,it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)).getDecorView());
			}
//	    }
}
    
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 设置视图
        setContentView(R.layout.activity_tab);
        PollingUtils.startPollingService(this, 5, PollingService.class, PollingService.ACTION);
        ATManager.addActivity(this);
        context=this;
        localActivityManager = getLocalActivityManager();
        
        container = (ScrollView)findViewById(R.id.container_index);
        menu_ss = (ImageView)findViewById(R.id.menu_ss);
        menu_fl = (ImageView)findViewById(R.id.menu_fl);
        menu_sc = (ImageView)findViewById(R.id.menu_sc);
        menu_fj = (ImageView)findViewById(R.id.menu_fj);
        layoutmenu_ss = (LinearLayout)findViewById(R.id.layoutmenu_ss);
        layoutmenu_fl = (LinearLayout)findViewById(R.id.layoutmenu_fl);
        layoutmenu_sc = (LinearLayout)findViewById(R.id.layoutmenu_sc);
        layoutmenu_fj = (LinearLayout)findViewById(R.id.layoutmenu_fj);
        cx_text_fj = (TextView) findViewById(R.id.cx_text_fj);
        cx_text_fl = (TextView) findViewById(R.id.cx_text_fl);
        cx_text_ss = (TextView) findViewById(R.id.cx_text_ss);
        cx_text_sc = (TextView) findViewById(R.id.cx_text_sc);
        

        //第一次载入
    	if(localActivityManager.getActivity("NearbyActivity")==null){
	        Intent it = new Intent(this, NearbyActivity.class);
	        container.addView(getLocalActivityManager().startActivity(NearbyActivity,it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView());
    	}
        
    	//首页搜索
        menu_ss.setOnClickListener(new OnClickListener() {
        	
            @Override
            public void onClick(View v) {

                menu_ss.setBackgroundResource(R.drawable.menu_11);
                menu_fl.setBackgroundResource(R.drawable.menu_4);
                menu_fj.setBackgroundResource(R.drawable.menu_2);
                menu_sc.setBackgroundResource(R.drawable.menu_3);
                cx_text_ss.setTextColor(cx_text_ss.getResources().getColor(R.color.color_blue));
                cx_text_fl.setTextColor(cx_text_fl.getResources().getColor(R.color.hint));
                cx_text_fj.setTextColor(cx_text_fj.getResources().getColor(R.color.hint));
                cx_text_sc.setTextColor(cx_text_sc.getResources().getColor(R.color.hint));
            	Activity forward_activity = localActivityManager.getActivity(IndexGalleryActivity);

            	resumeActivity(forward_activity,IndexGalleryActivity.class,IndexGalleryActivity);
            	
            }
        });
        
        menu_fl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	
                menu_ss.setBackgroundResource(R.drawable.menu_1);
                menu_fl.setBackgroundResource(R.drawable.menu_44);
                menu_fj.setBackgroundResource(R.drawable.menu_2);
                menu_sc.setBackgroundResource(R.drawable.menu_3);
                cx_text_ss.setTextColor(cx_text_ss.getResources().getColor(R.color.hint));
                cx_text_fl.setTextColor(cx_text_fl.getResources().getColor(R.color.color_blue));
                cx_text_fj.setTextColor(cx_text_fj.getResources().getColor(R.color.hint));
                cx_text_sc.setTextColor(cx_text_sc.getResources().getColor(R.color.hint));
            	Activity forward_activity = localActivityManager.getActivity(ShakeActivity);
            	
        		resumeActivity(forward_activity,ShakeActivity.class,ShakeActivity);
        			
            }
        });
        menu_fj.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
 //               Intent it = new Intent(context, NearbyActivity.class);
 //               startActivity(it);
            	
                menu_ss.setBackgroundResource(R.drawable.menu_1);
                menu_fl.setBackgroundResource(R.drawable.menu_4);
                menu_fj.setBackgroundResource(R.drawable.menu_22);
                menu_sc.setBackgroundResource(R.drawable.menu_3);
                cx_text_ss.setTextColor(cx_text_ss.getResources().getColor(R.color.hint));
                cx_text_fl.setTextColor(cx_text_fl.getResources().getColor(R.color.hint));
                cx_text_fj.setTextColor(cx_text_fj.getResources().getColor(R.color.color_blue));
                cx_text_sc.setTextColor(cx_text_sc.getResources().getColor(R.color.hint));
         	Activity forward_activity = localActivityManager.getActivity(NearbyActivity);
            	
       	resumeActivity(forward_activity,NearbyActivity.class,NearbyActivity);
        			
            }
        });
        //我的排号
        menu_sc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_ss.setBackgroundResource(R.drawable.menu_1);
                menu_fl.setBackgroundResource(R.drawable.menu_4);
                menu_fj.setBackgroundResource(R.drawable.menu_2);
                menu_sc.setBackgroundResource(R.drawable.menu_33);
                cx_text_ss.setTextColor(cx_text_ss.getResources().getColor(R.color.hint));
                cx_text_fl.setTextColor(cx_text_fl.getResources().getColor(R.color.hint));
                cx_text_fj.setTextColor(cx_text_fj.getResources().getColor(R.color.hint));
                cx_text_sc.setTextColor(cx_text_sc.getResources().getColor(R.color.color_blue));
            	Activity forward_activity = localActivityManager.getActivity(MyNumberActivity);
            	
        		resumeActivity(forward_activity,MyNumberActivity.class,MyNumberActivity);
            }
        });
        
        layoutmenu_ss.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		
        		menu_ss.setBackgroundResource(R.drawable.menu_11);
        		menu_fl.setBackgroundResource(R.drawable.menu_4);
        		menu_fj.setBackgroundResource(R.drawable.menu_2);
        		menu_sc.setBackgroundResource(R.drawable.menu_3);
        		cx_text_ss.setTextColor(cx_text_ss.getResources().getColor(R.color.color_blue));
        		cx_text_fl.setTextColor(cx_text_fl.getResources().getColor(R.color.hint));
        		cx_text_fj.setTextColor(cx_text_fj.getResources().getColor(R.color.hint));
        		cx_text_sc.setTextColor(cx_text_sc.getResources().getColor(R.color.hint));
        		Activity forward_activity = localActivityManager.getActivity(IndexGalleryActivity);
        		
        		resumeActivity(forward_activity,IndexGalleryActivity.class,IndexGalleryActivity);
        		
        	}
        });
        
        layoutmenu_fl.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		
        		menu_ss.setBackgroundResource(R.drawable.menu_1);
        		menu_fl.setBackgroundResource(R.drawable.menu_44);
        		menu_fj.setBackgroundResource(R.drawable.menu_2);
        		menu_sc.setBackgroundResource(R.drawable.menu_3);
        		cx_text_ss.setTextColor(cx_text_ss.getResources().getColor(R.color.hint));
        		cx_text_fl.setTextColor(cx_text_fl.getResources().getColor(R.color.color_blue));
        		cx_text_fj.setTextColor(cx_text_fj.getResources().getColor(R.color.hint));
        		cx_text_sc.setTextColor(cx_text_sc.getResources().getColor(R.color.hint));
        		Activity forward_activity = localActivityManager.getActivity(ShakeActivity);
        		
        		resumeActivity(forward_activity,ShakeActivity.class,ShakeActivity);
        		
        	}
        });
        layoutmenu_fj.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		//               Intent it = new Intent(context, NearbyActivity.class);
        		//               startActivity(it);
        		
        		menu_ss.setBackgroundResource(R.drawable.menu_1);
        		menu_fl.setBackgroundResource(R.drawable.menu_4);
        		menu_fj.setBackgroundResource(R.drawable.menu_22);
        		menu_sc.setBackgroundResource(R.drawable.menu_3);
        		cx_text_ss.setTextColor(cx_text_ss.getResources().getColor(R.color.hint));
        		cx_text_fl.setTextColor(cx_text_fl.getResources().getColor(R.color.hint));
        		cx_text_fj.setTextColor(cx_text_fj.getResources().getColor(R.color.color_blue));
        		cx_text_sc.setTextColor(cx_text_sc.getResources().getColor(R.color.hint));
        		Activity forward_activity = localActivityManager.getActivity(NearbyActivity);
        		
        		resumeActivity(forward_activity,NearbyActivity.class,NearbyActivity);
        		
        	}
        });
        //我的排号
        layoutmenu_sc.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		menu_ss.setBackgroundResource(R.drawable.menu_1);
        		menu_fl.setBackgroundResource(R.drawable.menu_4);
        		menu_fj.setBackgroundResource(R.drawable.menu_2);
        		menu_sc.setBackgroundResource(R.drawable.menu_33);
        		cx_text_ss.setTextColor(cx_text_ss.getResources().getColor(R.color.hint));
        		cx_text_fl.setTextColor(cx_text_fl.getResources().getColor(R.color.hint));
        		cx_text_fj.setTextColor(cx_text_fj.getResources().getColor(R.color.hint));
        		cx_text_sc.setTextColor(cx_text_sc.getResources().getColor(R.color.color_blue));
        		Activity forward_activity = localActivityManager.getActivity(MyNumberActivity);
        		
        		resumeActivity(forward_activity,MyNumberActivity.class,MyNumberActivity);
        	}
        });
    }
    

    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0,2,2,"注销");
    	menu.add(0,MENU_EXIT,1,"退出");
    	return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==2){
			showDialog(Const.DIALOG_YES_NO_MESSAGE);
		}
		switch(item.getItemId()){
			case MENU_EXIT:
				this.finish();
				System.exit(0);
				
				break;
		}
    	return super.onOptionsItemSelected(item);
    }
    
    protected Dialog onCreateDialog(int id) {
    	 switch (id) {
	         case Const.DIALOG_YES_NO_MESSAGE:
	        	 return new AlertDialog.Builder(context)
	             .setTitle("确定注销账号？")//设置对话框的标题
	             .setPositiveButton("确定", new DialogInterface.OnClickListener() {//设置按下表示确定按钮时按钮的text，和按钮的事件监听器
					public void onClick(DialogInterface dialog, int whichButton) {
	                	 finish();
	                	 //PollingUtils.stopPollingService(CommActivityGroup.this, PollingService.class, PollingService.ACTION);
//	                	 removeDialog(Const.DIALOG_YES_NO_MESSAGE);
//	                	 ATManager.exitClient(context);
	                 }
	             })
	             .setNegativeButton("取消", new DialogInterface.OnClickListener() {//设置取消按钮的text 和监听器
					public void onClick(DialogInterface dialog, int whichButton) {
	                	 dialog.dismiss();
	                 }
	             })
	             .create();
    	 }

     	return null;
    }

   
		
}
