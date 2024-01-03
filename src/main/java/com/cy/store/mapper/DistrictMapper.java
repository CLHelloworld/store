package com.cy.store.mapper;

import com.cy.store.entity.District;

import java.util.List;

public interface DistrictMapper {
    /**
     * 根據父代號查詢區域
     * @param parent 父代號
     * @return 某個父區域下的查詢列表
     * */
    List<District> findByParent(String parent);

    String findNameByCode(String code);
}
