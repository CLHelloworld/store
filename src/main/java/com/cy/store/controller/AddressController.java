package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

// 設定這個控制器處理的基本URL路徑為 "/addresses"
@RequestMapping("addresses")
// 表明這是一個REST控制器，返回的數據會自動轉換為JSON格式
@RestController
public class AddressController extends BaseController {
    // 讓Spring自動注入IAddressService的實現
    @Autowired
    // 定義一個私有成員變數，用於存取地址相關業務邏輯
    private IAddressService addressService;

    // 將HTTP請求映射到這個方法，當請求路徑為"/addresses/add_new_address"時，調用此方法
    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address,
                                          HttpSession session) {
        // 從session中獲取用戶ID
        Integer uid = getUidFromSession(session);
        // 從session中獲取用戶名
        String username = getUsernameFromSession(session);
        // 調用新增地址方法來添加新地址
        addressService.addNewAddress(uid, username, address);
        // 返回操作結果，通常是一個表示成功的狀態碼
        return new JsonResult<>(OK);
    }
}
