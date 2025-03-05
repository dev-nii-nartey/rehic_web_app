package com.rehic.attendance;

import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    void recordBulkAttendance(BulkAttendanceRequest request);
    void recordBulkAttendance2(BulkAttendanceRequest request);
    Attendance recordAttendance(Attendance attendance);
    Page<Attendance> getMemberAttendance(String memberEmail, int pageNumber, int pageSize);
    List<Attendance> getAttendanceForDate(LocalDate date);
}
