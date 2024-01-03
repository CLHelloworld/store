package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.AddressCountLimitException;
import com.cy.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 收貨地址的service實作層
 */

//@Service註解 : 將當前class交給Spring管理
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;
    //在添加用戶的收貨地址的service依賴於DistrictService的介面
    @Autowired
    private IDistrictService districtService;
    //從設定檔給值
    @Value("${user.address.max-count}")
    private Integer maxCount;

    //-------------------------新增收貨地址-----------------------------
    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //調用收貨地址總和方法,判斷有沒有達到上限
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("用戶收貨地址數量超出上限");
        }
        //補全address中的數據:省市區
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        //uid,isDelete
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        // 補全數據:4個時間訊息
        Date now = new Date();
        address.setCreatedUser(username);
        address.setCreatedTime(now);
        address.setModifiedUser(username);
        address.setModifiedTime(now);
        //新增收貨地址的方法
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("新增用戶收貨地址發生異常");
        }

    }
}
