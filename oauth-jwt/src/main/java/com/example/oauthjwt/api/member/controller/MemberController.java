package com.example.oauthjwt.api.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member/")
public class MemberController {

    @GetMapping("/getMember/{memberNo}")
    public String getMember(@PathVariable Long memberNo) {
        return "receive :  " + memberNo;
    }
}
