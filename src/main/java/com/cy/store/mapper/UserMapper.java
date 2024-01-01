package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Date;

/** 處理用戶數據操作區塊的mapper(Dao)介面 */
public interface UserMapper {

    /**
     * 插入用戶的資訊
     * @param user 的數據
     * @return 受影響的欄位(增刪改 都受影響的欄位放到返回值,可以根據返回值來判斷是否執行成功)
     * */
    Integer insert(User user);

    /**
     * 根據用戶名來查詢用戶的資料
     * @param username 的數據
     * @return 若找到對應的用戶則返回數據,若沒有則返回null
     */
    User findByUsername(String username);

    /**
     * 根據用戶的uid來修改用戶密碼
     * @param uid 用戶的id
     * @param password 用戶輸入的新密碼
     * @param modifiedUser 修改人的id
     * @param modifiedTime 修改的時間
     * @return 返回值為受影響的欄位
     */
    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                Date modifiedTime);

    /**
     * 根據用戶的id來查詢用戶數據
     * @param uid 用戶的id
     * @return 若找到則返回物件,反之則返回null
     */
    User findByUid(Integer uid);
}
