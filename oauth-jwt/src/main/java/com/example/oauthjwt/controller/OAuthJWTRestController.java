package com.example.oauthjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthJWTRestController {

    @GetMapping("/getItem/{itemNo}")
    public String getItem(@PathVariable Long itemNo) {
        return "receive :  " + itemNo;
    }
}
