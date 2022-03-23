package com.example.restapi.api.v1.member.entity;

import com.example.restapi.api.v1.member.model.MemberDto;
import com.example.restapi.api.v1.member.model.MemberModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 회원 entity
 * @author kdh
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicUpdate
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String name;
    private String birthDay;
    private String email;
    private String address;

    public void updateAll(MemberDto memberDto) {
        this.name = memberDto.getName();
        this.birthDay = memberDto.getBirthDay();
        this.email = memberDto.getEmail();
        this.address = memberDto.getAddress();
    }

    @Builder
    public Member(Long seq, String name, String birthDay, String email, String address) {
        this.seq = seq;
        this.name = name;
        this.birthDay = birthDay;
        this.email = email;
        this.address = address;
    }

    public MemberModel toModel() {
        return MemberModel.builder()
                .seq(this.seq)
                .name(this.name)
                .birthDay(this.birthDay)
                .email(this.email)
                .address(this.address)
                .build();
    }
}
