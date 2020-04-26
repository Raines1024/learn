package com.raines.store.util;

import net.sf.json.JSONObject;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 行政区划坐标位置判定
 * @author persisted.luck
 * @date 2018年9月12日 下午4:28:17
 *
 */
public class IsPtInPolyClass {
	
	public static Map<String,JSONObject> scXzqh = new LinkedHashMap<String,JSONObject>();
	public static Map<String,JSONObject> scXzqhNickKey = new LinkedHashMap<String,JSONObject>();
	public static Map<String, List<List<Point2D.Double>>> xzqhArea = new LinkedHashMap<String, List<List<Point2D.Double>>>();
	public static Map<String, List<List<Point2D.Double>>> xzqhAreaDepth1 = new LinkedHashMap<String, List<List<Point2D.Double>>>();
	public static Map<String, List<List<Point2D.Double>>> xzqhAreaDepth2 = new LinkedHashMap<String, List<List<Point2D.Double>>>();
	public static Map<String, List<List<Point2D.Double>>> xzqhAreaDepth3 = new LinkedHashMap<String, List<List<Point2D.Double>>>();
	
/*	static {
		try {

			scXzqh = HiveConnectManager.getInstance().listAllData("XZQH_ID", "XZQH_NAME,XZQH_NICK,XZQH_DEPTH,lng_baidu,lat_baidu,XZQH_JC,area_baidu,xzqh_alias", "engine_1819_pro.SC_XZQH", null);
			
			for(String key:scXzqh.keySet()) {
				JSONObject xzqh = scXzqh.get(key);
				if(xzqh.containsKey("area_baidu")) {
					int xzqhDepth = xzqh.getInt("XZQH_DEPTH");
					String listStr = xzqh.getString("area_baidu");
					JSONArray _areas = JSONArray.fromObject(listStr);
					List<List<Point2D.Double>> _areaPoints= new ArrayList<List<Point2D.Double>>();
					for(int i=0;i<_areas.size();i++) {
						_areaPoints.add(getListFromStrBaidu(_areas.getString(i)));
					}
					if(xzqhDepth==1) {
						xzqhAreaDepth1.put(key, _areaPoints);
					}else if(xzqhDepth==2) {
						xzqhAreaDepth2.put(key, _areaPoints);
					}else if(xzqhDepth==3) {
						xzqhAreaDepth3.put(key, _areaPoints);
					}
					xzqhArea.put(key, _areaPoints);
				}
				scXzqhNickKey.put(xzqh.getString("XZQH_NICK"), xzqh);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	

	/**
	 * 两种个优化方法：
	 * 1）先算出 当前坐标 与 每个行政区划中心点，距离最近的 行政区划，然后组成List去遍历，减少循环次数。
	 * 2）在循环List<Point2D.Double> pts的过程中，降低 坐标密度，减少循环次数。
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
//		System.out.println(checkPoly(new Point2D.Double(Double.valueOf("103.84567"),Double.valueOf("30.80881"))));	
//		long t = System.currentTimeMillis();
//		System.out.println(IsPtInPolyClass.checkPoly("116.314248", "40.061256")); // 海淀区
		System.out.println(IsPtInPolyClass.checkPoly("116.423161", "39.934165", "999999")); // 东城区
		
/*		System.out.println(System.currentTimeMillis() - t);
		System.out.println(IsPtInPolyClass.checkPoly("103.84567", "30.80881",null)); // 四川省成都市郫县
//		System.out.println(IsPtInPolyClass.checkPoly("87.844041", "47.34844", "654301")); // 阿勒泰市
//		System.out.println(IsPtInPolyClass.checkPoly("103.84567", "30.80881")); // 四川省成都市郫县
		System.out.println(IsPtInPolyClass.checkPoly("87.844041", "47.34844", "654301")); // 阿勒泰市
//		System.out.println(IsPtInPolyClass.checkPoly("103.84567", "30.80881")); // 四川省成都市郫县
		System.out.println(IsPtInPolyClass.checkPoly("87.844041", "47.34844", "654300")); // 阿勒泰市
		
		System.out.println(System.currentTimeMillis() - t);*/
		
//		System.out.println(IsPtInPolyClass.getXzqhCodeFormName("北京市", "北京市", "昌平区"));
//		System.out.println(IsPtInPolyClass.getXzqhCodeFormName("山西省", "太原市", ""));
//		System.out.println(System.currentTimeMillis() - t);
	}
	
	public static JSONObject checkPoly(String lng, String lat, String xzqhId){
		Point2D.Double point = new Point2D.Double(Double.valueOf(lng), Double.valueOf(lat));
		JSONObject result = new JSONObject();
		
		if(!StringTool.isEmpty(xzqhId)) {
			if(xzqhArea.containsKey(xzqhId)) {
				List<List<Point2D.Double>> _areaPoints = xzqhArea.get(xzqhId);
				for(List<Point2D.Double> _a:_areaPoints) {
					if(IsPtInPoly(point, _a)) {
						result = scXzqh.get(xzqhId);
						break;
					}
				}
			}
		}
		if(result.size()>0) {
			int xzqh_depth = result.getInt("XZQH_DEPTH");
			if(xzqh_depth==1) {
				String prevXzqh = result.getString("XZQH_ID");
				String xzqhPix = prevXzqh.substring(0, 2);
				out:for(String key:xzqhAreaDepth2.keySet()) {
					if(xzqhPix.equals(key.substring(0,2))) {
						List<List<Point2D.Double>> _areaPoints = xzqhAreaDepth2.get(key);
						for(List<Point2D.Double> _a:_areaPoints) {
							if(IsPtInPoly(point, _a)) {
								result = scXzqh.get(key);
								break out;
							}
						}
					}
				}
				
				if(result.size()>0) {
					prevXzqh = result.getString("XZQH_ID");
					xzqhPix = prevXzqh.substring(0, 4);
					out:for(String key:xzqhAreaDepth3.keySet()) {
						if(xzqhPix.equals(key.substring(0,4))) {
							List<List<Point2D.Double>> _areaPoints = xzqhAreaDepth3.get(key);
							for(List<Point2D.Double> _a:_areaPoints) {
								if(IsPtInPoly(point, _a)) {
									result = scXzqh.get(key);
									break out;
								}
							}
						}
					}
				}
				
				if(result.size()==0) {
					return null;
				}else {
					result.remove("area_baidu");
					return result;
				}
			}else if(xzqh_depth==2) {
				String prevXzqh = result.getString("XZQH_ID");
				String xzqhPix = prevXzqh.substring(0, 4);
				out:for(String key:xzqhAreaDepth3.keySet()) {
					if(xzqhPix.equals(key.substring(0,4))) {
						List<List<Point2D.Double>> _areaPoints = xzqhAreaDepth3.get(key);
						for(List<Point2D.Double> _a:_areaPoints) {
							if(IsPtInPoly(point, _a)) {
								result = scXzqh.get(key);
								break out;
							}
						}
					}
				}
				if(result.size()==0) {
					return null;
				}else {
					result.remove("area_baidu");
					return result;
				}
			}else if(xzqh_depth==3) {
				result.remove("area_baidu");
				return result;
			}
		}
		
		int n = 0;
		out:for(String key:xzqhAreaDepth1.keySet()) {
			List<List<Point2D.Double>> _areaPoints = xzqhAreaDepth1.get(key);
			for(List<Point2D.Double> _a:_areaPoints) {
				if(IsPtInPoly(point, _a)) {
					result = scXzqh.get(key);
					break out;
				}
				n++;
			}
		}
		
		if(result.size()>0) {
			String prevXzqh = result.getString("XZQH_ID");
			String xzqhPix = prevXzqh.substring(0, 2);
			out:for(String key:xzqhAreaDepth2.keySet()) {
				if(xzqhPix.equals(key.substring(0,2))) {
					List<List<Point2D.Double>> _areaPoints = xzqhAreaDepth2.get(key);
					for(List<Point2D.Double> _a:_areaPoints) {
						if(IsPtInPoly(point, _a)) {
							result = scXzqh.get(key);
							break out;
						}
						n++;
					}
				}
			}
		}
		
		if(result.size()>0) {
			String prevXzqh = result.getString("XZQH_ID");
			String xzqhPix = prevXzqh.substring(0, 4);
			out:for(String key:xzqhAreaDepth3.keySet()) {
				if(xzqhPix.equals(key.substring(0,4))) {
					List<List<Point2D.Double>> _areaPoints = xzqhAreaDepth3.get(key);
					for(List<Point2D.Double> _a:_areaPoints) {
						if(IsPtInPoly(point, _a)) {
							result = scXzqh.get(key);
							break out;
						}
						n++;
					}
				}
			}
		}
		
		System.out.println("count: " + n);
		
		if(result.size()==0) {
			return null;
		}else {
			result.remove("area_baidu");
			return result;
		}
	}
	
	public static JSONObject getXzqhCodeFormName(String province, String city, String county) {
		JSONObject result = new JSONObject();
		
		if(!StringTool.isEmpty(county)) {
			if(scXzqhNickKey.containsKey(county)) {
				result = scXzqhNickKey.get(county);
			}
		}
		
		if(result.size()==0&&!StringTool.isEmpty(city)) {
			if(scXzqhNickKey.containsKey(city)) {
				result = scXzqhNickKey.get(city);
			}
		}
		
		if(result.size()==0&&!StringTool.isEmpty(province)) {
			if(scXzqhNickKey.containsKey(province)) {
				result = scXzqhNickKey.get(province);
			}
		}
		
		if(result.size()==0) {
			return null;
		}else {
			result.remove("area_baidu");
			return result;
		}
		
	}
	
	public static List<Point2D.Double> getListFromStr(String str){
		String strs [] = str.split("\\],");
		
		List<Point2D.Double> polygon = new ArrayList<Point2D.Double>();
		for(int i=0;i<strs.length;i++){
			 String _temp = strs[i].replaceAll("\\[", "").replaceAll("\\]", "");
			 
			 polygon.add(new Point2D.Double(Double.valueOf(_temp.split(",")[0]), Double.valueOf(_temp.split(",")[1])));
			
		}
		return polygon;
	}
	
	public static List<Point2D.Double> getListFromStrBaidu(String str){
		List<Point2D.Double> polygon = new ArrayList<Point2D.Double>();
		if(!StringTool.isEmpty(str)) {
			String[] _points = str.split(";");
			for(int i=0;i<_points.length;i++){
				 String[] _p = _points[i].split(",");
				 polygon.add(new Point2D.Double(Double.valueOf(_p[0].trim()), Double.valueOf(_p[1].trim())));
			}
		}
		return polygon;
	}
	
	
	/**
     * 判断点是否在多边形内
     *
     * @param point 检测点
     * @param pts   多边形的顶点
     * @return 点在多边形内返回true, 否则返回false
     */
    public static boolean IsPtInPoly(Point2D.Double point, List<Point2D.Double> pts) {

        int N = pts.size();
        boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;//cross points count of x
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        Point2D.Double p1, p2;//neighbour bound vertices
        Point2D.Double p = point; //当前点

        p1 = pts.get(0);//left vertex
        for (int i = 1; i <= N; ++i) {//check all rays
            if (p.equals(p1)) {
                return boundOrVertex;//p is an vertex
            }
            p2 = pts.get(i % N);//right vertex
            if (p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)) {//ray is outside of our interests
                p1 = p2;
                continue;//next ray left point
            }

            if (p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)) {//ray is crossing over by the algorithm (common part of)
                if (p.y <= Math.max(p1.y, p2.y)) {//x is before of ray
                    if (p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)) {//overlies on a horizontal ray
                        return boundOrVertex;
                    }

                    if (p1.y == p2.y) {//ray is vertical
                        if (p1.y == p.y) {//overlies on a vertical ray
                            return boundOrVertex;
                        } else {//before ray
                            ++intersectCount;
                        }
                    } else {//cross point on the left side
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y
                        if (Math.abs(p.y - xinters) < precision) {//overlies on a ray
                            return boundOrVertex;
                        }

                        if (p.y < xinters) {//before ray
                            ++intersectCount;
                        }
                    }
                }
            } else {//special case when ray is crossing through the vertex
                if (p.x == p2.x && p.y <= p2.y) {//p crossing over p2
                    Point2D.Double p3 = pts.get((i + 1) % N); //next vertex
                    if (p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)) {//p.x lies between p1.x & p3.x
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;//next ray left point
        }

        return intersectCount % 2 != 0;
    }
    
  //地球平均半径
    private static final double EARTH_RADIUS = 6378137;
    //把经纬度转为度（°）
    private static double rad(double d){
       return d * Math.PI / 180.0;
    }
    
    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2){
       double radLat1 = rad(lat1);
       double radLat2 = rad(lat2);
       double a = radLat1 - radLat2;
       double b = rad(lng1) - rad(lng2);
       double s = 2 * Math.asin(
			Math.sqrt(
			    Math.pow(Math.sin(a/2),2)
			    + Math.cos(radLat1)* Math.cos(radLat2)* Math.pow(Math.sin(b/2),2)
			)
		);
       s = s * EARTH_RADIUS;
       s = Math.round(s * 10000) / 10000;
       return s;
    }
    
 
}
