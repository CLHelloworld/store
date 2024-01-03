package com.cy.store.mapper;

import com.cy.store.entity.Address;

/** 收貨地址Entity的介面 */
public interface AddressMapper {
    /**
     * 新增用戶收貨地址
     * @param address 收貨地址
     * @return 受影響的行數
     * */
    Integer insert(Address address);

    /**
     * 根據用戶的id統計收貨地址的數量
     * @param uid 用戶的id
     * @return 當前用戶收貨地址總數
     * */
    Integer countByUid(Integer uid);



}
