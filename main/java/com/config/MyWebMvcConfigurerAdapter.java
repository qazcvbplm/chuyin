package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter{

	
	@Bean
	public MyInterceptor get(){
		return new MyInterceptor();
	}
	
	 /**
     * 配置静态资源
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       // registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
       // registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        super.addResourceHandlers(registry);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则
        //excludePathPatterns 用于排除拦截
        registry.addInterceptor(get()).addPathPatterns("/ops/**")
            .excludePathPatterns("/ops/user/wx/login") //登录页
            .excludePathPatterns("/ops/school/find")
            .excludePathPatterns("/v2/api-docs")
            .excludePathPatterns("/ops/school/login")
            .excludePathPatterns("/webjars/**")
            .excludePathPatterns("/ops/notify/**") 
            .excludePathPatterns("/ops/shop/android/**") 
            .excludePathPatterns("/ops/orders/android/**") 
            .excludePathPatterns("/ops/shop/nocheck/shopstatistics")
            .excludePathPatterns("/ops/application/check")
            .excludePathPatterns("/ops/user/wx/get/bell")
            //.excludePathPatterns("/ops/sender/nocheck/**") 
           .excludePathPatterns("/doc.html"); //登录页
            //.excludePathPatterns("/hlladmin/user/sendEmail") //发送邮箱
            //.excludePathPatterns("/hlladmin/user/register") //用户注册
            //.excludePathPatterns("/hlladmin/user/login"); //用户登录
        super.addInterceptors(registry);
    }
}
