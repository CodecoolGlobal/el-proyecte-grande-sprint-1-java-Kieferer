package com.codecool.budapestgo.security;

public enum ClientPermission {
    CLIENT_READ("client:read"),
    CLIENT_WRITE("client:write"),
    PASS_READ("pass:read"),
    PASS_WRITE("pass:write"),
    ROUTE_READ("route:read"),
    ROUTE_WRITE("rotue:write"),
    SCHEDULE_READ("schedule:read"),
    SCHEDULE_WRITE("schedule:write"),
    STOP_READ("stop:read"),
    STOP_WRITE("stop:write");

    private final String permission;

    ClientPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
