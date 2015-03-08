package com.pac.model.dao;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.pac.bean.device;
import com.pac.util.DBUtil;


/**
 * TeamDao
 * @author K
 *
 */
public class KongTiaoDao {
	 private DBUtil util;
		
		public KongTiaoDao(){
			util = DBUtil.getInstance();
		}
		
		/**
		 * 查询设备当前情况
		 * @return
		 */
		public device  queryOneDevice(int deviceid ){
			Connection conn  = null;
	    	PreparedStatement ps=null;
			ResultSet rs=null;
			String 	 sql ="select * from device where deviceid =? ";
			device tp =new device();
			try {
				conn= util.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, deviceid);
				rs =ps.executeQuery();
				while(rs.next()){
				      tp.setDeviceid(deviceid);
				      tp.setStatus(rs.getInt("status"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn,  ps, rs);
			}
			return tp;
		}
		
		    /**
		    * 更改状态
		    */
		   public int updateDevice(device deviceid_ ){
			   Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				String sql =null;
				int flag = 0;
				try {
					conn = util.getConnection();
						 sql = " update device set   status=? where deviceid=?";
						 stmt = conn.prepareStatement(sql);
						 stmt.setInt(1, deviceid_.getStatus());
						 stmt.setInt(2, deviceid_.getDeviceid());
					flag = stmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					util.free(conn, stmt, rs);
				}
				return flag;
		   }
		   
		  
			
			
}
