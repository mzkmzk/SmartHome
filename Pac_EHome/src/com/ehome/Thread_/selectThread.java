package com.ehome.Thread_;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ehome.utils.ModelServer;

public class selectThread implements Runnable {

	public int deviceid;
	public String status;
	@Override
	public void run() {
		Log.i("12345"," 设备号 :"+deviceid);
		   List<NameValuePair> params = new ArrayList<NameValuePair>();
		   //添加设备号
		   params.add(new BasicNameValuePair("deviceid",deviceid+""));
		   ModelServer modelHttp =new ModelServer();
		   Map<Boolean,String> result = modelHttp.Model_server(params, "queryKongTiao");
		   try {
			JSONObject json= new JSONObject(result.get(true));
			status =json.getString("status");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
