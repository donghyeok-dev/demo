package com.example.testh2.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String name;
    private String mobile;
}
