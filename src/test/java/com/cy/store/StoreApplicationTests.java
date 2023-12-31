package com.cy.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() {
    }
    /**
     * 資料庫連接池
     * 1.DBCP
     * 2.C3P0
     * 3.Hikari(日語光)管理資料庫的連接對象
     * */

    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

}
