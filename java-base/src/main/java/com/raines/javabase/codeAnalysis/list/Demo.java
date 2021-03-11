package com.raines.javabase.codeAnalysis.list;

import java.util.*;

public class Demo {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        for (int i = 0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("1","aasdf");
        map.put("2","q2erw");
        map.get()
        map.entrySet().forEach(k -> {
            k.getKey()
            System.out.println(k);
        });
    }
}
