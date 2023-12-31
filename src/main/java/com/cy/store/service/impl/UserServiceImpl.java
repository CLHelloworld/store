package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/** 會員模組的service實作層 */
@Service //@Service註解 : 將當前class交給Spring管理
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        // 通過user參數來獲取傳遞過來的username
        String username = user.getUsername();
        // 調用findByUsername(username)方法,判斷會員是否被註冊過
        User result = userMapper.findByUsername(username);
        // 判斷結果是否不為null則拋出會員名稱被使用的異常
        if(result != null){
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
        if(rows != 1){
            throw new InsertException("在會員註冊過程中出現未知的異常");
        }

    }

    /** 定義一個mp5的加密處理 */
    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            // MD5加密算法方法的使用(進行三次
           password =  DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        //返回加密之後的密碼
        return password;
    }
}
