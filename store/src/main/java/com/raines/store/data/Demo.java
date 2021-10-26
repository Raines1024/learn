package com.raines.store.data;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 利用druid连接池进行数据库查询操作(配置文件方式)
 * 参数：https://blog.csdn.net/GouGe_CSDN/article/details/86599576
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        /*
        导包
        准备配置文件,只要是properties文件即可,需要自己加载该配置文件
        创建连接池对象
        获取连接
        获取语句执行者进行后续的一系列操作
         */
        //创建properties对象,将配置文件信息读取到该对象中
        Properties pro = new Properties();
        InputStream in = Demo.class.getClassLoader().getResourceAsStream("druid.properties");//获取输入流
        pro.load(in);//读取配置文件的信息到ps中,抛出异常
        DataSource dataSource = DruidDataSourceFactory.createDataSource(pro);//获取连接池
        Connection connection = dataSource.getConnection();//获取连接
        //查询出部门编号、部门名称、部门位置、每个部门的员工人数
        String sql = "select * from block_info;";
        //获取语句执行者等一系列操作
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
//            int id = resultSet.getInt("id");
//            String dname = resultSet.getString("dname");
//            String loc = resultSet.getString("loc");
//            int count = resultSet.getInt("count(*)");
            System.out.println(resultSet.getString("name"));

        }
        //释放资源
        closeResource(resultSet, ps, connection);

    }

    private static void closeResource(ResultSet resultSet, PreparedStatement ps, Connection connection) throws SQLException {
        resultSet.close();
        ps.close();
        if (connection != null) {
            connection.close();
        }
    }

}
