package com.codecool.budapestgo.dao.model.pass;

import lombok.Getter;

@Getter
public enum PassType {
    DAILY(1L),
    WEEKLY(7L),
    MONTHLY(30L);
    private final Long expireInDay;

    PassType(Long expireInDay) {
        this.expireInDay = expireInDay;
    }

}
