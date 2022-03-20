package com.example.restapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 회원 Dto
 * @author kdh
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long seq;
    @NotEmpty
    private String name;
    @NotEmpty
    private Integer age;
    @NotEmpty
    private String address;
}
