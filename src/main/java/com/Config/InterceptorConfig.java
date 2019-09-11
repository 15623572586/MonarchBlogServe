package com.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * 注册拦截器
 * 未使用
 */
@Configuration
public class InterceptorConfig implements HandlerInterceptor {
    //在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("拦截1");
        HttpSession session = httpServletRequest.getSession();
        String user = (String) session.getAttribute("user"); //获取登录的session信息
        if(user!=null){
            return true;
        }else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");  //未登录自动跳转界面
//            httpServletResponse.sendRedirect("http://localhost:8080/login");  //未登录自动跳转界面
            String roat = httpServletRequest.getServerName();
            System.out.println(roat);
            return false;
        }
    }

////    请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//        System.out.println("postHandle被调用\n");
//    }
//
//    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println("afterCompletion被调用\n");
//    }
}
