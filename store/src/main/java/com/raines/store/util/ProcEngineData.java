package com.raines.store.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class ProcEngineData {

	/**
	 * 处理Rtime
	 * @param rtime
	 * @return
	 */
	public static String procRtime(String rtime) {
			rtime =rtime.replace("/", "-");
			if(rtime.indexOf("-")!=-1) {
				String[] times1 = rtime.split(" ");
				String[] times2 = times1[0].split("-");
				if (times2[1].length()==1) 
					times2[1] = "0"+times2[1];
				if (times2[2].length()==1) 
					times2[2] = "0"+times2[2];	
				String[] hms = times1[1].split(":");
				if (hms[0].length()==1) 
					hms[0] = "0"+hms[0];
				if (hms[1].length()==1) 
					hms[1] = "0"+hms[1];	
				if (hms[2].length()==1) 
					hms[2] = "0"+hms[2];	
				rtime = StringUtils.join(times2, "-")+" "+StringUtils.join(hms, ":");
			}
		
		return rtime;
	}
	
	/**
	 * 获取时间戳
	 * @param rowKey
	 * @return
	 */
	public static long getCtimeByRowKey(String rowKey) {
		return Long.valueOf(rowKey.substring(8,18));
	}
	
	/**
	 * 根据百度坐标获取行政区划
	 * @param lng
	 * @param lat
	 * @param sourceXzqhId 数据原始xzqhid
	 * @return
	 */
	public static String procGeoToXzqh(String lng, String lat, String sourceXzqhId) {
		String xzqhid = "999999";
		JSONObject object = IsPtInPolyClass.checkPoly(lng,lat, sourceXzqhId);
		if(object != null) {
			xzqhid = object.get("XZQH_ID").toString();
		}
		return xzqhid;
	}
	/**
	 * 处理 累计启动时间
	 * @param totalstarttime
	 * @return
	 */
	public static double procTotalStartTime(String totalstarttime) {
		double ret=0d;
		if(StringUtils.isBlank(totalstarttime)) {
			ret=0d;
		}else {
			totalstarttime =totalstarttime.trim();
			if(!StringTool.isDouble(totalstarttime)) {
				ret=0d;
			}else {
				double d = Double.parseDouble(totalstarttime);
				if(d < 0) {
					ret=0d;
				}else {
					ret = d;
				}
			}
		}
		return ret;
	}
	

	/**
	 * 处理发动机转速
	 * @param enginespeed
	 * @return
	 */
	public static double procEngineSpeed(String enginespeed) {
		double ret=0d;
		if(StringUtils.isBlank(enginespeed)) {
			ret=0d;
		}else {
			enginespeed =enginespeed.trim();
			if(!StringTool.isDouble(enginespeed)) {
				ret=0d;
			}else {
				double d = Double.parseDouble(enginespeed);
				if(d < 0 || d > 8031.875) {
					ret=0d;
				}else {
					ret = d;
				}
			}
		}
		return ret;
	}

	/**
	 * 通过行政区划名称获取到 行政区划编号
	 * @param province
	 * @param city
	 * @param area
	 * @return
	 */
	public static String getXzqhCodeFormName(String province, String city, String county) {
		String sourcexzqhid = "999999";
		
		JSONObject xzqh = IsPtInPolyClass.getXzqhCodeFormName(province, city, county);
		if(xzqh!=null) {
			sourcexzqhid = xzqh.getString("XZQH_ID");
		}
		
		return sourcexzqhid;
	}
	
	/**
	 * 处理 油耗
	 * @param oilwear
	 * @return
	 */
	public static double procOilwear(String oilwear) {
		double ret=0d;
		if(StringUtils.isBlank(oilwear)) {
			ret=0d;
		}else {
			oilwear =oilwear.trim();
			if(!StringTool.isDouble(oilwear)) {
				ret=0d;
			}else {
				double d = Double.parseDouble(oilwear);
				if(d < 0) {
					ret= 0d;
				}else {
					ret = d;
				}
			}
		}
		return ret;
	}
	/**
	 * 处理 燃油总消耗量
	 * @param oilwear
	 * @return
	 */
	public static double procFuelConsumption(String fuelconsumption) {
		double ret=0d;
		if(StringUtils.isBlank(fuelconsumption)) {
			ret=0d;
		}else {
			fuelconsumption =fuelconsumption.trim();
			if(!StringTool.isDouble(fuelconsumption)) {
				ret=0d;
			}else {
				double d = Double.parseDouble(fuelconsumption);
				if(d<0) {
					ret=0d;
				}else {
					ret = d;
				}
			}
		}
		return ret;
	}
	/**
	 * 处理  海拔高度
	 * @param height
	 * @return
	 */
	public static int procHeight(String height) {
		int ret=0;
		if(StringUtils.isBlank(height)) {
			ret= 0;
		}else {
			height =height.trim();
			
			if(!StringTool.isInt(height)) {
				ret= 0;
			}else {
				ret = Integer.parseInt(height);
			}
		}
		return ret;
	}
	/**
	 * 处理  精度纬度共用
	 * @param height
	 * @return
	 */
	public static String procLngLat(String lngOrLat) {
		String ret="";
		if(StringUtils.isBlank(lngOrLat)) {
			ret="0.0";
		}else {
			lngOrLat =lngOrLat.trim();
			if(!StringTool.isDouble(lngOrLat)) {
				ret="0.0";
			}else {
				ret =lngOrLat;
			}
		}
		return ret;
	}
	
	public static void main(String[] args) {
		System.out.println(ProcEngineData.procGeoToXzqh("116.423161", "39.934165", "999999"));
	}
}
