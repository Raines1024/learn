package com.raines.javaadvanced.notnull;



public class Main {

    public static void main(String[] args) {
        ParserFactory.getParser("").findAction("A").doSomething();
//        demo("A");
    }

    private static void demo(String someInput){
        //获取一个解析器对象
        Parser parser = ParserFactory.getParser("");
        if (parser == null) {
            // now what?
            // this would be an example of where null isn't (or shouldn't be) a valid response
        }
        //寻找解析器实现
        Action action = parser.findAction(someInput);
        if (action == null) {
            // do nothing
        } else {
            //调用解析方法
            action.doSomething();
        }
    }

}
