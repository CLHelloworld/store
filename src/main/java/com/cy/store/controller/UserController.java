package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;

@RestController //@Controller + @ResponseBody
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;


    // ===約定大於配置 : 開發思想來完成,省略大量的配置甚至註解的編寫===

    //-----------------------------用戶註冊----------------------------

    /**
     * 1.接收數據的方式:請求處理方法的參數列表設置為pojo類型(物件)來接收前端的數據
     * SpringBoot會將前端的url地址中的參數和pojo類型(物件)的屬性(變數)名進行比較,如果
     * 這兩個名稱相同,則將值注入到pojo類中對應的屬性(變數)
     */

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    //-----------------------------用戶登錄功能----------------------------

    /**
     * 2.接收數據的方式:請求處理方法的參數列表設置為非pojo(物件)類型
     * SpringBoot會直接將請求的參數名和方法的參數名直接進行比較,若名稱相同
     * 則自動完成值得依賴注入
     */
    @RequestMapping("login")
    //前端發送請求過來,進入login方法做了一個查詢的動作,把結果放到data返回給前端
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session) {
        User data = userService.login(username, password);
        //session物件中數據的綁定(session為全局的)
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        //獲取session中榜定的數據
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(OK, data);
    }

    //-----------------------------用戶修改密碼----------------------------
    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }

    //----------------------根據用戶的id查詢用戶的資訊-------------------------
    @GetMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    //----------------------------更新用戶的資訊-------------------------------
    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,
                                       HttpSession session) {
        //user物件中有四種數據:username,phone,email,gender
        //uid數據需要再次封裝到user物件中
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }

    //---------------------------用戶頭像更新-------------------------------

    /**
     * MultipartFile介面是SpringMVC提供的一個介面,這個介面包裝了
     * 獲取文件類型的數據(任何類型的file都可),SpringBoot整合了
     * SpringMVC,只需要在處理請求的方法參數列表上聲明一個參數類型為MultipartFile
     * SpringBoot自動將傳遞進來的文件賦值給這個參數
     *
     * 方法參數file對應到前端input標籤name屬性為file的按鈕
     * 若前後端不同時可以用@RequestParam("表單內的name值")放在參數前指定對應
     *
     * @param session
     * @param file
     * @return
     * */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile file){

        return null;
    }
}

