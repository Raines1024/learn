package com.raines.comm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.*;
import feign.jackson.JacksonDecoder;
import kong.unirest.Unirest;
import lombok.SneakyThrows;

import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.*;

interface GitHub {

    @RequestLine("POST /app/doCard/{time}")
    Contributor contributors(@HeaderMap Map<String, Object> headerMap, @Param("time") String time);

    //可以通过对对象类型的参数加上QueryMap注解, 将对象的内容构造成查询参数
    @RequestLine("POST /iim-pocs/token")
    Issue createIssue(@HeaderMap Map<String, Object> headerMap, @QueryMap User user);

    @RequestLine("POST /getRealTimeInfo")
    @Headers("Content-Type: application/json")
    @Body("{body}")
    RealInfo realTime(@Param("body") RealParam body);

}

class Contributor {
    public String msg;
    public String timestamp;
    public int code;
    public Map<String, Object> data;
}

class Issue {
    public String errorMessage;
    public String result;
    public boolean success;
}

class User {
    public String userName;
    public String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}

class RealInfo {
    public Integer success;
    public Map<String, Object> data;
}

class RealParam {
    public String terminalId;
    public String params;
    public Integer flag;

    public RealParam(String terminalId, String params, Integer flag) {
        this.terminalId = terminalId;
        this.params = params;
        this.flag = flag;
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}

public class MyApp {
    public static void main(String... args) throws Exception {

//        demo($.formatDate(new Date(),"yyyy-MM-dd"));
//        Thread.sleep(1000L);
//        doCard();
//        System.out.println($.getDateForString("20211105120841", "yyyyMMddHHmmss").getTime());
        new Timer("timer - ").schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println($.getDateString(new Date().getTime(), "HHmmss") + " run ");
                doCard();
            }
        }, $.getDateForString("20211204120843", "yyyyMMddHHmmss"));
//        new Timer("timer - ").schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println($.getDateString(new Date().getTime(), "HHmmss") + " run ");
//                doCard();
//                System.exit(0);
//            }
//        }, $.getDateForString("20211102124910", "yyyyMMddHHmmss"));
    }

    public static void demo(String day) {
        String response = Unirest.post("https://sinolookmall.com.cn/EnjoyMobileWSCXCX/Enjoy/Service")
                .header("sign", "BE3B0142D30D5513F1F4953C123CC86E")
                .header("Content-Type", "application/json")
                .body("{\n\t\"UniqueKey\": \"移动会员签到记录\",\n\t\"MethodName\": \"Sign\",\n\t\"UserNo\": \"\",\n\t\"ClientTime\": \""+day+"T03:06:12.091Z\",\n\t\"ObjectData\": {\n\t\t\"c_string_parm1\": \"900030203\",\n\t\t\"c_string_parm2\": \"25ee91a8-d1ff-4c84-9723-77a525a8a3de\",\n\t\t\"c_string_parm3\": \"ouQTz5CvmAD1tpdP3UO0tZNfdfYQ\",\n\t\t\"c_string_parm4\": \"9999000127153\"\n\t},\n\t\"Tag\": null,\n\t\"Channel\": \"BBC商城(微信小程序)\",\n\t\"SessionId\": \"a1195ad3-e0e3-4c33-a6a7-a5132359e1d3\"\n}")
                .asString().getBody();
        System.out.println(response);
    }


    public static void doCard() {
        long time = new Date().getTime() / 1000;
        GitHub github = Feign.builder()
                .decoder(new JacksonDecoder())
                .target(GitHub.class, "http://kaoqin.lovol.com/");
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("token", "1");
        headerMap.put("bindCode", "80970FCB-41C6-4387-B3EF-7E679FD52347");
        Contributor contributor = github.contributors(headerMap, time + "");
        System.out.println(contributor.data + "===" + contributor.code + "===" + contributor.msg + " (" + contributor.timestamp + ")");
    }

    //json格式post
    private static void jsonPost() {
        GitHub github = Feign.builder()
                .decoder(new JacksonDecoder())
                .target(GitHub.class, "http://101.200.35.250:8090");
        RealInfo contributor = github.realTime(new RealParam("LOV1251712601170", "A3,A0,C1,C2", 1));
        System.out.println(contributor.data + "===" + contributor.success);
    }

    //表单格式post
    private static void formPost() {
        GitHub github = Feign.builder()
                .decoder(new JacksonDecoder())
                .target(GitHub.class, "http://139.196.190.10");

        // Fetch and print a list of the contributors to this library.
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("Authorization", "5f650c29-506e-43cd-b3c5-cb922260ef41");
        Issue contributor = github.createIssue(headerMap, new User("dyxdsfq", "123456"));
        System.out.println(contributor.errorMessage + "===" + contributor.result + "===" + " (" + contributor.success + ")");
    }
}
