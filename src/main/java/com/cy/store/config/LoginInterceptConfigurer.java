package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller攔截器的註冊
 */
@Configuration //加載當前的攔截器進行註冊
public class LoginInterceptConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 創建自定義攔截器物件
        HandlerInterceptor interceptor = new LoginInterceptor();
        //配置白名單 : 存放在List集合 所有靜態資源和不想被攔截的頁面(可讓非會員看到的頁面)
        List<String> patterns = new ArrayList<String>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/district/**");
        /** 用於配置攔截器 */

        // 完成攔截器的註冊
        registry.addInterceptor(interceptor)
                //表示要攔截的url /**代表所有
                .addPathPatterns("/**")
                //表示不需要攔截的url,只接受集合
                .excludePathPatterns(patterns);

    }
}
