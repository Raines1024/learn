package com.example.english;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Practice {


    static Scanner myScanner = new Scanner(System.in);
    static int start = 0;
    static boolean flag = false;
    static long time = 0;
    static void  init (){


        System.out.println("是否换行显示中文注释，默认否");
        try {
            String input = myScanner.next();
            if ("是".equals(input.trim())){
                flag = true;
            }else{
                flag = false;
            }
        }catch (Exception e){
        }
        System.out.println("注释需要延迟多少秒？默认0");
        try {
            String input = myScanner.next();
            time = Long.parseLong(input)*1000;
        }catch (Exception e){
            System.err.println("非数字，无延迟");
        }

    }

    public static List<Map<String,Object>> getWords() {
        return MysqlConnectManager.getInstance().queryMysql("id,word,chinese", "english", null, false);
    }

    static List<String> w = new ArrayList<>();
    static List<String> c = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {
        List<Map<String,Object>> list = getWords();
        w = list.stream().map(x -> x.get("word").toString()).collect(Collectors.toList());
        c = list.stream().map(x -> x.get("chinese").toString()).collect(Collectors.toList());
        System.out.println("开始单词数，默认从头开始");
        try {
            String input = myScanner.next();
            start = Integer.parseInt(input)-1;
        }catch (Exception e){
            System.err.println("非数字，从头开始");
        }
        init();
        //3. 接收用户输入了， 使用 相关的方法
        loop(start);
        System.out.println("单词背完啦，晓龙真棒！");
    }

    //学过单词的集合
//    static List list = new ArrayList();

    static void loop(int start) throws InterruptedException {

        for (int i = start; i < w.size(); i++) {
            reward(i);
            String singleWord = w.get(i);
//            //去除重复单词
//            if (list.contains(singleWord)){
//                continue;
//            }
//            list.add(singleWord);

            System.out.println(singleWord+"    "+c.get(i));
            Speak.openExe(singleWord);

            if (flag){
                System.out.println("\n");
            }

            if (time>0){
                Thread.sleep(time);
            }
//            System.out.println(c[i]);

            String name = myScanner.next(); //接收用户输入字符串


            writeFile(formatDate(new Date(),null)+"nums"+i+"单词--->"+singleWord+"\n");
            judge(singleWord,name,i);
        }
    }


    public static String formatDate(Date argDate, String argFormat) {
        if (argDate == null) {
            throw new RuntimeException("参数[日期]不能为空!");
        }
        if (argFormat == null || argFormat.length() == 0) {
            argFormat = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdfFrom = new SimpleDateFormat(argFormat);
        return sdfFrom.format(argDate).toString();
    }

    static void reward(int i) throws InterruptedException {
        String num = i+1+"";
        if (num.length()>2){
            num = num.substring(num.length()-2);
            if ("00".equals(num)){
                System.out.println(i+1+"又学会了100个单词，晓龙加油鸭！是否重新学习刚才的100个单词呢？");
                if ("是".equals(myScanner.next())){
//                    list = new ArrayList();
                    loop(i+1-100);
                }
            }
        }
    }

    static boolean judge(String old,String newest,int i){
        if (newest.equals("修改配置")){
            init();
            return judge(old,myScanner.next(),i);
        }
        if (old.equals(newest)){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            return true;
        }else {
            System.err.println("输入错误--->"+old);
            writeFile("输入错误--->"+old+"\n");
            Speak.openExe(old);
            return judge(old,myScanner.next(),i);
        }
    }

    static void writeFile(String data){
        try{

            File file =new File("word.txt");

            //if file doesnt exists, then create it
            if(!file.exists()){
                file.createNewFile();
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(),true);
            fileWritter.write(data);
            fileWritter.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
