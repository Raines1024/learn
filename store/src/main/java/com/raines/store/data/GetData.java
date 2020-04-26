package com.raines.store.data;

import com.raines.store.util.MysqlConnectManager;

import java.util.List;
import java.util.Map;

/**
 * 使用MysqlConnectManager访问mysql
 */
public class GetData {

    public static List<Map<String,Object>> getBlocks() {
        return MysqlConnectManager.getInstance().queryMysql("id,name,boundary", "block_info", null, false);
    }

    public static void main(String[] args) {
        for (Map map : getBlocks()){
            System.out.println(map);
        }
    }

}
