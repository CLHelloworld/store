package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**  定義一個攔截器HandlerInterceptor  **/
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 檢查全局session是否有uid數據,有則放行如果沒有則重導到登錄頁面
     * @param request HTTP請求對象，包含了請求的所有信息。
     * @param response HTTP響應對象，可以通過它對響應進行設置。
     * @param handler 處理器，通常是一個url+Controller映射。
     * @return 返回一個boolean值。如果返回true，則繼續執行後續的攔截器和控制器方法；如果返回false，則中斷後續所有操作。
     * @throws Exception 如果方法執行過程中發生錯誤，會拋出異常。
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //HttpServletRequest物件來得到當前session
        Object obj = request.getSession().getAttribute("uid");
        // 檢查session中是否存在"uid"屬性，該屬性用於識別用戶是否已經登錄
        if (obj == null){
            //說明用戶沒有登錄過,重導到login.html頁面
            response.sendRedirect("/web/login.html");
            //結束後續的調用
            return false;
        }
        //請求放行
        return true;
    }

}
