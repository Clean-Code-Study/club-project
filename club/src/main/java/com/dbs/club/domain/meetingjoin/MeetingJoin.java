package com.dbs.club.domain.meetingjoin;

import com.dbs.club.domain.common.BaseEntity;
import com.dbs.club.domain.meeting.Meeting;
import com.dbs.club.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MeetingJoin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "memberId", referencedColumnName = "id")
    private Member member;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "meetingId", referencedColumnName = "id")
    private Meeting meeting;

    @Column(name = "status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private MeetingJoinState status;

    public void cancel() {
        this.status = MeetingJoinState.CANCEL;
    }

    public boolean isMeetingDateBeforeToday() {
        return this.getMeeting().getDate().isBefore(LocalDate.now());
    }

    public void updateStatus(MeetingJoinState status) {
        this.status = status;
    }

}
