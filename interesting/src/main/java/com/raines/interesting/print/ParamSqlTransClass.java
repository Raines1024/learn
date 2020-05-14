package com.raines.interesting.print;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 驼峰转下划线
 */
public class ParamSqlTransClass {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    static String sql = "carNo\n" +
            "orderNo\n" +
            "sellArea\n" +
            "sellTime\n" +
            "repairTime\n" +
            "fault\n" +
            "faultAppear\n" +
            "faultReason\n" +
            "faultMessage\n" +
            "faultNum";

    static String mes = "出厂编号\n" +
            "索赔单号\n" +
            "销售区域\n" +
            "销售时间\n" +
            "报修时间\n" +
            "故障\n" +
            "故障现象\n" +
            "故障原因\n" +
            "故障描述\n" +
            "整机故障频次";

    public static void main(String[] args) {
        String[] mess = mes.split("\\n");
        String[] sqls = sql.split("\\n");
        for (int i = 0; i < sqls.length; i++) {
            System.out.println(sqls[i] + "           "+humpToLine(sqls[i]) + "           "+mess[i]);
        }
    }

    /**
     * 驼峰转下划线
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
