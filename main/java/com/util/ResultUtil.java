package com.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;


public class ResultUtil {

	
	private ResponseObject rep;
	
	
	public void success(HttpServletRequest request,HttpServletResponse response,String msg) {
		rep=new ResponseObject(true, msg);
		out(request,response);
	}
	
	public ResultUtil push(String key,Object o){
		if(rep==null){
			rep=new ResponseObject(true, "ok");
		}
		rep.push(key, o);
		return this;
	}
	
	public void success(HttpServletRequest request,HttpServletResponse response,String msg,Map<String,Object> params) {
		rep=new ResponseObject(true, msg,params);
		out(request,response);
	}
	
	public void error(HttpServletRequest request,HttpServletResponse response,String msg) {
		rep=new ResponseObject(false, msg);
		out(request,response);
	}



	public void out(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
		response.setContentType("text/xml;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			String array = JSON.toJSONString(this.rep);
			//System.out.println(array);
			//String log="返回结果大小" + Util.df.format((array.getBytes().length) / 1024.00) + "KB";
			//System.out.println(log);
			out.print(array);
			out.flush();
			out.close();
		} catch (IOException e) {
		}
		
	}
}
