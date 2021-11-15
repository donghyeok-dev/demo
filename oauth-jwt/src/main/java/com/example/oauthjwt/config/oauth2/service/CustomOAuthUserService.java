package com.example.oauthjwt.config.oauth2.service;

import com.example.oauthjwt.config.oauth2.enums.ProviderType;
import com.example.oauthjwt.config.oauth2.enums.RoleType;
import com.example.oauthjwt.config.oauth2.exceptions.OAuth2ProviderMissMatchException;
import com.example.oauthjwt.config.oauth2.info.OAuth2UserInfo;
import com.example.oauthjwt.config.oauth2.info.OAuth2UserInfoFactory;
import com.example.oauthjwt.config.security.user.AuthCustomUser;
import com.example.oauthjwt.api.member.entity.Member;
import com.example.oauthjwt.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration()
                .getRegistrationId()
                .toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());
        Member member = memberRepository.findByUserId(userInfo.getId()).orElse(null);

        if(member != null) {
            if(providerType != member.getProviderType()) {
                throw new OAuth2ProviderMissMatchException(String.format("%s로 가입되어 있지만 %s로 로그인 시도 하셨습니다.",
                        member.getProviderType().getCodeName(),
                        providerType.getCodeName()));
            }

            member.modifyProviderMember(userInfo.getName(), userInfo.getPictureUrl());
        }else {
            member = this.memberRepository.save(Member.builder()
                    .userId(userInfo.getId())
                    .userName(userInfo.getName())
                    .email(userInfo.getEmail())
                    .verifiedEmail(true)
                    .pictureUrl(userInfo.getPictureUrl())
                    .providerType(providerType)
                    .roleType(RoleType.USER)
                    .build());
        }

        return AuthCustomUser.builder()
                .userId(member.getUserId())
                .providerType(member.getProviderType())
                .roleType(member.getRoleType())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode())))
                .attributes(oAuth2User.getAttributes())
                .build();
    }
}