package com.exception;


import com.util.LoggerUtil;
import com.util.ResponseObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

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
        stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        LoggerUtil.log(stringWriter.toString());
        if (ex.getMessage() == null || ex.getMessage().length() < 2) {
    		return new ResponseObject(false, "服务器被外星人攻击了！");
    	}
    	else{
    		return new ResponseObject(false, ex.getMessage());
    	}
    }
    
}
