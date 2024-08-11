package com.dbs.club.domain.member;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbs.club.domain.common.RegisterDeleteState;
import com.dbs.club.domain.member.exception.MemberException;
import com.dbs.club.infrastructure.member.MemberRepository;
import com.dbs.club.presentation.member.MemberRequestDto;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMember_ShouldThrowException_WhenLoginIdIsDuplicated() {
        // Given
        String duplicatedLoginId = "user123";
        MemberRequestDto.Create createRequest = new MemberRequestDto.Create(
            duplicatedLoginId,
            "password@123",
            "회원이름",
            "01012345678",
            "testNickname",
            LocalDate.of(1990, 1, 1),
            MemberGenderType.MALE,
            "music"
        );

        Member duplicatedMember = new Member(
            duplicatedLoginId, "password@123", "테스트이름", "01012345678", "testNickname",
            LocalDate.of(1990, 1, 1), MemberGenderType.MALE, "music", RegisterDeleteState.REGISTERED
        );

        // When
        when(memberRepository.findByLoginId(duplicatedLoginId)).thenReturn(Optional.of(duplicatedMember));

        // Then
        assertThrows(MemberException.class, () -> {
            memberService.createMember(createRequest);
        });
    }

    @Test
    void createMember_ShouldThrowException_WhenNicknameIsDuplicated() {
        // Given
        String duplicatedNickname = "testNickname";
        MemberRequestDto.Create createRequest = new MemberRequestDto.Create(
            "user123",
            "password@123",
            "회원이름",
            "01012345678",
            duplicatedNickname,
            LocalDate.of(1990, 1, 1),
            MemberGenderType.MALE,
            "music"
        );

        Member duplicatedMember = new Member(
            "user123", "password@123", "테스트이름", "01012345678", duplicatedNickname,
            LocalDate.of(1990, 1, 1), MemberGenderType.MALE, "music", RegisterDeleteState.REGISTERED
        );

        // When
        when(memberRepository.findByNickname(duplicatedNickname)).thenReturn(Optional.of(duplicatedMember));

        // Then
        assertThrows(MemberException.class, () -> {
            memberService.createMember(createRequest);
        });
    }
}
