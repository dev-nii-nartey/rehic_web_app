package com.rehic.attendance;

public record MemberAttendance(
        String memberEmail,
        boolean attended
) {}