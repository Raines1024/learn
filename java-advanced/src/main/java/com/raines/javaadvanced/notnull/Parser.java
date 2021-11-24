package com.raines.javaadvanced.notnull;

/**
 * 解析器接口
 */
@FunctionalInterface
public interface Parser {
    Action findAction(String userInput);
}
