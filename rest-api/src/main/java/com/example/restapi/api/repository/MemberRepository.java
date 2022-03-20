package com.example.restapi.api.repository;

import com.example.restapi.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 repository
 * @author kdh
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
}
