package com.rehic.attendance;


import com.rehic.members.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private MemberRepository memberRepository;
    private AttendanceRepo attendanceRepo;



    //BULK PROCESSING (atomicity not considered ) liable to partial update and no rollbacks
    @Override
    public void recordBulkAttendance(BulkAttendanceRequest request) {
        List<List<MemberAttendance>> batches = makeBatch(request.attendances());

        List<CompletableFuture<Void>> futures = batches.stream()
                .map(batch -> CompletableFuture.runAsync(() -> processBatch(request.date(), batch))).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }


    //Improved Bulk processing
    @Override
    public void recordBulkAttendance2(BulkAttendanceRequest request) {
        List<List<MemberAttendance>> batches = makeBatch(request.attendances());
        List<Attendance> savedRecords = new ArrayList<>();

        try {
            List<CompletableFuture<Void>> futures = batches.stream()
                    .map(batch -> CompletableFuture.runAsync(() -> {
                        List<Attendance> records = processBatch2(request.date(), batch);
                        synchronized (savedRecords) {
                            savedRecords.addAll(records);
                        }
                    }))
                    .toList();

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            // Rollback changes in case of error
            savedRecords.forEach(attendanceRepo::delete);
            throw new RuntimeException("Bulk operation failed, changes rolled back", e);
        }
    }



    @Override
    public Attendance recordAttendance(Attendance attendance) {
        validateRecording(attendance);
        return attendanceRepo.save(attendance);
    }

    @Override
    public Page<Attendance> getMemberAttendance(String memberEmail, int PageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(PageNumber, pageSize);
        return attendanceRepo.findByMemberEmail(pageRequest, memberEmail);
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
        return attendanceRepo.save(attendance); // Return the saved entity
    }

    private List<List<MemberAttendance>> makeBatch(List<MemberAttendance> attendances) {
        int batchSize = 20;
        List<List<MemberAttendance>> batch = new ArrayList<>();
        for (int i = 0; i < attendances.size(); i += batchSize) {
            int endIndex = Math.min(batchSize, i + batchSize);
            batch.add(attendances.subList(i, endIndex));
        }
        return batch;
    }

    private void processBatch(LocalDate date, List<MemberAttendance> batch) {
        batch.stream().map(memberAttendance -> createAttendanceRecord(date, memberAttendance))
                .forEach(this::saveValidatedAttendance);
    }

    private List<Attendance> processBatch2(LocalDate date, List<MemberAttendance> batch) {
        return batch.stream()
                .map(memberAttendance -> createAttendanceRecord(date, memberAttendance))
                .map(this::saveValidatedAttendance)
                .toList();
    }
}
