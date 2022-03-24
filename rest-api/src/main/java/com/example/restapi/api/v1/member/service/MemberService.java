package com.example.restapi.api.v1.member.service;

import com.example.restapi.api.v1.member.entity.Member;
import com.example.restapi.api.v1.member.model.MemberModel;
import com.example.restapi.api.v1.member.model.MemberModelAssembler;
import com.example.restapi.global.error.exception.ConflictDataException;
import com.example.restapi.global.error.exception.InvalidParameterException;
import com.example.restapi.global.error.exception.NotFoundDataException;
import com.example.restapi.api.v1.member.model.MemberDto;
import com.example.restapi.api.v1.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원 서비스
 * @author kdh
 */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberModelAssembler memberModelAssembler;
    private final MemberRepository memberRepository;

    /*
        회원 목록 조회
     */
    @Transactional(readOnly = true)
    public CollectionModel<MemberModel> findAllMember() {
        return memberModelAssembler.toCollectionModel(this.memberRepository.findAll());
    }

    /*
        회원 조회
     */
    @Transactional(readOnly = true)
    public MemberModel findMember(Long id) {
        return memberModelAssembler.toModelDetail(this.memberRepository.findById(id)
                .orElseThrow(NotFoundDataException::new));
    }

    /*
        회원 등록
     */
    @Transactional
    public MemberModel saveMember(MemberDto memberDto) {
        if (this.memberRepository.existsByNameAndBirthDay(memberDto.getName(), memberDto.getBirthDay()))
            throw new ConflictDataException("이미 등록된 회원입니다.");

        return memberModelAssembler.toModelUpdate(this.memberRepository.save(memberDto.toEntity()));
    }

    /*
        회원 수정
     */
    @Transactional
    public MemberModel updateMember(Long id, MemberDto memberDto) {
        Member member = this.memberRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("존재하지 않는 회원입니다.", 1));

        member.updateAll(memberDto);

        return memberModelAssembler.toModelUpdate(this.memberRepository.save(member));
    }

    /*
        회원 삭제
     */
    @Transactional
    public Link deleteMember(Long id) {
        Member member = this.memberRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("존재하지 않는 회원입니다.", 1));

        this.memberRepository.delete(member);

        return memberModelAssembler.toModelDelete();
    }
}
