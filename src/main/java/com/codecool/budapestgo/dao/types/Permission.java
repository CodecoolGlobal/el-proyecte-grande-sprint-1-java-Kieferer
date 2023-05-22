package com.codecool.budapestgo.dao.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    CLIENT_READ("client:read"),
    CLIENT_UPDATE("client:update"),
    CLIENT_CREATE("client:create"),
    CLIENT_DELETE("client:delete"),

    PASS_READ("pass:read"),
    PASS_UPDATE("pass:update"),
    PASS_CREATE("pass:create"),
    PASS_DELETE("pass:delete"),

    ROUTE_READ("route:read"),
    ROUTE_UPDATE("route:update"),
    ROUTE_CREATE("route:create"),
    ROUTE_DELETE("route:delete"),

    SCHEDULE_READ("schedule:read"),
    SCHEDULE_UPDATE("schedule:update"),
    SCHEDULE_CREATE("schedule:create"),
    SCHEDULE_DELETE("schedule:delete"),

    STOP_READ("stop:read"),
    STOP_UPDATE("stop:update"),
    STOP_CREATE("stop:create"),
    STOP_DELETE("stop:delete");
    @Getter
    private final String permission;
}
