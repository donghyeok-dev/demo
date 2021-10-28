package com.example.oauthjwt.config.oauth2.enums;

import com.example.oauthjwt.common.enums.ConverterEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProviderType implements ConverterEnum {
    GOOGLE("GOOGLE", "구글"),
    FACEBOOK("FACEBOOK", "페이스북"),
    NAVER("NAVER", "네이버"),
    KAKAO("KAKAO", "카카오");

    private final String code;
    private final String codeName;
}
