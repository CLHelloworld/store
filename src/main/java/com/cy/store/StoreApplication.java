package com.cy.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.MultipartConfig;

//@Configuration //表示配置Bean
@SpringBootApplication
//MapperScan註解指定當前項目中的Mapper接口路徑的位置,啟動時會自動載入所有接口
@MapperScan("com.cy.store.mapper")
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

            //SpringBoot 2.0以上版本可以使用設定檔進行配置
//    @Bean
//    public MultipartConfigElement getMultipartConfigElement() {
//
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//
//        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
//
//        factory.setMaxRequestSize(DataSize.of(15,DataUnit.MEGABYTES));
//
//        return factory.createMultipartConfig();
//    }
}
