package com.example.testh2.domain;

import com.example.testh2.dto.MemberDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mobile;

    public void updateMember(MemberDto memberDto) {
        this.name = memberDto.getName();
        this.mobile = memberDto.getMobile();
    }

    @Builder
    public Member(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }
}
