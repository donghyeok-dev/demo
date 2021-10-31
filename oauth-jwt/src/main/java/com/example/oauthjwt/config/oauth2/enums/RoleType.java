package com.example.oauthjwt.config.oauth2.enums;

import com.example.oauthjwt.common.enums.ConverterEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType implements ConverterEnum {
    ANONYMOUS("ROLE_ANONYMOUS", "익명"),
    USER("ROLE_USER", "회원"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String code;
    private final String codeName;
}
