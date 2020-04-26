package com.raines.store.util;

import net.sf.json.JSONObject;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 链接Hive Impala工具类
 * @author T450s
 *
 */
public class HiveConnectManager {

	private volatile static HiveConnectManager singleton = null;
	private static ResourceBundle rb = null;
	private static Connection con = null;
	
	private HiveConnectManager() {
		try {
			
			rb = ResourceBundle.getBundle("systemParamters");
			Class.forName("org.apache.hive.jdbc.HiveDriver");
			con = getConn();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HiveConnectManager getInstance() {
		if (singleton == null) {
			synchronized (HiveConnectManager.class) {
				if (singleton == null) {
					singleton = new HiveConnectManager();
				}
			}
		}
		return singleton;
	}
	
	public Connection getConn(){
		if(con == null){
			
			try {
				con = DriverManager.getConnection(rb.getString("impalaUrl"), "", "");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	
	public  void closeConn(){
		if(con != null){
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void executeImpala(String sql){
		getConn();
		try {
			Statement stmt = con.createStatement();
		    stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeConn();
	}
	
	public Map<String,JSONObject> listAllData(String pkColumn, String otherColumns, String tableName, String whereSql) throws Exception {
		
		getConn();
		Map<String,JSONObject> _map = new LinkedHashMap<String,JSONObject>();
		
		String sql = "select "+pkColumn+","+otherColumns+" from "+tableName;
		if(!StringTool.isEmpty(whereSql)){
			sql += " where "+whereSql;
		}
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
	    while (rs.next()) {
	    	JSONObject jsObject = new JSONObject();
			if(pkColumn.contains(" as ")){
				jsObject.put(pkColumn.split(" as ")[1], rs.getObject(1));
			}else{
				jsObject.put(pkColumn, rs.getObject(1));
			}
			
			int count = 1;
			for(String s : otherColumns.split(",")){
				if(s.contains(" as ")){
					jsObject.put(s.split(" as ")[1], rs.getObject(++count));
				}else{
					jsObject.put(s, rs.getObject(++count));
				}
			}
			_map.put(rs.getObject(1).toString(), jsObject);
	    	
	    }
	    closeConn();
		
		return _map;
	}
	
}
