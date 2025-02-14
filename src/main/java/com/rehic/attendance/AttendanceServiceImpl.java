package com.rehic.attendance;


import com.rehic.members.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private MemberRepository memberRepository;
    private AttendanceRepo attendanceRepo;


    @Override
    public List<Attendance> recordBulkAttendance(BulkAttendanceRequest request) {
        if (request.date().getDayOfWeek() != DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Attendance can only be recorded for Sundays");
        }
          return request.attendances().stream()
                .map(memberAttendance -> createAttendanceRecord(request.date(), memberAttendance))
                .map(this::saveValidatedAttendance)
                .toList();
    }

    @Override
    public Attendance recordAttendance(Attendance attendance) {
        validateRecording(attendance);
         return attendanceRepo.save(attendance);
    }

    @Override
    public List<Attendance> getMemberAttendance(String memberEmail) {
        return attendanceRepo.findByMemberEmail(memberEmail);
    }

    @Override
    public List<Attendance> getAttendanceForDate(LocalDate date) {
        return attendanceRepo.findByDate(date);
    }


    private void validateRecording(Attendance attendance) {
        if (attendance.date().getDayOfWeek() != DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Attendance can only be recorded for Sundays");
        }
        memberRepository.findById(attendance.memberEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        if (attendanceRepo.existsByMemberEmailAndDate(attendance.memberEmail(), attendance.date())) {
            throw new IllegalArgumentException("Attendance already recorded for this date");
        }
    }

    private Attendance createAttendanceRecord(LocalDate date, MemberAttendance memberAttendance) {
        return new Attendance(
                UUID.randomUUID(),
                memberAttendance.memberEmail(),
                date,
                memberAttendance.attended()
        );
    }

    private Attendance saveValidatedAttendance(Attendance attendance) {
        validateRecording(attendance);
        return attendanceRepo.save(attendance);
    }
}
