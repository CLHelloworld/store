package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //@Controller + @ResponseBody
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    /*   //使用統一處理取代
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        //創建響應結果對象
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("會員註冊成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("會員名稱被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("註冊時產生未知的異常");
        }
        return result;
    }
    */
}

