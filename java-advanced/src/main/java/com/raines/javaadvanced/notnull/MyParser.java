package com.raines.javaadvanced.notnull;

import java.util.HashMap;
import java.util.Map;

public class MyParser implements Parser{

    Map<String,Action> map = new HashMap<>();

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
