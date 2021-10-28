package com.example.oauthjwt.config.oauth2.info;

import java.util.Map;

public class FacebookOAuth2UserInfo extends OAuth2UserInfo{

    public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    @Override
    public String getId() {
        return getAttributeFromAttributeMap("id");
    }

    @Override
    public String getName() {
        return getAttributeFromAttributeMap("name");
    }

    @Override
    public String getEmail() {
        return getAttributeFromAttributeMap("email");
    }

    @Override
    public String getPictureUrl() {
        return getAttributeFromAttributeMap("imageUrl");
    }
}
