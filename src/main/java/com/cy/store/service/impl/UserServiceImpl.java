package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * 會員模組的service實作層
 */

@Service //@Service註解 : 將當前class交給Spring管理
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    //-------------------------用戶註冊-----------------------------
    @Override
    public void reg(User user) {
        // 通過user參數來獲取傳遞過來的username
        String username = user.getUsername();
        // 調用findByUsername(username)方法,判斷會員是否被註冊過
        User result = userMapper.findByUsername(username);
        // 判斷結果是否不為null則拋出會員名稱被使用的異常
        if (result != null) {
            //拋出異常
            throw new UsernameDuplicatedException("會員名稱已使用");
        }

        // 密碼加密處理 : md5算法的形式
        //字串 + 密碼 + 字串 =>交給 md5 加密
        String oldPassword = user.getPassword();
        //獲取隨機生成的字串(鹽值)
        String salt = UUID.randomUUID().toString().toUpperCase();
        //紀錄隨機生成的字串(鹽值)
        user.setSalt(salt);
        //將密碼和字串作為整體進行加密處理,忽略原有密碼的強度提升數據安全性
        String md5Password = getMD5Password(oldPassword, salt);
        //將加密過後的密碼放進去
        user.setPassword(md5Password);

        // 補全數據:is_delete設置為0
        user.setIsDelete(0);

        // 補全數據:4個時間訊息
        user.setCreatedUser(user.getCreatedUser());
        user.setModifiedUser(user.getModifiedUser());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        // 執行註冊功能的實現(row == 1)
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("在會員註冊過程中出現未知的異常");
        }

    }

    //-------------------------用戶登錄功能--------------------------
    @Override
    public User login(String username, String password) {
        // 根據用戶名稱來查詢用戶的數據是否存在,若不存在則拋出異常
        User result = userMapper.findByUsername(username);

        if (result == null) {
            throw new UserNotFoundException("用戶資料不存在");
        }
        // 檢查用戶的密碼是否正確
        // 1.先得到資料庫中加密之後的密碼
        String oldPassword = result.getPassword();
        // 2.和用戶傳過來的密碼進行比較
        // 2-1. 先獲取加密字串(鹽值):上一次註冊時自動生成的加密字串(鹽值)
        String salt = result.getSalt();
        // 2-2. 將用戶傳過來的密碼按照相同的md5算法規則進行加密
        String newMD5Password = getMD5Password(password, salt);
        // 3. 將密碼進行比較
        if (!newMD5Password.equals(oldPassword)) {
            throw new PasswordNotMatchException("用戶密碼不正確");
        }
        // 判斷id_delete(狀態欄位)的值是否為1表示狀態為刪除
        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("用戶資料不存在");
        }
        //將部分資料傳給前端不需要傳整包,提升效能也能提升安全性
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        //返回有用戶的頭像
        user.setAvatar(result.getAvatar());

        //將當前用戶的資料返回,為了輔助其他頁面做展示使用(uid,username,avater(大頭貼))
        return user;
    }

    //-----------------------修改用戶密碼----------------------------
    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        //確認用戶是否存在
        // 調用userMapper的findByUid()方法，根據用戶的uid查詢用戶數據
        User result = userMapper.findByUid(uid);
        // 如果查詢結果為null或者用戶標記為已刪除，則拋出UserNotFoundException異常
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("會員資料不存在");
        }
        // 原始密碼和資料庫中密碼進行比對，先將原始密碼進行MD5加密處理
        String oldMD5Password = getMD5Password(oldPassword, result.getSalt());
        // 如果加密後的原始密碼與資料庫中的密碼不一致，則拋出PasswordNotMatchException異常
        if (!result.getPassword().equals(oldMD5Password)) {
            throw new PasswordNotMatchException("密碼錯誤");
        }
        // 將新密碼進行MD5加密
        String newMD5Password = getMD5Password(newPassword, result.getSalt());
        // 調用userMapper的updatePasswordByUid()方法，更新用戶密碼，並返回更新的行數
        Integer rows = userMapper.updatePasswordByUid(uid, newMD5Password, username, new Date());

        // 如果更新的行數不為1，則拋出UpdateException異常
        if (rows != 1) {
            throw new UpdateException("更新資料時產生未知的異常");
        }
    }

    //---------------------根據用戶的id查詢用戶的資訊------------------------------
    @Override
    public User getByUid(Integer uid) {
        //確認用戶是否存在
        // 調用 userMapper 的 findByUid 方法根據 uid 獲取用戶信息
        User result = userMapper.findByUid(uid);
        // 檢查獲取的用戶信息是否為 null 或者用戶標記為已刪除（isDelete 等於 1）
        if (result == null || result.getIsDelete() == 1) {
            // 如果用戶不存在或已被標記為刪除，則拋出 UserNotFoundException 異常
            throw new UserNotFoundException("用戶數據不存在");
        }
        // 創建一個新的 User 對象
        User user = new User();
        // 將查詢到的用戶信息複製到新的 User 對象中
        user.setUsername(result.getUsername()); // 設置用戶名
        user.setPhone(result.getPhone());       // 設置電話號碼
        user.setEmail(result.getEmail());       // 設置電子郵箱
        user.setGender(result.getGender());     // 設置性別
        // 返回新創建的 User 物件
        return user;
    }

    //-----------------------更新用戶的資訊--------------------------

    /**
     * user物件中的數據只有phone/email/gender,
     * 手動將uid/username封裝到user物件中
     */
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        //確認用戶是否存在
        // 使用 userMapper 的 findByUid 方法根據 uid 查找用戶
        User result = userMapper.findByUid(uid);
        // 檢查獲取的用戶是否為 null 或者用戶是否被標記為已刪除（isDelete 等於 1）
        if (result == null || result.getIsDelete() == 1) {
            // 如果用戶不存在或已被標記為刪除，則拋出 UserNotFoundException 異常
            throw new UserNotFoundException("用戶數據不存在");
        }
        // 設置 user 對象的 uid
        user.setUid(uid);
        // user.setUsername(username);
        // 設置修改用戶的用戶名
        user.setModifiedUser(username);
        // 設置修改時間為當前時間
        user.setModifiedTime(new Date());
        // 調用 userMapper 的 updateInfoByUid 方法更新用戶信息
        Integer rows = userMapper.updateInfoByUid(user);
        // 檢查是否正確更新了一條數據
        if (rows != 1) {
            // 如果不是一條，則拋出 UpdateException 異常
            throw new UpdateException("更新數據時發生未知的異常");
        }
    }

    //-----------------------更新用戶的頭像----------------------------
    @Override
    public void changeAvatat(Integer uid, String avatar, String username) {
        //確認用戶是否存在
        // 使用 userMapper 的 findByUid 方法根據 uid 查找用戶
        User result = userMapper.findByUid(uid);
        // 檢查獲取的用戶是否為 null 或者用戶是否被標記為已刪除（isDelete 等於 1）
        if (result == null || result.getIsDelete() == 1) {
            // 如果用戶不存在或已被標記為刪除，則拋出 UserNotFoundException 異常
            throw new UserNotFoundException("用戶數據不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新用戶頭像時發生異常");
        }
    }

    //--------------------------------------------------------------------

    /**
     * 定義一個mp5的加密處理
     */
    private String getMD5Password(String password, String salt) {
        for (int i = 0; i < 3; i++) {
            // MD5加密算法方法的使用(進行三次
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        //返回加密之後的密碼
        return password;
    }
}
