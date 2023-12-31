package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest : 表示當前的class為一個測試,不會一起打包
@SpringBootTest
//@RunWith : 表示啟動這個單元測試(沒有加上沒辦法運行),需要傳遞一個參數,必須是SpringRunner類型
@RunWith(SpringRunner.class)
public class UserServicerTests {
    // idea檢測到介面不能被創建實體(動態代理技術來解決)
    @Autowired
    private UserService userService;
    /**
     * 單元測試方法 : 可以單獨運行,不用啟動整個專案
     * 1.必須標註@Test註解
     * 2.返回值類型必須是void
     * 3.方法的參數列表不指定任何類型
     * 4.方法的訪問修飾字必須是public
     */
    @Test
// 標記這是一個測試方法
// 定義了一個名為reg的公開測試方法
    public void reg(){
        try {
            // 創建一個新的User對象
            User user = new User();
            // 為User對象設置用戶名為'yuanxin01'
            // 為User對象設置密碼為'123'
            user.setUsername("yuanxin02");
            user.setPassword("123");
            // 調用userService的reg方法，傳入user對象進行註冊
            userService.reg(user);
            // 如果註冊成功，打印"ok"
            System.out.println("ok");
            // 捕捉ServiceException異常
        } catch (ServiceException e) {
            // 印出異常類型的簡單名稱
            System.out.println(e.getClass().getSimpleName());
            // 印出異常的消息
            System.out.println(e.getMessage());

        }
    }

}
