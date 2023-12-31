package com.cy.store.mapper;

import com.cy.store.entity.User;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.service.ex.UserNotFoundException;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

//@SpringBootTest : 表示當前的class為一個測試,不會一起打包
@SpringBootTest
//@RunWith : 表示啟動這個單元測試(沒有加上沒辦法運行),需要傳遞一個參數,必須是SpringRunner類型
@RunWith(SpringRunner.class)
public class UserMapperTests {
    // idea檢測到介面不能被創建實體(動態代理技術來解決)
    @Autowired
    private UserMapper userMapper;

    /**
     * 單元測試方法 : 可以單獨運行,不用啟動整個專案
     * 1.必須標註@Test註解
     * 2.返回值類型必須是void
     * 3.方法的參數列表不指定任何類型
     * 4.方法的訪問修飾字必須是public
     */
    //--------------------------測試新增用戶---------------------
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);

    }

    //----------------------測試根據Username尋找用戶------------------------------
    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }

    //---------------------------測試更換密碼--------------------------
    @Test
    public void updatePasswordByUid() {

        userMapper.updatePasswordByUid(7, "321", "管理員", new Date());
    }

    //-----------------------測試根據Uid尋找用戶-----------------------------
    @Test
    public void findByUid() {
        System.out.println(userMapper.findByUid(7));
    }

    //----------------------測試更新用戶數據----------------------------------
    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(8);
        user.setPhone("878787878787");
        user.setEmail("test002@gmail.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    //----------------------測試更新用戶頭像----------------------------------
    @Test
    public void updateAvatarByUid() {
        userMapper.updateAvatarByUid(
                8,
                "/upload/avatar.png",
                "管理員",
                new Date());

    }
}