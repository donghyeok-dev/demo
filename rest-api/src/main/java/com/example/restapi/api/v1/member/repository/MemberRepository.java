package com.example.restapi.api.v1.member.repository;

import com.example.restapi.api.v1.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 repository
 * @author kdh
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNameAndBirthDay(String name, String birthDay);
}
