package com.example.oauthjwt.config.oauth2.info;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo{

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return getAttributeFromAttributeMap("id", "response");
    }

    @Override
    public String getName() {
        return getAttributeFromAttributeMap("nickname", "response");
    }

    @Override
    public String getEmail() {
        return getAttributeFromAttributeMap("email", "response");
    }

    @Override
    public String getPictureUrl() {
        return getAttributeFromAttributeMap("profile_image", "response");
    }


}
