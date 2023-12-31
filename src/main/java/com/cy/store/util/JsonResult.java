package com.cy.store.util;

import java.io.Serializable;

/**
 * Json格式
 * */
public class JsonResult<E> implements Serializable {

    /** 狀態碼 */
    private Integer state;
    /** 描述訊息 */
    private String message;
    /** 任何數據類型 */
    private E date;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }
    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }
    //狀態碼加上異常
    public JsonResult(Integer state, E date) {
        this.state = state;
        this.date = date;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getDate() {
        return date;
    }

    public void setDate(E date) {
        this.date = date;
    }
}
