package com.raines.javaadvanced.notnull;

/**
 * 默认解析器（寻找不到的默认处理）
 */
public class NoThingParser implements Parser{

    private static Action DO_NOTHING = () -> {
        //do nothing
        System.out.println("do nothing");
    };

    @Override
    public Action findAction(String userInput) {
        return DO_NOTHING;
    }
}
