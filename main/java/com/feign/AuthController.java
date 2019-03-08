package com.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="auth",fallback=AuthController.FaildBack.class)
@Component
public interface AuthController {

	@PostMapping("auth/verify")
	public String verify(@RequestParam("token") String token);
	
	@PostMapping("auth/getToken")
	public String getToken(@RequestParam("userId") String userId,@RequestParam("loginName") String loginName,@RequestParam("role") String role);
	
	@Component
	class FaildBack implements AuthController{

		@Override
		public String verify(String token) {
			throw new RuntimeException("服务故障");
		}

		@Override
		public String getToken(String userId, String loginName, String role) {
			throw new RuntimeException("服务故障");
		}
		
	}
}
