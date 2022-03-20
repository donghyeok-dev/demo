package com.example.restapi.api.service;

import com.example.restapi.global.error.exception.NotFoundDataException;
import com.example.restapi.api.dto.MemberDto;
import com.example.restapi.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 서비스
 * @author kdh
 */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /*
        회원 조회
     */
    @Transactional(readOnly = true)
    public MemberDto findMember(Long id) {
        return this.memberRepository.findById(id)
                .orElseThrow(NotFoundDataException::new)
                .toDto();
    }
}
