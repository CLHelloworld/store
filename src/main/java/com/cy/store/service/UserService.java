package com.cy.store.service;

import com.cy.store.entity.User;

/** 會員模組的service層介面 */
public interface UserService {
    /**
     * 會員註冊
     * @param user 會員資料對象
     */
    void reg(User user);
}
