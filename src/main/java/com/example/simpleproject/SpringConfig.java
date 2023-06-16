package com.example.simpleproject;

import com.example.simpleproject.repository.*;
import com.example.simpleproject.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//자바 코드로 직접 스프링 빈에 등록하는 방법

@Configuration
public class SpringConfig {

//    jdbc 사용
//    private DataSource dataSource;
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//    jpa 사용
//    private EntityManager em;
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    private final MemberRepository memberRepository;

    //spring data jpa 에서 만든 구현체가 자동으로 들어와진다.
    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository(){
////        return new MemoryMemberRepository();
////        return new JdbcMemberRepository(dataSource);
////        return new JdbcTemplateMemberRepository(dataSource);
////        return new JpaMemberRepository(em);
//    }

    // 상황에 따라 구현 클래스를 변경해야할때 쉽게 변경가능
    // 어떠한 코드를 손대지않고 JdbcMemberRepository 클래스를 두고 확장한것임

}
