package com.example.restapi.api.controller;

import com.example.restapi.api.dto.MemberDto;
import com.example.restapi.api.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Api(tags = "회원 API")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMember(@PathVariable Long id) {
        return new ResponseEntity<>(this.memberService.findMember(id), HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity createMember(@RequestBody @Valid MemberDto memberDto, Errors errors) {
//        if (errors.hasErrors()) {
//            return ResponseEntity.badRequest().body(errors);
//        }
//
//
//    }

}
