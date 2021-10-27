package com.example.oauthjwt.config.oauth2;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OAuth2Token {
    private String token;
    private String refreshToken;
}
