package com.dbs.club.infrastructure.meetingjoin;

import com.dbs.club.domain.meeting.Meeting;
import com.dbs.club.domain.meetingjoin.MeetingJoin;
import com.dbs.club.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MeetingJoinRepository extends JpaRepository<MeetingJoin, Long> {
        Page<MeetingJoin> findByMemberId(Long member_id, Pageable pageable);
        boolean existsByMemberAndMeeting(Member member, Meeting meeting);
        boolean existsByMemberAndMeetingDate(Member member, LocalDate meetingDate);

}
