package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController //@Controller + @ResponseBody
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    //自定義檔案上傳路徑跟application配置檔有關係
    @Value("${file.upload-dir}")
    private String uploadDir;

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
     * <p>
     * 方法參數file對應到前端input標籤name屬性為file的按鈕
     * 若前後端不同時可以用@RequestParam("表單內的name值")放在參數前指定對應
     *
     * @param session
     * @param file
     * @return
     */

    /**
     * 使用 static 的理由
     * <p>
     * 1.共用資源:
     * static 變數是屬於類別的，不是屬於某個特定物件的。
     * 這表示所有這個類別的物件都共用同一個 AVATAR_TYPE 變數。
     * 這在所有物件都需要用到同一份資料（這裡就是允許的圖片格式清單）時特別方便。
     * <p>
     * 2.記憶體效率:
     * 用 static 變數可以節省記憶體，因為不管創建了多少個這個類別的物件，
     * 在記憶體裡只會有一份拷貝。
     * <p>
     * 3.靜態初始化區塊:
     * 靜態初始化區塊（static { ... }）是用來初始化靜態變數的。
     * 這個區塊只會在類別第一次載入到 JVM（Java 虛擬機）時執行一次。
     * <p>
     * 4.不需要物件也能存取:
     * 因為 AVATAR_TYPE 是靜態的，所以它可以在不用創建類別物件的情況下被存取。
     * 這對於需要用到共用常數，但又不需要類別其他功能的場合很有幫助。
     * <p>
     * 總結 : 用 static 來定義 AVATAR_TYPE 及其初始化區塊，
     * 是為了確保這個圖片格式清單在整個應用程式中都是一個共用、一致的資源，
     * 同時也有效率地使用記憶體。
     */
    //設置上傳文件的最大值(10MB)
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    //限制上傳文件的類型（JPEG, PNG, BMP, GIF）
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(
            HttpSession session,
            @RequestParam("file") MultipartFile file) {
        //判斷文件是否為空
        if (file.isEmpty()) {
            throw new FileEmptyException("文件為空");
        }
        //判斷文件大小是否超過上限
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件大小超出限制");
        }
        //取得上傳文件的 MIME 型態
        String contentType = file.getContentType();
        //判斷文件類型是否是規定中的副檔名
        //如果集合中包含某個元素則返回true
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件類型錯誤");
        }
        //上傳的文件.../upload/文件.png
//        String parent =
//                session.getServletContext().
//                        getRealPath("upload");
        //自定義的上傳路徑
        String parent = uploadDir;
        //File對象指向這個路徑,File是否存在
        File dir = new File(parent);
        if (!dir.exists()) {//檢查目錄是否存在
            dir.mkdirs();//創建當前的目錄
        }

        System.out.println("文件將被儲存到: " + parent);


        // 獲取上傳文件的原始文件名,前綴和後綴要分開處理
        String originalFilename = file.getOriginalFilename();
        // 在控制台輸出原始文件名，用於調試或記錄
        System.out.println("OriginalFilename " + originalFilename);
        // 尋找原始文件名中最後一個點（.）的位置，這通常是檔案副檔名的開始
        int index = originalFilename.lastIndexOf(".");
        // 從副檔名開始的位置截取字串，獲取檔案的副檔名
        String suffix = originalFilename.substring(index);
        // 生成一個隨機的UUID，並轉換為大寫，然後與檔案副檔名結合，形成新的文件名
        // 這樣做可以確保文件名的唯一性，避免上傳的文件因同名而被覆蓋
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        //創建一個新的 File 物件，代表文件要被存儲的目標位置
        File dest = new File(dir, filename);
        // 將上傳的文件儲存到伺服器的指定位置
        try {
            file.transferTo(dest);
        } catch (FileStateException e) {
            throw new FileStateException("文件狀態異常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件讀寫異常");
        }
        // 從 session 中獲取用戶的唯一識別ID
        Integer uid = getUidFromSession(session);
        // 從 session 中獲取用戶名
        String username = getUsernameFromSession(session);
        // 構造頭像的儲存路徑，這裡假設頭像被儲存在 /upload 目錄下
        // filename 是之前創建的新文件名
        String avatar = "/upload/" + filename;
        // 調用 userService 的 changeAvatar 方法來更新用戶的頭像信息
        // 這個方法會接受用戶ID、新頭像的路徑和用戶名作為參數
        userService.changeAvatat(uid, avatar, username);
        //返回用戶頭像的路徑給前端,用於頭像展示使用
        return new JsonResult<>(OK, avatar);
    }
}

