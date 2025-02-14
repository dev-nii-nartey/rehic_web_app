package com.rehic.attendance;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rehic/attendance")
@AllArgsConstructor
@Data
public class AttendanceController {
    private AttendanceService attendanceService;

    @PostMapping("/bulk")
    public List<Attendance> recordBulkAttendance(@RequestBody BulkAttendanceRequest request) {
        return attendanceService.recordBulkAttendance(request);
    }

    @PatchMapping
    public Attendance recordAttendance(Attendance attendance) {
        return attendanceService.recordAttendance(attendance);
    }

    @GetMapping
    public List<Attendance> getMemberAttendance(String email) {
        return attendanceService.getMemberAttendance(email);
    }

    @PostMapping("/date")
    public List<Attendance> getAttendanceForDate(LocalDate date) {
        return attendanceService.getAttendanceForDate(date);
    }

}
