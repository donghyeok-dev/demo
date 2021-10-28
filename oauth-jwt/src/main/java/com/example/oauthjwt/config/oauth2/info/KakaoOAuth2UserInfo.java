package com.example.oauthjwt.config.oauth2.info;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return getAttributeFromAttributeMap("id");
    }

    @Override
    public String getName() {
        return getAttributeFromAttributeMap("nickname", "properties");
    }

    @Override
    public String getEmail() {
        return getAttributeFromAttributeMap("account_email", "properties");
    }

    @Override
    public String getPictureUrl() {
        return getAttributeFromAttributeMap("thumbnail_image", "properties");
    }
}
