package com.cy.store.controller;

import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * Controller層的父層
 */
public class BaseController {
    /**
     * 操作成功的狀態碼
     */
    public static final int OK = 200;

    // 定義一個公開的靜態常數OK，值為200，表示操作成功的HTTP狀態碼
    // 以下是處理異常的方法，當ServiceException類型的異常發生時，此方法會被自動調用
    @ExceptionHandler(ServiceException.class) // 用於統一處理ServiceException類型的異常
    public JsonResult<Void> handleException(Throwable e) {
        // 定義一個方法handleException，用於處理異常，方法接收Throwable類型的參數e
        // 創建一個JsonResult實體，將異常e作為參數傳遞進去
        JsonResult<Void> result = new JsonResult<>(e);
        // 判斷異常e是否為UsernameDuplicatedException
        if (e instanceof UsernameDuplicatedException) {
            // 如果是，設置JsonResult的狀態碼為4000
            result.setState(4000);
            // 設置JsonResult的消息為"會員名稱被占用"
            result.setMessage("會員名稱已經被使用");

            // 判斷異常e是否為UserNotFoundException的實例
        } else if (e instanceof UserNotFoundException) {
            // 如果是，設置JsonResult的狀態碼為5001
            result.setState(5001);
            // 設置JsonResult的消息為"會員資料不存在"
            result.setMessage("會員資料不存在");

            // 判斷異常e是否為PasswordNotMatchException
        } else if (e instanceof PasswordNotMatchException) {
            // 如果是，設置JsonResult的狀態碼為5002
            result.setState(5002);
            // 設置JsonResult的消息為"密碼錯誤"
            result.setMessage("密碼錯誤");

            // 判斷異常e是否為InsertException
        } else if (e instanceof InsertException) {
            // 如果是，設置JsonResult的狀態碼為5000
            result.setState(5000);
            // 設置JsonResult的消息為"註冊時產生未知的異常"
            result.setMessage("註冊時產生未知的異常");
        }
        return result;
    }
    /**
     * 獲取Session中的uid
     * @param session session 物件
     * @return 當前登錄用戶的uid的值
     */
    protected final Integer getuidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid")
                .toString());
    }
    /**
     * 獲取當前用戶的username
     * @param session session 物件
     * @return 當前登錄用戶的名稱
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}

