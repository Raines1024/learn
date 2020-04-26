package com.raines.store.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class StringTool {

	/**
	 * 数组转为字符串
	 * 
	 * @param s
	 *            数组
	 * @param compart
	 *            分隔符
	 **/
	public static String ArrayToString(String[] s, String compart) {

		int fieldsNumber = s.length;
		String field = "";

		for (int i = 0; i < fieldsNumber; i++) {
			if ((i + 1) != fieldsNumber) {
				field += " " + compart + s[i] + compart + ",";
			} else {
				field += " " + compart + s[i] + compart;
			}
		}
		return field;

	}

	// ISO编码转换成GBK编码
	public static String convertGBK(String str) {
		try {
			if (isEmpty(str))
				return "";
			byte[] bytesStr = str.getBytes("ISO-8859-1");
			return new String(bytesStr, "gb2312");
		} catch (Exception ex) {
			return str;
		}
	}

	// GBK编码转换成ISO编码
	public static String convertISO(String str) {
		try {
			if (isEmpty(str))
				return "";
			byte[] bytesStr = str.getBytes("gb2312");
			return new String(bytesStr, "ISO-8859-1");
		} catch (Exception ex) {
			return str;
		}
	}

	/**
	 * 判断字符串是否为空
	 */
	public static boolean isEmpty(String str) {

		boolean flag = false;
		if (str == null || "".equals(str) || "null".equals(str)) {

			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @return New String 空值而不是null
	 */
	public static String isNewStr(String str) {

		String flag = str;
		if (str == null || str.equals("") || str.equals("null"))
			flag = "";
		return flag;
	}

	/**
	 * YYYY-MM-DD
	 **/
	public static String getNowDate() {

		Calendar cal = Calendar.getInstance();
		int intMonth = cal.get(Calendar.MONTH) + 1;
		int intDate = cal.get(Calendar.DATE);
		String sMonth = String.valueOf(intMonth);
		String sDate = String.valueOf(intDate);
		if (intMonth < 10) {
			sMonth = "0" + sMonth;
		}
		if (intDate < 10) {
			sDate = "0" + sDate;
		}
		return cal.get(Calendar.YEAR) + "-" + sMonth + "-" + sDate;
	}

	/**
	 * 按照字节截取字符串长度
	 * 
	 * @param s
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String bSubstring(String s, int length) throws Exception {

		byte[] bytes = s.getBytes("Unicode");
		int n = 0; // 表示当前的字节数
		int i = 2; // 要截取的字节数，从第3个字节开始
		for (; i < bytes.length && n < length; i++) {
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (i % 2 == 1) {
				n++; // 在UCS2第二个字节时n加1
			} else {
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0) {
					n++;
				}
			}
		}
		// 如果i为奇数时，处理成偶数
		if (i % 2 == 1)

		{
			// 该UCS2字符是汉字时，去掉这个截一半的汉字
			if (bytes[i - 1] != 0)
				i = i - 1;
			// 该UCS2字符是字母或数字，则保留该字符
			else
				i = i + 1;
		}

		return new String(bytes, 0, i, "Unicode");
	}

	public static String clearHtml(String str) {
		int flag1 = str.indexOf("<");
		int flag2 = str.indexOf(">");
		while (flag1 != -1 || flag2 != -1) {
			str = clearHtmlPrivate(str);
			flag1 = str.indexOf("<");
			flag2 = str.indexOf(">");
		}
		return str;
	}

	private static String clearHtmlPrivate(String str) {
		int start = str.indexOf("<");
		int end = str.indexOf(">");
		if (start != -1 || end != -1) {
			str = str.substring(0, start) + str.substring(end + 1);
		}
		return str;
	}

	public static String superClearHtml(String str) {
		str = str.replaceAll("<head>.*</head>|<script>.*?</script>", "").// 头部与脚本去掉
				replaceAll("<.*?>|&.{2,5};", "");// 标签与转义去掉
		return str;
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		if (src == null)
			return null;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else if (src.charAt(pos + 1) == ' ' || src.charAt(pos + 1) == ';') {
					tmp.append(src.substring(pos, pos + 1));
					lastPos = pos + 1;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * YYYYMMDDHHNNSSMI
	 **/
	public static String getRandomNumber() {

		Calendar cal = Calendar.getInstance();
		int intMonth = cal.get(Calendar.MONTH) + 1;
		int intDate = cal.get(Calendar.DATE);
		int HH = cal.get(Calendar.HOUR);
		int NN = cal.get(Calendar.MINUTE);
		int SS = cal.get(Calendar.SECOND);
		int MI = cal.get(Calendar.MILLISECOND);
		String sMonth = String.valueOf(intMonth);
		String sDate = String.valueOf(intDate);
		if (intMonth < 10) {
			sMonth = "0" + sMonth;
		}
		if (intDate < 10) {
			sDate = "0" + sDate;
		}
		return cal.get(Calendar.YEAR) + sMonth + sDate + HH + NN + SS + MI;
	}

	public static String convertMarks(String args) {
		return args.replaceAll("'", "''");
	}

	public static String convertNull(String str) {
		// boolean flag = false;
		if (str == null || "null".equals(str)) {
			return "";
		} else {
			return str.trim();
		}
	}

	public static String convertNullNone(String str) {
		if (str == null || "null".equals(str) || "".equals(str)) {
			return "暂无数据";
		} else {
			return str.trim();
		}
	}

	/**
	 * 根据scXzqh生成出前台city空间可用的数据格式字符串，当provinceId为空的时候，则取全部数据
	 * 
	 * @param scXzqh
	 * @param provinceId
	 * @return
	 */
	public static String[] getXzqhString(Map<String, JSONObject> scXzqh, String provinceId) {
		JSONArray province = new JSONArray();
		JSONArray area = new JSONArray();

		if (StringTool.isEmpty(provinceId)) {
			for (String key : scXzqh.keySet()) {
				if (key.endsWith("0000")) { // 省
					// { "id": "820000", "name": "澳门", "city":
					JSONObject _province = new JSONObject();
					_province.put("id", key);
					_province.put("name", scXzqh.get(key).getString("XZQH_NICK"));

					String str = key.substring(0, 2);
					JSONArray city = new JSONArray();
					if ("11".equals(str) || "12".equals(str) || "31".equals(str) || "50".equals(str)) {
						JSONObject _city = new JSONObject();
						_city.put("id", str + "0000");
						_city.put("name", scXzqh.get(key).getString("XZQH_NICK"));
						city.add(_city);
					} else {
						for (String key2 : scXzqh.keySet()) {
							if (key2.startsWith(str) && key2.endsWith("00") && !key.equals(key2)) {
								JSONObject _city = new JSONObject();
								_city.put("id", key2);
								_city.put("name", scXzqh.get(key2).getString("XZQH_NICK"));
								city.add(_city);
							}
						}
					}
					_province.put("city", city);

					province.add(_province);
				}
			}

			for (String key : scXzqh.keySet()) {
				if (!key.endsWith("00")) {
					JSONObject _area = new JSONObject();
					// { "id": "320104", "name": "秦淮区", "pid": "320100" }
					_area.put("id", key);
					_area.put("name", scXzqh.get(key).getString("XZQH_NICK"));

					String str = key.substring(0, 2);
					if ("11".equals(str) || "12".equals(str) || "31".equals(str) || "50".equals(str)) {
						_area.put("pid", str + "0000");
					} else {
						_area.put("pid", key.substring(0, 4) + "00");
					}
					area.add(_area);
				}
			}
		} else {
			for (String key : scXzqh.keySet()) {
				if (key.endsWith("0000") && key.startsWith(provinceId.substring(0, 2))) { // 省
					// { "id": "820000", "name": "澳门", "city":
					JSONObject _province = new JSONObject();
					_province.put("id", key);
					_province.put("name", scXzqh.get(key).getString("XZQH_NICK"));

					String str = key.substring(0, 2);
					JSONArray city = new JSONArray();
					for (String key2 : scXzqh.keySet()) {
						if (key2.startsWith(str) && key2.endsWith("00") && !key.equals(key2)) {
							JSONObject _city = new JSONObject();
							_city.put("id", key2);
							_city.put("name", scXzqh.get(key2).getString("XZQH_NICK"));
							city.add(_city);
						}
					}
					_province.put("city", city);

					province.add(_province);
				}
			}

			for (String key : scXzqh.keySet()) {
				if (!key.endsWith("00") && key.startsWith(provinceId.substring(0, 2))) {
					JSONObject _area = new JSONObject();
					// { "id": "320104", "name": "秦淮区", "pid": "320100" }
					_area.put("id", key);
					_area.put("name", scXzqh.get(key).getString("XZQH_NICK"));
					_area.put("pid", key.substring(0, 4) + "00");
					area.add(_area);
				}
			}
		}

		return new String[] { province.toString(), area.toString() };
	}

	/**
	 * 网格区域切分
	 * 
	 * @param line
	 * @param tableW
	 * @param tableH
	 * @return
	 */
	public static JSONArray getGridding(JSONArray line, Integer tableW, Integer tableH) {
		double minLng = 500;
		double maxLng = -500;
		double minLat = 500;
		double maxLat = -500;
		for (int i = 0; i < line.size(); i++) {
			String rect = line.getString(i);
			for (String point : rect.split(",")) {
				double lng = Double.parseDouble(point.split(" ")[0]);
				double lat = Double.parseDouble(point.split(" ")[1]);

				if (lng < minLng)
					minLng = lng;
				if (lng > maxLng)
					maxLng = lng;
				if (lat < minLat)
					minLat = lat;
				if (lat > maxLat)
					maxLat = lat;
			}
		}

		System.out.println(minLng + "," + maxLng + "," + minLat + "," + maxLat);

		int h_count = tableW; // 横向个数
		int z_count = tableH; // 纵向个数

		// 左上：小_大，右下：大_小
		double lngAvg = (maxLng - minLng) / h_count;
		double latAvg = (maxLat - minLat) / z_count;

		JSONArray areas = new JSONArray();
		DecimalFormat df = new DecimalFormat("#.000000");
		for (int n1 = 0; n1 < h_count; n1++) {
			for (int n2 = 0; n2 < z_count; n2++) {
				double leftTopLng = minLng + (n1 * lngAvg);
				double leftTopLat = maxLat - (n2 * latAvg);
				String _rect = df.format(leftTopLng) + " " + df.format(leftTopLat) + ","
						+ df.format((leftTopLng + lngAvg)) + " " + df.format(leftTopLat) + ","
						+ df.format((leftTopLng + lngAvg)) + " " + df.format((leftTopLat - latAvg)) + ","
						+ df.format(leftTopLng) + " " + df.format((leftTopLat - latAvg)) + "," + df.format(leftTopLng)
						+ " " + df.format(leftTopLat);
				areas.add(_rect);
			}
		}
		System.out.println(areas.toString());
		return areas;
	}

	/**
	 * 检查是否为数据
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 检查是否为数据
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
