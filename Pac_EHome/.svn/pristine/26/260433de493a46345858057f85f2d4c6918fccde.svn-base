package com.ehome.Thread_;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.ehome.utils.ModelServer;

public class updateThread implements Runnable{

	public int deviceid;
	public String status;
	
	@Override
	public void run() {
		Log.i("12345","传过去的码是 : "+status+" 设备号 :"+deviceid);
		   List<NameValuePair> params = new ArrayList<NameValuePair>();
		   //添加设备号和状态传输
		   params.add(new BasicNameValuePair("deviceid",deviceid+""));
		   params.add(new BasicNameValuePair("status",status+""));
		   ModelServer modelHttp =new ModelServer();
		   modelHttp.Model_server(params, "updateKongTiao");
	}

	

	public int getDeviceid() {
		return deviceid;
	}



	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}
