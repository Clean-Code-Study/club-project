package com.dbs.club.domain.board.exception;

import com.dbs.club.domain.common.BusinessException;
import com.dbs.club.domain.common.exception.ErrorCode;

public class BoardException extends BusinessException {

    public BoardException(ErrorCode errorCode) { super(errorCode); }
}
