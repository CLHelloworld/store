package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest : 表示當前的class為一個測試,不會一起打包
@SpringBootTest
//@RunWith : 表示啟動這個單元測試(沒有加上沒辦法運行),需要傳遞一個參數,必須是SpringRunner類型
@RunWith(SpringRunner.class)
public class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert(){

         Address address = new Address();
         address.setUid(23);
         address.setPhone("77888877");
         address.setName("女朋友");
         addressMapper.insert(address);

    }

    @Test
    public void countByUid(){
        Integer count = addressMapper.countByUid(23);
        System.out.println(count);
    }

}
