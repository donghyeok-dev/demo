package com.example.restapi.api.entity;

import com.example.restapi.api.dto.MemberDto;
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
    private Integer age;
    private String address;

    @Builder
    public Member(String name, Integer age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public MemberDto toDto() {
        return MemberDto.builder()
                .seq(this.seq)
                .name(this.name)
                .age(this.age)
                .address(this.address)
                .build();
    }
}
