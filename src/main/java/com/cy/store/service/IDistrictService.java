package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

public interface IDistrictService {
    /**
     * 根據父區域的代號來查詢區域
     *
     * @param parent 父代號
     * @return 多個子區域
     */
    List<District> getByParent(String parent);

    String getNameByCode(String code);

}
