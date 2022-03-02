package com.raines.javaadvanced.filterDemo;

public class Demo {

    public static void main(String[] args) {
        int month = 2;
        int l = 202101;
        String s = "nohup yarn jar hreader-1.0-SNAPSHOT.jar cn.com.tiza.tstar.hfile.textkafka.TextkafkaDaohangMetaJob4Carno /tstar/lovol_parserdata/202101* /tmp/raines/daohang/202101 >butie_daohang.log &";
        while (true){
            l=l+1;
            System.out.println(month+++"月");
            System.out.println(s.replaceAll("202101",l+""));
            if (l>202111){
                break;
            }
        }
    }
}
