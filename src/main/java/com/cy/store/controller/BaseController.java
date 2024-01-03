package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * Controller層的父層為了統一處理例外
 */
public class BaseController {
    /**
     * 操作成功的狀態碼
     */
    public static final int OK = 200;

    // 定義一個公開的靜態常數OK，值為200，表示操作成功的HTTP狀態碼
    // 以下是處理異常的方法，當ServiceException類型的異常發生時，此方法會被自動調用
    // 用於統一處理ServiceException類型和FileUploadException類型的異常
    @ExceptionHandler({ServiceException.class,FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        // 定義一個方法handleException，用於處理異常，方法接收Throwable類型的參數e
        // 創建一個JsonResult實體，將異常e作為參數傳遞進去
        JsonResult<Void> result = new JsonResult<>(e);
        // 判斷異常e是否為UsernameDuplicatedException
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("會員名稱已經被使用");
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMessage("會員資料不存在");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("密碼錯誤");
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("收貨地址數量超出上限");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("註冊時產生未知的異常");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新資料時產生未知的異常");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage("上傳文件不得為空");
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage("上傳檔案的大小超過限制");
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
            result.setMessage("上傳的檔案類型不符合規定");
        } else if (e instanceof FileStateException) {
            result.setState(6003);
            result.setMessage("上傳檔案的狀態異常(例如使用中)");
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
            result.setMessage("上傳檔案過程中發生異常");
        }
        return result;
    }
    /**
     * 獲取Session中的uid
     * @param session session 物件
     * @return 當前登錄用戶的uid的值
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid")
                .toString());
    }
    //--------------------------------------------------------------------
    /**
     * 獲取當前用戶的username
     * @param session session 物件
     * @return 當前登錄用戶的名稱
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}

