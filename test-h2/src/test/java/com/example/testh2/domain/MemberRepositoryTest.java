package com.example.testh2.domain;

import com.example.testh2.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("local")
@DisplayName("Member Entity Jpa 테스트")
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("insert 테스트")
    void testInsert() {
        // given
        List<Member> members = new ArrayList<>();

        members.add(Member.builder()
                .name("세종대왕")
                .mobile("01030007777")
                .build());

        members.add(Member.builder()
                .name("장영실")
                .mobile("01055559999")
                .build());

        // when
        this.memberRepository.saveAll(members);
        List<Member> findMembers = this.memberRepository.findAll();

        // then
        assertEquals(2, findMembers.size());
        assertEquals("세종대왕", findMembers.get(0).getName());
        assertEquals("01030007777", findMembers.get(0).getMobile());
        assertEquals("장영실", findMembers.get(1).getName());
        assertEquals("01055559999", findMembers.get(1).getMobile());
    }

    @Test
    @DisplayName("Update 테스트")
    void testUpdate() {
        // given
        Member member = this.memberRepository.save(Member.builder()
                .name("세종대왕")
                .mobile("01030007777")
                .build());

        // when
        member.updateMember(MemberDto.builder()
                .id(member.getId())
                .name("문종")
                .mobile("01040005555")
                .build());

        this.entityManager.persistAndFlush(member); // Manually Dirty Checking
        this.entityManager.clear();

        Optional<Member> findMember = this.memberRepository.findById(member.getId());

        // then
        assertTrue(findMember.isPresent());
        assertEquals("문종", findMember.get().getName());
        assertEquals("01040005555", findMember.get().getMobile());
    }
}