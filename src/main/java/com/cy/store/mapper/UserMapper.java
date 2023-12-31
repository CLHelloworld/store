package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 使用者Entity的介面
 */
public interface UserMapper {

    //-------------------------用戶註冊-----------------------------

    /**
     * 插入用戶的資訊
     *
     * @param user 的數據
     * @return 受影響的欄位(增刪改 都受影響的欄位放到返回值, 可以根據返回值來判斷是否執行成功)
     */
    Integer insert(User user);

    //-------------------------用戶登錄功能--------------------------

    /**
     * 根據用戶名來查詢用戶的資料
     *
     * @param username 的數據
     * @return 若找到對應的用戶則返回數據, 若沒有則返回null
     */
    User findByUsername(String username);

    //-----------------------修改用戶密碼----------------------------

    /**
     * 根據用戶的uid來修改用戶密碼
     *
     * @param uid          用戶的id
     * @param password     用戶輸入的新密碼
     * @param modifiedUser 修改人的id
     * @param modifiedTime 修改的時間
     * @return 返回值為受影響的欄位
     */
    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                Date modifiedTime);

    //---------------------根據用戶的id查詢用戶的資訊--------------------

    /**
     * 根據用戶的id來查詢用戶數據
     *
     * @param uid 用戶的id
     * @return 若找到則返回物件, 反之則返回null
     */
    User findByUid(Integer uid);

    //-----------------------更新用戶的資訊---------------------------

    /**
     * 更新用戶的數據
     *
     * @param user 用戶的數據
     * @return 返回值為受影響的欄位
     */
    Integer updateInfoByUid(User user);

    //-----------------------修改用戶的頭像-----------------------------

    /**
     * 根據用戶的uid來修改用戶的頭像
     * *@Param("SQL映射文件(XML)中 #{} 的變數名")
     * 當SQL裡的#{}映射的介面方法參數名不同時,需要將某個參數指定到#{}時可使用
     * 可以使用@param來標註映射的關係
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime
                              );



}
