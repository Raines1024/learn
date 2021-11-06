package com.raines.javaadvanced.notnull;

/**
 *
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
