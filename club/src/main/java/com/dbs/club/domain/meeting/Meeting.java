package com.dbs.club.domain.meeting;

import com.dbs.club.domain.common.BaseEntity;
import com.dbs.club.domain.common.MeetingState;
import com.dbs.club.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Meeting extends BaseEntity {

    public static final String LOCATION_REGEX = "^[가-힣]+구 [가-힣]+동$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn( name = "member_id", referencedColumnName = "id")
    private Member member;

    @Column(name = "title", nullable = false, length = 90)
    private String title;

    @Column(name = "content", nullable = false, length = 1500)
    private String content;

    @Column(name = "location", nullable = false, length = 30)
    private String location;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "join_limit", nullable = false)
    private int joinLimit;

    @Column(name = "join_count", nullable = false)
    private int joinCount;

    @Column(name = "status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private MeetingState status;

    public void updateStatus(MeetingState status) {
        this.status = status;
    }

    public void updateJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public void update(
            String title,
            String content,
            String location,
            LocalDate date,
            int joinLimit
    ) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.date = date;
        this.joinLimit = joinLimit;
    }

    public boolean canNotUpdateMeetingDate() {
        return this.date.isBefore(LocalDate.now());
    }
}
