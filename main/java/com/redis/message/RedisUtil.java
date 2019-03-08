package com.redis.message;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.dto.SchoolIndexDto;

@Component
public class RedisUtil {

	@Autowired
	private RedisTemplate<String, Object> schoolCache;
	

    public SchoolIndexDto init(int schoolId){
    	if(schoolCache.opsForValue().get("school"+schoolId)==null){
    		return new SchoolIndexDto();
    	}else{
    		return (SchoolIndexDto) schoolCache.opsForValue().get("school"+schoolId);
    	}
    }
    
    private void set(int schoolId, SchoolIndexDto temp) {
    	schoolCache.opsForValue().set("school"+schoolId, temp);
	}
    
    
    public void userCountadd(int schoolId){
    	SchoolIndexDto temp=init(schoolId);
    	temp.userCountadd();
    	set(schoolId,temp);
    }
    
   


	public void takeoutCountadd(int schoolId){
    	SchoolIndexDto temp=init(schoolId);
    	temp.takeoutCountadd();
    	set(schoolId,temp);
    }
    
    public void runCountadd(int schoolId){
    	SchoolIndexDto temp=init(schoolId);
    	temp.runCountadd();
    	set(schoolId,temp);
    }
    
    public void takeoutCountSuccessadd(int schoolId){
    	SchoolIndexDto temp=init(schoolId);
    	temp.takeoutCountaddSuccess();
    	set(schoolId,temp);
    }
    
    public void runCountSuccessadd(int schoolId){
    	SchoolIndexDto temp=init(schoolId);
    	temp.runCountSuccessadd();
    	set(schoolId,temp);
    }
    public void amountadd(int schoolId,BigDecimal amount){
    	SchoolIndexDto temp=init(schoolId);
    	temp.amountadd(amount);
    	set(schoolId,temp);
    }


	public SchoolIndexDto get(int schoolId) {
		SchoolIndexDto temp=init(schoolId);
		return temp;
	}


	public void clear() {
        Set<String> keys=schoolCache.keys("school*");	
        schoolCache.delete(keys);
	}
    
	
}
