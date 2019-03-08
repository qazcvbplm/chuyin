package com.config;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feign.AuthController;
import com.util.ResultUtil;

@Component
public class MyInterceptor implements HandlerInterceptor {

	@Autowired
	private AuthController auth;
	
	
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	            throws Exception {
		 String token =request.getHeader("token");
		 String msg=null;
		    if((msg=auth.verify(token))!=null){
		    	JSONObject json=JSON.parseObject(msg);
		    	request.setAttribute("Id", json.getString("userId"));
		    	request.setAttribute("role", json.getString("role"));
		    	return true;
		    }
		    new ResultUtil().error(request, response, "1100");;
	        return false;
	    }

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}
}
