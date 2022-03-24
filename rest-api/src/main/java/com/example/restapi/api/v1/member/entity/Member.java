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
    private Long id;
    private String name;
    private String birthDay;
    private String email;
    private String address;

    @Builder
    public Member(Long id, String name, String birthDay, String email, String address) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.email = email;
        this.address = address;
    }

    public void updateAll(MemberDto memberDto) {
        this.name = memberDto.getName();
        this.birthDay = memberDto.getBirthDay();
        this.email = memberDto.getEmail();
        this.address = memberDto.getAddress();
    }

    public MemberModel toModel() {
        return MemberModel.builder()
                .id(this.id)
                .name(this.name)
                .birthDay(this.birthDay)
                .email(this.email)
                .address(this.address)
                .build();
    }
}
