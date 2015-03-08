package com.botb.activity.register;

import com.botb.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**注册验证界面activity*/
public class RegisterConfirmActivity extends Activity implements OnClickListener{
	private Button btn_reg_reget,btn_title_left,btn_reg_subreg;
	private TextView tv_reg_reget,tv_top_title;
	private MyCount mc;
	private EditText txt_reg_vercode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_confirm);
		initView();
	}

	private void initView() {
		tv_reg_reget = (TextView) findViewById(R.id.tv_reg_reget);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_title.setText("注册账号");
		mc = new MyCount(30000, 1000);
		mc.start();
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_left.setOnClickListener(this);
		//btn_title_right = (Button) findViewById(R.id.btn_title_right);
		//btn_title_right.setVisibility(View.GONE);
		btn_reg_reget = (Button) findViewById(R.id.btn_reg_reget);
		btn_reg_reget.setOnClickListener(this);

		btn_reg_subreg = (Button) findViewById(R.id.btn_reg_subreg);
		btn_reg_subreg.setOnClickListener(this);
		
		txt_reg_vercode= (EditText) findViewById(R.id.txt_reg_vercode);
	}
	
	/**自定义一个继承CountDownTimer的内部类，用于实现计时器的功能*/
	class MyCount extends CountDownTimer{
		/**
		 * MyCount的构造方法
		 * @param millisInFuture 要倒计时的时间
		 * @param countDownInterval 时间间隔
		 */
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTick(long millisUntilFinished) {//在进行倒计时的时候执行的操作
			long second = millisUntilFinished /1000;
			tv_reg_reget.setText(second+"秒后可以重新获得验证码");
			if(second == 30){
				tv_reg_reget.setText(29+"秒后可以重新获得验证码");
			}
			Log.i("PDA", millisUntilFinished/1000+"");
			
		}

		@Override
		public void onFinish() {//倒计时结束后要做的事情
			// TODO Auto-generated method stub
			tv_reg_reget.setVisibility(View.GONE);
			btn_reg_reget.setVisibility(View.VISIBLE);
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_reg_reget:
			mc.start();
			tv_reg_reget.setVisibility(View.VISIBLE);
			btn_reg_reget.setVisibility(View.GONE);
			break;
		case R.id.btn_title_left:
			RegisterConfirmActivity.this.finish();
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

			break;
		case R.id.btn_reg_subreg:
			String vercode = txt_reg_vercode.getText().toString().trim();
					
			if("123456".equals(vercode)){
				showCustomerToast(R.drawable.btn_check_buttonless_on, "验证码正确");
				Intent intent= new Intent(RegisterConfirmActivity.this,RegisterMessageActivity.class);
				Bundle extras = getIntent().getExtras();
				intent.putExtra("Text3", extras.getInt("Text2"));
				intent.putExtra("phone", extras.getString("phone"));
				startActivityForResult(intent, 3);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

			}else{
				showCustomerToast(android.R.drawable.ic_delete, "验证码错误，请重新验证");
			}
			break;
		}
	}
	
	private void showCustomerToast(final int icon, final String message){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_customer, (ViewGroup) findViewById(R.id.toast_layout_root));
		
		ImageView toastIcon = (ImageView) layout.findViewById(R.id.toastIcon);
		toastIcon.setBackgroundResource(icon);
		
		TextView toastMessage = (TextView) layout.findViewById(R.id.toastMessage);
		toastMessage.setText(message);
		Toast toast = new Toast(getApplicationContext());
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}



}
