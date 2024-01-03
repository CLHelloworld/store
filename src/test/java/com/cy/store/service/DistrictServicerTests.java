package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
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
public class DistrictServicerTests {

    // idea檢測到介面不能被創建實體(動態代理技術來解決)
    @Autowired
    private DistrictMapper districtMapper;

    /**
     * 單元測試方法 : 可以單獨運行,不用啟動整個專案
     * 1.必須標註@Test註解
     * 2.返回值類型必須是void
     * 3.方法的參數列表不指定任何類型
     * 4.方法的訪問修飾字必須是public
     */

    @Test
    public void findByParent() {
        List<District> list = districtMapper.findByParent("210100");
        for (District d : list
        ) {
            System.out.println(d);
        }
    }


}