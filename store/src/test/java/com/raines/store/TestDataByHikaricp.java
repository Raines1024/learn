package com.raines.store;

import com.raines.store.hikaricpdemo.util.HikaricpUtils;
import com.raines.store.hikaricpdemo.util.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class TestDataByHikaricp {

    @Test
    public void  testHikariCp() throws SQLException, IOException {
        QueryRunner qr =  new QueryRunner(HikaricpUtils.getDataSource());
        User u = qr.query("select * from user where id=1", new BeanHandler<User>(User.class));
        System.out.println(u);
    }
}