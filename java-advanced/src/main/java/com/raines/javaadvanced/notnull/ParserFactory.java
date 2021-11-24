package com.raines.javaadvanced.notnull;

/**
 * 解析器工厂
 */
public class ParserFactory {
    public static Parser getParser(String parser){
        if (parser == null){
            return new NoThingParser();
        }
        return new MyParser();
    }
}
