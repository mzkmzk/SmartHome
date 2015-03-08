package com.pac.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.pac.bean.TestKongTiao;
import com.pac.bean.device;
import com.pac.model.dao.KongTiaoDao;

public class queryKongTiao implements Action{

	private int id ;
	private String status;
	
	private KongTiaoDao ktd =new KongTiaoDao();
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		device device_  = ktd.queryOneDevice(Integer.parseInt(request.getParameter("deviceid")));
		status =device_.getStatus()+"";
		HttpServletResponse  hr = ServletActionContext.getResponse();
		Cookie ccc =new Cookie(device_.getDeviceid()+"", device_.getStatus()+"");
		hr.addCookie(ccc);           
		//System.out.println("设备id :" +device_.getDeviceid()+"传递参数 :"+device_.getStatus());
		return SUCCESS;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
