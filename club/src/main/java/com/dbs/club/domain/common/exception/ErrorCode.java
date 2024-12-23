package com.dbs.club.domain.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

    COMMON_SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    COMMON_INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "요청한 값이 올바르지 않습니다."),

    MEMBER_LOGIN_ID_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 사용중인 아이디입니다."),
    MEMBER_NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 사용중인 닉네임입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    MEETING_JOIN_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 신청된 모임입니다."),
    MEETING_JOIN_DATE_DUPLICATE(HttpStatus.BAD_REQUEST, "같은 일자의 모임은 신청할 수 없습니다."),
    MEETING_JOIN_NOT_FOUND(HttpStatus.NOT_FOUND, "모임신청 내역을 찾을 수 없습니다."),
    MEETING_JOIN_CAN_NOT_CANCEL(HttpStatus.BAD_REQUEST, "모임 참여 후에는 취소할 수 없습니다."),
    MEETING_JOIN_PERMISSION_DENIED(HttpStatus.BAD_REQUEST, "본인이 가입한 모임만 취소할 수 있습니다."),

    MEETING_NOT_FOUND(HttpStatus.NOT_FOUND, "모임을 찾을 수 없습니다."),

    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),

    MEETING_CAN_NOT_UPDATE(HttpStatus.BAD_REQUEST, "모임 수정이 불가능합니다."),
    MEETING_CAN_NOT_DELETE(HttpStatus.BAD_REQUEST, "모임 삭제가 불가능합니다."),
    MEETING_STATUS_NOT_OPEN(HttpStatus.BAD_REQUEST, "모집중이 아닌 모임은 신청할 수 없습니다."),
    BOARD_PROFANITY_FOUND(HttpStatus.BAD_REQUEST, "부적절한 단어가 포함되어 있습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
