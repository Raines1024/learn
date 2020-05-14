package com.raines.store.data;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        //查询经理的信息。显示员工姓名，员工工资，职务名称，职务描述，部门名称，部门位置，工资等级
        String sql1 = "select * from block_info;";

        //查询所有员工信息。显示员工姓名，员工工资，职务名称，职务描述，部门名称，部门位置，工资等级
        String sql2 = " select * from block_info;";
        //获取语句执行者等一系列操作
        PreparedStatement ps = connection.prepareStatement(sql);
//        PreparedStatement ps1 = connection.prepareStatement(sql1);
//        PreparedStatement ps2 = connection.prepareStatement(sql2);
        ResultSet resultSet = ps.executeQuery();
//        ResultSet resultSet1 = ps1.executeQuery();
//        ResultSet resultSet2 = ps2.executeQuery();
        while (resultSet.next()) {
//            int id = resultSet.getInt("id");
//            String dname = resultSet.getString("dname");
//            String loc = resultSet.getString("loc");
//            int count = resultSet.getInt("count(*)");
            System.out.println(resultSet.getString("name"));

        }
        System.out.println("===================================================================");
//        while (resultSet1.next()) {
//            String ename = resultSet1.getString("ename");
//            int salary = resultSet1.getInt("salary");
//            String jname = resultSet1.getString("jname");
//            String description = resultSet1.getString("description");
//            String dname = resultSet1.getString("dname");
//            String loc = resultSet1.getString("loc");
//            int grade = resultSet1.getInt("grade");
//            System.out.println("ename=" + ename + " salary=" + salary + " jname=" + jname + " description=" + description
//                    + " dname=" + dname + " loc=" + loc + " grade=" + grade);
//
//        }
//        System.out.println("===================================================================");
//        while (resultSet2.next()) {
//            String ename = resultSet2.getString("ename");
//            int salary = resultSet2.getInt("salary");
//            String jname = resultSet2.getString("jname");
//            String description = resultSet2.getString("description");
//            String dname = resultSet2.getString("dname");
//            String loc = resultSet2.getString("loc");
//            int grade = resultSet2.getInt("grade");
//            System.out.println("ename=" + ename + " salary=" + salary + " jname=" + jname + " description=" + description
//                    + " dname=" + dname + " loc=" + loc + " grade=" + grade);
//
//        }
        //释放资源
        closeResource(resultSet, ps, connection);
//        closeResource(resultSet1, ps1, null);
//        closeResource(resultSet2, ps2, null);

    }

    private static void closeResource(ResultSet resultSet, PreparedStatement ps, Connection connection) throws SQLException {
        resultSet.close();
        ps.close();
        if (connection != null) {
            connection.close();
        }
    }

}
