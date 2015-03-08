package com.ehome;


import android.os.Bundle;
import android.os.Handler;

import com.ehome.R;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {
	
	EditText et_Num, et_Pwd;
	private Button mdenglu,mzhuce;// 
	private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initViews();
		initEvents();
		
		mdenglu = (Button) findViewById(R.id.login_btn_login);
		mdenglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				login();
			}
		});
		
/*		mzhuce = (Button) findViewById(R.id.login_register);
		mzhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(LoginActivity.this,
						IndexActivity.class));
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

				//overridePendingTransition(android.R.anim.slide_out_right,android.R.anim.slide_in_left);
			}
		});	*/	
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		this.et_Num = (EditText) this.findViewById(R.id.login_et_account);
		this.et_Pwd = (EditText) this.findViewById(R.id.login_et_pwd);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private void login(){
		String name = et_Num.getText().toString();
		String password = et_Pwd.getText().toString();

		if (TextUtils.isEmpty(name)) {
			showCustomToast(R.string.toast_error_username_null);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			showCustomToast(R.string.toast_error_password_null);
			return;
		}

		final CustomProgressDialog progress = new CustomProgressDialog(LoginActivity.this,"正在登录中...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();

		
		new Handler().postDelayed(new Runnable(){    
	          
            @Override   
            public void run() {    
            	progress.dismiss();
				startActivity(new Intent(LoginActivity.this,IndexActivity.class));
        		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        		finish();    
            }    
                   
           }, SPLASH_DISPLAY_LENGHT);   
		
	}
}
