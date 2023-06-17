package com.example.simpleproject.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//AOP로 사용할 수 있는 어노테이션
@Aspect
@Component
//넣는것 보다 Config에 Bean으로 등록하는게 좋다. 쓰이는지 가시로 체크가능
public class TimeTraceAop {

    //타겟팅을 해줄 수 있음
    @Around("execution(* com.example.simpleproject.service..*(..))")
    public Object excute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());
        try{
            return joinPoint.proceed();
            //다음메소드 실행
        }finally {
            long end = System.currentTimeMillis();
            long timeMs = end - start;
            System.out.println("END : " +joinPoint.toString() + " " + timeMs + "ms");
        }
    }

}
