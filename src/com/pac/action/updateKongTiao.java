package com.pac.action;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.pac.bean.TestKongTiao;
import com.pac.bean.device;
import com.pac.model.dao.KongTiaoDao;

public class updateKongTiao implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6012314L;
	private int id ;
	private int msg ;
	private String ii;
	private KongTiaoDao ktd =new KongTiaoDao();
	
	public String execute() throws Exception {
		
		System.out.println("修改参数  Micudino 连接服务器成功");
		HttpServletRequest request =ServletActionContext.getRequest();
		 device device_ =new device();
		 device_.setDeviceid(Integer.parseInt(request.getParameter("deviceid")));
		 device_.setStatus(Integer.parseInt(request.getParameter("status")));
		 System.out.println("设备id :" +device_.getDeviceid()+"传递参数 :"+device_.getStatus());
		ktd.updateDevice(device_);
		
		
		/*System.out.println(request.getProtocol());//获取请求使用的通信协议，如http/1.1等 
		System.out.println(request.getServletPath());//获取请求的JSP也面所在的目录。 
		System.out.println(request.getContentLength());//获取HTTP请求的长度。 
		System.out.println(request.getMethod());//获取表单提交信息的方式，如POST或者GET。 
		//request.getHeader(String s);//:获取请求中头的值。一般来说，S参数可取的头名有accept,referrer、accept-language、content-type、accept-encoding、user-agent、host、cookie等，比如，S取值user-agent将获得用户的浏览器的版本号等信息。 
		System.out.println(request.getHeaderNames());//:获取头名字的一个枚举。 
		//request.getHeaders(String s);//:获取头的全部值的一个枚举。 
		System.out.println(request.getRemoteAddr());//:获取客户的IP地址。 
		System.out.println(request.getRemoteHost());//:获取客户机的名称（如果获取不到，就获取IP地址）。 
		System.out.println(request.getServerName());//:获取服务器的名称。 
		//request.getServePort();//:获取服务器的端口。 
		System.out.println(request.getParameterNames());
		Cookie[] cc = request.getCookies();
		HttpServletResponse  hr = ServletActionContext.getResponse();
		Cookie ccc =new Cookie("wahah", "wahshhs");
		hr.addCookie(ccc);                     
		Enumeration enu=request.getParameterNames();
		while(enu.hasMoreElements()){
		String paraName=(String)enu.nextElement();
		System.out.println(paraName+": "+request.getParameter(paraName));
		} 
		
		msg=1;
		ii="3";*/
		return SUCCESS;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMsg() {
		return msg;
	}
	public void setMsg(int msg) {
		this.msg = msg;
	}
	public String getIi() {
		return ii;
	}
	public void setIi(String ii) {
		this.ii = ii;
	}
	
}
