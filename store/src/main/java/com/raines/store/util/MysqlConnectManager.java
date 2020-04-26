package com.raines.store.util;

import net.sf.json.JSONObject;

import java.sql.*;
import java.util.*;

public class MysqlConnectManager {

	private static ResourceBundle rb = ResourceBundle.getBundle("systemParamters");
	private static String driver = rb.getString("mysql.driverClass");
	private static String url = rb.getString("mysql.jdbcUrl");
	private static String username = rb.getString("mysql.user");
	private static String password = rb.getString("mysql.password");
	
	private volatile static MysqlConnectManager singleton = null;
	private static Connection con = null;
	
	private MysqlConnectManager() {
		try {			
			Class.forName(driver);
			con = getConn();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MysqlConnectManager getInstance() {
		if (singleton == null) {
			synchronized (MysqlConnectManager.class) {
				if (singleton == null) {
					singleton = new MysqlConnectManager();
				}
			}
		}
		return singleton;
	}
	
	public Connection getConn(){
		if(con == null){
			try {
				con = DriverManager.getConnection(url, username, password);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	
	
	public void closeConn(){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void executeMysql(String sql, Boolean closeConn){
		getConn();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(closeConn)
			closeConn();
	}
	
	public List<Map<String, Object>> queryMysql(String columns, String tableName, String whereSql, Boolean closeConn){
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		getConn();
		try {
			String sql = "select "+columns+" from "+tableName;
			if(!StringTool.isEmpty(whereSql)){
				sql += " where "+whereSql;
			}
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
		    while (rs.next()) {
		    	Map<String, Object> ele = new HashMap<String, Object>();
				
				int count = 1;
				for(String s : columns.split(",")){
					if(s.contains(" as ")){
						ele.put(s.split(" as ")[1], rs.getObject(count++));
					}else{
						ele.put(s, rs.getObject(count++));
					}
				}
				res.add(ele);	
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(closeConn)
			closeConn();
		return res;
	}
	
	public List<String> querySingleField(String column, String tableName, String whereSql, Boolean closeConn){
		List<String> res = new ArrayList<String>();
		getConn();
		try {
			String sql = "select "+column+" from "+tableName;
			if(!StringTool.isEmpty(whereSql)){
				sql += " where "+whereSql;
			}
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
		    while (rs.next()) {	
		    	res.add(rs.getObject(1).toString());	
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(closeConn)
			closeConn();
		return res;
	}
	
	public String querySingleFieldToString(String column, String tableName, String whereSql, Boolean closeConn){
		StringBuffer sb = new StringBuffer();
		getConn();
		try {
			String sql = "select "+column+" from "+tableName;
			if(!StringTool.isEmpty(whereSql)){
				sql += " where "+whereSql;
			}
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
		    while (rs.next()) {	
		    	sb.append(rs.getObject(1).toString());	
		    	sb.append(";");
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(closeConn)
			closeConn();
		return sb.toString();
	}
	
	public Map<String,JSONObject> listAllData(String pkColumn, String otherColumns, String tableName, String whereSql, Boolean closeConn) throws Exception {
		
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
	    if(closeConn)
	    	closeConn();
		
		return _map;
	}
}
