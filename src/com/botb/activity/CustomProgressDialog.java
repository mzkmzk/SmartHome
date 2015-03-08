package com.botb.activity;

import com.botb.activity.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomProgressDialog extends Dialog {
	private Context context = null;
	private static CustomProgressDialog customProgressDialog = null;
	
	public CustomProgressDialog(Context context, String strMessage){
		this(context, R.style.CustomProgressDialog, strMessage); 
	}
	
	public CustomProgressDialog(Context context, int theme, String strMessage) {
        super(context, theme);
        Log.i("加载页面", "zzzzz");
        this.setContentView(R.layout.customprogressdialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
		Log.i("TextView", strMessage);
    	TextView tvMsg = (TextView)this.findViewById(R.id.id_tv_loadingmsg);
    	
    	if (tvMsg != null){
    		tvMsg.setText(strMessage);
    	}
    }
	
    public void onWindowFocusChanged(boolean hasFocus){
    	
    	if (!hasFocus) {  
            dismiss();  
        } 
    	Log.i("动画", "zzzzzz");
        ImageView imageView = (ImageView) this.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }
}
