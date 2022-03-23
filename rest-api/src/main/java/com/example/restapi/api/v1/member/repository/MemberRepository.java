package com.example.restapi.api.v1.member.repository;

import com.example.restapi.api.v1.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 repository
 * @author kdh
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNameAndBirthDay(String name, String birthDay);
}
