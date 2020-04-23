package com.raines.interesting.renderActionDemo.render;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class Render {
    public static void rend(HttpServletResponse response, Result result){
        response.setHeader("Content-type", "application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter pw = response.getWriter();
            pw.write(JSON.toJSONString(result));
            pw.flush();
            pw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
