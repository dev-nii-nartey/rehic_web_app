package com.rehic.attendance;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
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
    public Page<Attendance> getMemberAttendance(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize

            ) {
        return attendanceService.getMemberAttendance(email,pageNumber,pageSize );
    }

    @PostMapping("/date")
    public List<Attendance> getAttendanceForDate(LocalDate date) {
        return attendanceService.getAttendanceForDate(date);
    }

}
