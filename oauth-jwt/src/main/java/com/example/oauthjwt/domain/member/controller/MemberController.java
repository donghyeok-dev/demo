package com.example.oauthjwt.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/getMember/{memberNo}")
    public String getMember(@PathVariable Long memberNo) {
        return "receive :  " + memberNo;
    }
}
