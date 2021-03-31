package com.raines.comm;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 常用工具类
 */
public class $ {

    /**
     * 返回数据指定格式
     * @param code
     * @param msg
     * @return
     */
    public static Map<String, Object> reason(Integer code, String msg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        resultMap.put("data", null);
        resultMap.put("timestamp", new Date().getTime());
        return resultMap;
    }

    public static Map<String, Object> reason(Integer code, String msg,Object data,String id) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        resultMap.put("data", data);
        resultMap.put("BUSINESS_IDS", id);
        resultMap.put("timestamp", new Date().getTime());
        return resultMap;
    }

    public static Map<String, Object> reason(Integer code, String msg,Object data) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        resultMap.put("data", data);
        resultMap.put("timestamp", new Date().getTime());
        return resultMap;
    }

    public static void main(String[] args) {
//        System.out.println(Long.valueOf("ffff",16).toString());
//        System.out.println(Long.toHexString(11));
//        System.out.println(String.format("%06d", Integer.parseInt("123")));
    }

    public static String thirdFillZero(String code){
        return autoGenericCode(code,3);
    }

    public static String fourthFillZero(String code){
        return autoGenericCode(code,4);
    }

    /**
     * 不够位数的在前面补0，保留num的长度位数字
     */
    private static String autoGenericCode(String str, int strLength) {
        int strLen =str.length();
        if (strLen <strLength) {
            while (strLen< strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);//左补0
//    sb.append(str).append("0");//右补0
                str= sb.toString();
                strLen= str.length();
            }
        }

        return str;
    }

    /**
     * 16进制求和
     */
    private static String hexSum(String a,String b){
        if (a == null || b == null || a.length()<1 ||b.length()<1){
            throw new RuntimeException("num is null");
        }
        if (a.length() > 18 || b.length() > 18){
            throw new RuntimeException("num large");
        }
        long sum = Long.valueOf(a,16)+Long.valueOf(b,16);
        return Long.toHexString(sum);
    }

    /**
     * 16进制自增
     */
    public static String hexInc(String a){
        return hexSum(a,"1");
    }

    public static Date getDateForString(String time,String format) throws ParseException {
        SimpleDateFormat sdf =   new SimpleDateFormat(format);
        Date date = sdf.parse(time);
        return date;
    }

    /**
     * 根据毫秒转换成指定日期字符串
     *
     * @return
     */
    public static String getDateString(Long millisSecond,String format) {
        return formatDate(new Date(millisSecond),format);
    }

    /**
     * 得到日期字符串
     */
    public static String formatDate(Date date, String format) {
        String formatDate = null;
        if (format != null && format.length() > 0) {
            formatDate = new SimpleDateFormat(format).format(date);
        } else {
            formatDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
        return formatDate;
    }

}
