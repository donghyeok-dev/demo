package com.example.oauthjwt.config.oauth2.enums;

import com.example.oauthjwt.common.enums.ConverterEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProviderType implements ConverterEnum {
    GOOGLE("1", "구글"),
    FACEBOOK("2", "페이스북"),
    NAVER("3", "네이버"),
    KAKAO("4", "카카오");

    private final String code;
    private final String codeName;
}
