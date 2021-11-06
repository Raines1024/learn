package com.raines.javaadvanced.notnull;

@FunctionalInterface
public interface Parser {
    Action findAction(String userInput);
}
