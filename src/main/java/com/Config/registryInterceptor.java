package com.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class registryInterceptor implements WebMvcConfigurer {
    /**
     * 注册拦截器
     *     未使用
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPattern后跟拦截地址，excludePathPatterns后跟排除拦截地址
//        registry.addInterceptor(new InterceptorConfig()).addPathPatterns("/**").excludePathPatterns("/login");
    }
}
