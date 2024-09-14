package com.dbs.club.infrastructure.meetingjoin;

import com.dbs.club.domain.meetingjoin.MeetingJoin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingJoinRepository extends JpaRepository<MeetingJoin, Long> {
        Page<MeetingJoin> findByMemberId(Long member_id, Pageable pageable);

}
