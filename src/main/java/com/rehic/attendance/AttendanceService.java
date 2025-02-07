package com.rehic.attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    List<Attendance> recordBulkAttendance(BulkAttendanceRequest request);
    Attendance recordAttendance(Attendance attendance);
    List<Attendance> getMemberAttendance(String memberEmail);
    List<Attendance> getAttendanceForDate(LocalDate date);
}
