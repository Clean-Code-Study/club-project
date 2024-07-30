package com.dbs.club.domain.meetingjoin;

import com.dbs.club.domain.common.BaseEntity;
import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MeetingJoin extends BaseEntity {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

//    @ManyToOne(cascade=CascadeType.REMOVE)
//    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
//    private Meeting meeting;

    @Column(name = "status", nullable = false, length = 30)
    private RegisterDeleteState status;

    public MeetingJoin(Member member, RegisterDeleteState status) {
        //매개변수에 Meeting meeting 추가
        this.member = member;
        //this.meeting = meeting;
        this.status = status;
    }

    public static MeetingJoin init(Member member, RegisterDeleteState status) {
        //매개변수에 Meeting meeting 추가 후 return 값에도 meeting 추가
        return new MeetingJoin(member, status);
    }
}
