package com.raines.store.data;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.raines.store.util.MysqlConnectManager;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 使用MysqlConnectManager访问mysql
 */
public class GetData {

    private static ResourceBundle rb = ResourceBundle.getBundle("systemParamters");

    public static List<Map<String,Object>> getBlocks() {
        return MysqlConnectManager.getInstance().queryMysql("id,name,boundary", "block_info", null, false);
    }

    public static void main(String[] args) {
        for (Map map : getBlocks()){
            System.out.println(map);
        }
    }

}
