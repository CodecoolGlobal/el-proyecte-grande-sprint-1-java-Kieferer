package com.codecool.budapestgo.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.codecool.budapestgo.security.ClientPermission.*;

public enum ClientRole {
    USER(Sets.newHashSet(
            ROUTE_READ,
            SCHEDULE_READ,
            STOP_READ
    )),
    EMPLOYEE(Sets.newHashSet(
            CLIENT_READ,
            CLIENT_WRITE,
            PASS_READ,
            PASS_WRITE,
            ROUTE_READ,
            ROUTE_WRITE,
            SCHEDULE_READ,
            SCHEDULE_WRITE,
            STOP_READ,
            STOP_WRITE
    ));

    private final Set<ClientPermission> permissions;

    ClientRole(Set<ClientPermission> permissions) {
        this.permissions = permissions;
    }
}
