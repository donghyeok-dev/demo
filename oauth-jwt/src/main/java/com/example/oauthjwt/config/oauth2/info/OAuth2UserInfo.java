package com.example.oauthjwt.config.oauth2.info;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getPictureUrl();

    protected String getAttributeFromAttributeMap(String attributeName) {
        return (String) attributes.get(attributeName);
    }

    protected String getAttributeFromAttributeMap(String attributeName, String parentAttributeName) {
        Map<String, Object> response = (Map<String, Object>) attributes.get(parentAttributeName);

        return response != null ? (String) response.get(attributeName) : null;
    }
}
