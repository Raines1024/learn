package com.raines.javaadvanced.notnull;

/**
 * 工厂
 */
public class ParserFactory {

    public static Parser getParser(){
        return new MyParser();
    }

}
