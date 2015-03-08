package com.botb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.botb.Util.NetWorkUtils;
import com.botb.activity.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public abstract class BaseActivity extends FragmentActivity {
	protected NetWorkUtils mNetWorkUtils;
	//protected FlippingLoadingDialog mLoadingDialog;

	/**
	 * 屏幕的宽度、高度、密度
	 */
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected float mDensity;

	protected List<AsyncTask<Void, Void, Boolean>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNetWorkUtils = new NetWorkUtils(this);
		//mLoadingDialog = new FlippingLoadingDialog(this, "请求提交中");

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		mDensity = metric.density;
	}

	@Override
	protected void onDestroy() {
		clearAsyncTask();
		super.onDestroy();
	}

	/** 初始化视图 **/
	protected abstract void initViews();

	/** 初始化事件 **/
	protected abstract void initEvents();

	protected void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
		mAsyncTasks.add(asyncTask.execute());
	}

	protected void clearAsyncTask() {
		Iterator<AsyncTask<Void, Void, Boolean>> iterator = mAsyncTasks
				.iterator();
		while (iterator.hasNext()) {
			AsyncTask<Void, Void, Boolean> asyncTask = iterator.next();
			if (asyncTask != null && !asyncTask.isCancelled()) {
				asyncTask.cancel(true);
			}
		}
		mAsyncTasks.clear();
	}

//	protected void showLoadingDialog(String text) {
//		if (text != null) {
//			mLoadingDialog.setText(text);
//		}
//		mLoadingDialog.show();
//	}
//
//	protected void dismissLoadingDialog() {
//		if (mLoadingDialog.isShowing()) {
//			mLoadingDialog.dismiss();
//		}
//	}

//	/** 短暂显示Toast提示(来自res) **/
//	protected void showShortToast(int resId) {
//		Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
//	}
//
//	/** 短暂显示Toast提示(来自String) **/
//	protected void showShortToast(String text) {
//		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//	}

//	/** 长时间显示Toast提示(来自res) **/
//	protected void showLongToast(int resId) {
//		Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
//	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
	// 自定义Toast信息显示
	protected void showCustomerToast(int icon, String message){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_customer, (ViewGroup) findViewById(R.id.toast_layout_root));
		
		ImageView toastIcon = (ImageView) layout.findViewById(R.id.toastIcon);
		toastIcon.setBackgroundResource(icon);
		
		TextView toastMessage = (TextView) layout.findViewById(R.id.toastMessage);
		toastMessage.setText(message);
		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

//	/** 显示自定义Toast提示(来自res) **/
//	protected void showCustomToast(int resId) {
//		View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(
//				R.layout.common_toast, null);
//		((HandyTextView) toastRoot.findViewById(R.id.toast_text))
//				.setText(getString(resId));
//		Toast toast = new Toast(BaseActivity.this);
//		toast.setGravity(Gravity.CENTER, 0, 0);
//		toast.setDuration(Toast.LENGTH_SHORT);
//		toast.setView(toastRoot);
//		toast.show();
//	}
//
	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(
				R.layout.common_toast, null);
		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(BaseActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}
//
//	/** Debug输出Log日志 **/
//	protected void showLogDebug(String tag, String msg) {
//		Log.d(tag, msg);
//	}
//
//	/** Error输出Log日志 **/
//	protected void showLogError(String tag, String msg) {
//		Log.e(tag, msg);
//	}

	/** 通过Class跳转界面 **/
	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/** 含有Bundle通过Class跳转界面 **/
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/** 通过Action跳转界面 **/
	protected void startActivity(String action) {
		startActivity(action, null);
	}

	/** 含有Bundle通过Action跳转界面 **/
	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

//	/** 含有标题和内容的对话框 **/
//	protected AlertDialog showAlertDialog(String title, String message) {
//		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
//				.setMessage(message).show();
//		return alertDialog;
//	}
//
//	/** 含有标题、内容、两个按钮的对话框 **/
//	protected AlertDialog showAlertDialog(String title, String message,
//			String positiveText,
//			DialogInterface.OnClickListener onPositiveClickListener,
//			String negativeText,
//			DialogInterface.OnClickListener onNegativeClickListener) {
//		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
//				.setMessage(message)
//				.setPositiveButton(positiveText, onPositiveClickListener)
//				.setNegativeButton(negativeText, onNegativeClickListener)
//				.show();
//		return alertDialog;
//	}
//
//	/** 含有标题、内容、图标、两个按钮的对话框 **/
//	protected AlertDialog showAlertDialog(String title, String message,
//			int icon, String positiveText,
//			DialogInterface.OnClickListener onPositiveClickListener,
//			String negativeText,
//			DialogInterface.OnClickListener onNegativeClickListener) {
//		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
//				.setMessage(message).setIcon(icon)
//				.setPositiveButton(positiveText, onPositiveClickListener)
//				.setNegativeButton(negativeText, onNegativeClickListener)
//				.show();
//		return alertDialog;
//	}
//
//	/** 默认退出 **/
//	protected void defaultFinish() {
//		super.finish();
//	}
}
