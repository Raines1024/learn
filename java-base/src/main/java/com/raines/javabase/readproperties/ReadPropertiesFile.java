package com.raines.javabase.readproperties;

import ice.tool.PropertiesProvider;
import ice.tool.exception.CommonException;

public class ReadPropertiesFile {

    private static PropertiesProvider pp;

    static {
        initPropertiesProvider();
    }

    //初始化系统文件
    private static void initPropertiesProvider() {
        pp = PropertiesProvider.getInstance("/sys_config.properties");
        if(pp == null){
            throw new CommonException("找不到系统文件：/sys_config.properties");
        }
    }

    public static void main(String[] args) {
        System.out.println(pp.getValue("cron.days"));
    }

}
