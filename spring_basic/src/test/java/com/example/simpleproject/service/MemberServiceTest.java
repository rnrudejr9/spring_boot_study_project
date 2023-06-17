package com.example.simpleproject.service;

import com.example.simpleproject.domain.Member;
import com.example.simpleproject.repository.MemoryMemberRepository;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository repository;

//    각각 사용하기 위한 별도로 생성
    @BeforeEach
    public void beforeEach(){
        repository = new MemoryMemberRepository();
        memberService = new MemberService(repository);
        //외부에서 데이터를 넣어줌, DI (의존성 주입)
    }


    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");
        //when
        Long saveId = memberService.join(member);
        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }


    @Test
    void 중복회원에러() {
        //given
        Member member = new Member();
        member.setName("hello");
        Member member1 = new Member();
        member1.setName("hello");
        //when
        Long saveId = memberService.join(member);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member1));
//        트라이캐치로 잡을 수 있음
//        애로우함수를 실행했을때, 위 에러가 발생할것이다.

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");
    }

    @Test
    void 하나찾기() {
    }

    @Test
    void findOne() {
    }
}