package com.botb.activity.register;

import com.botb.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class RegisterChooseActivity extends Activity implements OnClickListener {

	private TextView tv_top_title;
	private Button btn_title_left,btn_reg_per,btn_reg_com;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_choose);
		initView();
	}
	
	private void initView(){
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_title.setText("排号助手注册");
		
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_left.setOnClickListener(this);
		
		btn_reg_com=(Button) findViewById(R.id.btn_reg_com);
		btn_reg_com.setOnClickListener(this);
		
		btn_reg_per=(Button) findViewById(R.id.btn_reg_per);
		btn_reg_per.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(RegisterChooseActivity.this, RegisterActivity.class);
		switch (v.getId()) {
		case R.id.btn_title_left:
			RegisterChooseActivity.this.finish();
			break;
		case R.id.btn_reg_com:
			intent.putExtra("Text", 1);
			startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

			break;
		case R.id.btn_reg_per:
			intent.putExtra("Text", 2);
			startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

			break;
		default:
			break;
		}
	}
		
}
