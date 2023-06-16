package com.example.simpleproject.repository;

import com.example.simpleproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//springdatajpa 가 이걸보고 본인이 직접 구현체를 만들어서 등록함
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>,MemberRepository {

    //select m from Member m where m.name =?
    //인터페이스안 메소드 이름만으로도 개발 가능하다.
    @Override
    Optional<Member> findByName(String name);

//    기본적인 CRUD 기능
//    이름만으로도 조회 기능
//    페이징 기능 자동제공
}
