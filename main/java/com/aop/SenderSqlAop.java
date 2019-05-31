package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.entity.Sender;

@Aspect
@Component
public class SenderSqlAop {

	@Autowired
	private StringRedisTemplate cache;
	
	 //定义一个公用方法
    @Pointcut("execution(public * com.dao.SenderMapper.selectByPrimaryKey(..))")
    public void function(){
    }

    
    @Around("function()")
    public Object twiceAsOld(ProceedingJoinPoint thisJoinPoint) throws Throwable{
        Object[] params=thisJoinPoint.getArgs();
        if(params==null || params[0]==null){
        	return null;
        }
        int id=(int) params[0];
        Object rs=null;
        if(Boolean.parseBoolean(cache.opsForValue().get("cache"))){
        	if((rs=cache.boundHashOps("SENDER").get(id+""))==null){
        		Sender msg=(Sender) thisJoinPoint.proceed();
        		cache.boundHashOps("SENDER").put(""+id, JSON.toJSONString(msg));
        		return msg;
        	}else{
        		return JSON.parseObject(rs.toString(), Sender.class);
        	}
        }else{
        	return thisJoinPoint.proceed();
        }
    }
}
