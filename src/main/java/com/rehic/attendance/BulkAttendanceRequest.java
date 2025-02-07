package com.rehic.attendance;

import java.time.LocalDate;
import java.util.List;

public record BulkAttendanceRequest(
        LocalDate date,
        List<MemberAttendance> attendances) {}