package com.example.simpleproject.service;

import com.example.simpleproject.domain.Member;
import com.example.simpleproject.repository.MemberRepository;
import com.example.simpleproject.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//스프링 컨테이너에 멤버 서비스를 등록해줌 @Service
//JPA를 사용하기 위한 @Transactional 어노테이션 또는 메소드에만 넣어도됨
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
//        중복제거
//        cmd + art + v 자동변수완성
//        옵셔널을 반환하는 것은 좋지 않다.

//        AOP 관련 시간측정 로직을 작성하려하는데 핵심기능이 아닌 부분을 넣으려고한다.
//        공통의 부분인 시간측정을 섞으면 유지보수가 어렵다.
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 회원");
        });
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long id){
        return memberRepository.findById(id);
    }
}
