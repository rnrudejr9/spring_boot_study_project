package com.example.simpleproject;

import com.example.simpleproject.repository.JdbcMemberRepository;
import com.example.simpleproject.repository.JdbcTemplateMemberRepository;
import com.example.simpleproject.repository.MemberRepository;
import com.example.simpleproject.repository.MemoryMemberRepository;
import com.example.simpleproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//자바 코드로 직접 스프링 빈에 등록하는 방법

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//        return  new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }

    // 상황에 따라 구현 클래스를 변경해야할때 쉽게 변경가능
    // 어떠한 코드를 손대지않고 JdbcMemberRepository 클래스를 두고 확장한것임

}
