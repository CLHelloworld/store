package com.cy.store.service;

import com.cy.store.entity.User;

/**
 * 用戶模組的service層介面
 */
public interface IUserService {

//-----------------------------用戶註冊----------------------------

    /**
     * 用戶註冊
     *
     * @param user 用戶資料物件
     */
    void reg(User user);

//-----------------------------用戶登錄功能----------------------------

    /**
     * 用戶登錄功能
     *
     * @param username 用戶名
     * @param password 用戶密碼
     * @return 當前的用戶數據, 若沒有則返回null
     */
    // 正常登錄成功只要比對完成不需要返回值
    // User返回值為,為了將數據保存在cookie或是session做一些其他的操作可以避免平凡操作資料庫獲取數據
    // 用戶名稱,id=>session,頭像=>cookie
    User login(String username, String password);

 //-----------------------------用戶修改密碼----------------------------
    void changePassword(Integer uid, String username,
                        String oldPassword,
                        String newPassword);

//-------------------------根據用戶的id查詢用戶的資訊-----------------------------
    /**
     * 根據用戶的id查詢用戶的資訊
     *
     * @param uid 用戶id
     * @return 用戶的資訊
     */
    User getByUid(Integer uid);

//------------------------------更新用戶的資訊-------------------------------
    /**
     * 更新用戶的資訊
     *
     * @param uid      用戶id
     * @param username 用戶名稱
     * @param user     用戶的資訊
     */
    void changeInfo(Integer uid, String username, User user);

}
