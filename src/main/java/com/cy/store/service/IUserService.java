package com.cy.store.service;

import com.cy.store.entity.User;

/** 會員模組的service層介面 */
public interface IUserService {
    /**
     * 會員註冊
     * @param user 會員資料物件
     *
     */
    void reg(User user);

    /**
     * 用戶登錄功能
     * @param username 用戶名
     * @param password 用戶密碼
     * @return 當前的用戶數據,若沒有則返回null
     * */

    // 正常登錄成功只要比對完成不需要返回值
    // User返回值為,為了將數據保存在cookie或是session做一些其他的操作可以避免平凡操作資料庫獲取數據
    // 用戶名稱,id=>session,頭像=>cookie
    User login(String username,String password);
}
