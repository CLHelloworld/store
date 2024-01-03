package com.cy.store.service;

import com.cy.store.entity.Address;

/**
 * 收貨地址的service層介面
 */
public interface IAddressService {

    /**
     * 新增新的收貨地址
     * @param uid 當前登錄的用戶id
     * @param username 當前登錄的用戶名稱
     * @param address 送出的數據
     */
    void addNewAddress(Integer uid, String username , Address address);


}
