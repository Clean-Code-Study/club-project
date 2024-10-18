package com.dbs.club.domain.common;

public enum MeetingState {

    OPEN, FULL, DELETED;

    public boolean isNotOpen() {
        return !this.equals(OPEN);
    }

}
