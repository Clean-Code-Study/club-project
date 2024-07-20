package com.dbs.club.infrastructure.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbs.club.domain.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
