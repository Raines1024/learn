package com.raines.javaadvanced.expectionprocess;

public class LogicException extends RuntimeException{

    protected Integer code;

    public LogicException() {
        super();
    }

    public LogicException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public LogicException(String message) {
        super(message);
        this.code = 500;
    }

}
