package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.mybatis.spring.annotation.MapperScan;

/** 處理用戶數據操作區塊的mapper(Dao)介面 */
public interface UserMapper {
    /**
     * 插入用戶的資訊
     * @param user 的數據
     * @return 受影響的行數(增刪改 都受影響的行數座回返回值,可以根據返回值來判斷是否執行成功)
     * */
    Integer insert(User user);
    /**
     * 根據用戶名來查詢用戶的資料
     * @param username 的數據
     * @return 若找到對應的用戶則返回數據,若沒有則返回null
     */

    User findByUsername(String username);
}
