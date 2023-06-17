package com.example.simpleproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {
    //db에서 알아서 생성해주는 전략 identity
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //db에서의 table 컬럼 네임과 매핑가능
    @Column(name = "name")
    private String name;
}
