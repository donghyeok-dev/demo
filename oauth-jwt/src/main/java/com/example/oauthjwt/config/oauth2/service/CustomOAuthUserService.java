package com.example.oauthjwt.config.oauth2.service;

import com.example.oauthjwt.config.oauth2.enums.ProviderType;
import com.example.oauthjwt.config.oauth2.info.OAuth2UserInfo;
import com.example.oauthjwt.config.oauth2.info.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration()
                .getRegistrationId()
                .toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());

//        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(providerType, userNameAttributeName, oAuth2User.getAttributes());
//
//        log.info(">>> {}", oAuth2Attribute);
//
//        Map<String, Object> attributes = oAuth2Attribute.convertToMap();
//        Collection<? extends GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//
//        return new DefaultOAuth2User(roles, attributes, "email");
        return null;
    }
}