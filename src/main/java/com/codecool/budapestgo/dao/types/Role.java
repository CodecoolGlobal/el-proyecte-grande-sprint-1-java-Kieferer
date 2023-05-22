package com.codecool.budapestgo.dao.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codecool.budapestgo.dao.types.Permission.*;

@RequiredArgsConstructor
public enum Role {
    CUSTOMER(Set.of(
            ROUTE_READ,
            SCHEDULE_READ,
            STOP_READ
    )),
    EMPLOYEE(Set.of(
            CLIENT_READ,
            CLIENT_CREATE,
            CLIENT_UPDATE,
            PASS_READ,
            PASS_CREATE,
            PASS_UPDATE,
            ROUTE_READ,
            ROUTE_CREATE,
            ROUTE_UPDATE,
            SCHEDULE_READ,
            SCHEDULE_CREATE,
            SCHEDULE_UPDATE,
            STOP_READ,
            STOP_CREATE,
            STOP_UPDATE
    )),
    ADMIN(Arrays.stream(Permission.values()).collect(Collectors.toSet()));

    @Getter
    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
