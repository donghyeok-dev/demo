package com.example.oauthjwt.config.oauth2.handler;

import com.example.oauthjwt.config.oauth2.token.OAuth2Token;
import com.example.oauthjwt.config.oauth2.token.OAuth2TokenService;
import com.example.oauthjwt.config.security.user.AuthCustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final OAuth2TokenService oAuth2TokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        if(response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to ");
            return;
        }
        AuthCustomUser authCustomUser = (AuthCustomUser) authentication.getPrincipal();
        OAuth2Token oAuth2Token = oAuth2TokenService.generateToken(authCustomUser.getUserId(), authCustomUser.getRoleType().getCode());


        System.out.println(">>>>>>>>>>>>> onAuthenticationSuccess : " + oAuth2User.getAttributes());
    }
}
