package com.dbs.club.infrastructure.meetingjoin;

import com.dbs.club.domain.meetingjoin.MeetingJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MeetingJoinRepository extends JpaRepository<MeetingJoin, Long> {
}
