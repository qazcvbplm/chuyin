package com.util;

import java.util.HashMap;
import java.util.Map;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应数据包
 * @author ops
 */
@ApiModel(description="统一返回数据个格式")
public class ResponseObject{
	
	
	@ApiModelProperty(notes="业务是否成功标志")
	private boolean code;
	@ApiModelProperty(notes="业务具体结果")
	private String msg;
	@ApiModelProperty(notes="数据包")
	private Map<String, Object> params;
	
   
	

	public ResponseObject() {
		super();
	}



	public ResponseObject(boolean code, String msg, Map<String, Object> params) {
		super();
		this.code = code;
		this.msg = msg;
		this.params = params;
	}
	
	

	public ResponseObject(boolean code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}



	public boolean isCode() {
		return code;
	}

	public void setCode(boolean code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	public ResponseObject push(String key, Object o) {
		if (params == null)
			params = new HashMap<>();
		params.put(key, o);
		return this;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	

	    
}
