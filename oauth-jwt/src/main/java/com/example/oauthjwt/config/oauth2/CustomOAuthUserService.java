package com.example.oauthjwt.config.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String resourceServerUri = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        String accessToken = userRequest.getAccessToken().getTokenValue();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        log.info(String.format(">>> registrationId: %s, resourceServerUri: %s, accessToken: %s, userNameAttributeName: %s",
                registrationId,
                resourceServerUri,
                accessToken,
                userNameAttributeName
                ));

        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        log.info(">>> {}", oAuth2Attribute);

        Map<String, Object> attributes = oAuth2Attribute.convertToMap();
        Collection<? extends GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new DefaultOAuth2User(roles, attributes, "email");
    }
}
