package com.raines.comm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.*;
import feign.jackson.JacksonDecoder;
import lombok.SneakyThrows;

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
    public static void main(String... args) throws JsonProcessingException, ParseException {
//        doCard();
        new Timer("timer - ").schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println($.getDateString(new Date().getTime(), "HHmmss") + " run ");
                doCard();
                System.exit(0);
            }
        }, $.getDateForString("20210830120712", "yyyyMMddHHmmss"));
//        new Timer("timer - ").schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println($.getDateString(new Date().getTime(), "HHmmss") + " run ");
//                doCard();
//                System.exit(0);
//            }
//        }, $.getDateForString("20210818133208", "yyyyMMddHHmmss"));
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
