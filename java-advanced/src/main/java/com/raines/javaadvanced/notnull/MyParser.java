package com.raines.javaadvanced.notnull;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的自定义解析器
 */
public class MyParser implements Parser{

    static Map<String,Action> map = new HashMap<>();

    static {
        map.put("A",()->{System.out.println("A plan start.");});
    }

    private static Action DO_NOTHING = () -> {
        //do nothing
        System.out.println("do nothing");
    };

    @Override
    public Action findAction(String userInput) {
        Action res = map.get(userInput);
        if (res == null) {
            return DO_NOTHING;
        }
        return res;
    }
}
