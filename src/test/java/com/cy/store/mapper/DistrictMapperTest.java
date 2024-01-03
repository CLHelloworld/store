package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import com.cy.store.service.IDistrictService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@SpringBootTest : 表示當前的class為一個測試,不會一起打包
@SpringBootTest
//@RunWith : 表示啟動這個單元測試(沒有加上沒辦法運行),需要傳遞一個參數,必須是SpringRunner類型
@RunWith(SpringRunner.class)
public class DistrictMapperTest {

    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void getByParent() {
        // 86代表中國,所有省的父代號都是86
        List<District> list = districtMapper.findByParent("86");
        for (District d : list
        ) {
            System.out.println(d);
        }
    }

    @Test
    public void fineNameByCode() {
       String name = districtMapper.findNameByCode("610000");
        System.out.println(name);
    }
}
