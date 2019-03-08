package com.exception;


import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.LoggerUtil;
import com.util.ResponseObject;

@ControllerAdvice
public class ExceptionHandler {

	private StringWriter stringWriter;
	  /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseObject errorHandler(Exception ex) {
    	if(ex.getMessage()==null||ex.getMessage().length()<2){
    		stringWriter = new StringWriter();
    		ex.printStackTrace(new PrintWriter(stringWriter));
    		LoggerUtil.log(stringWriter.toString());
    		return new ResponseObject(false, "服务器被外星人攻击了！");
    	}
    	else{
    		return new ResponseObject(false, ex.getMessage());
    	}
    }
    
}
