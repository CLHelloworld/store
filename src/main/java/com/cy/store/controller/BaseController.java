package com.cy.store.controller;

import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
        JsonResult<Void> result = new JsonResult<>(e);
        // 創建一個JsonResult對象，將異常e作為參數傳遞進去
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("會員名稱被占用");
        } else if (e instanceof InsertException) {
            // 判斷異常e是否為UsernameDuplicatedException的實例
            result.setState(5000);
            // 如果是，設置JsonResult的狀態碼為5000
            result.setMessage("註冊時產生未知的異常");
            // 設置JsonResult的消息為"註冊時產生未知的異常"
        }
        return result;
    }
}

