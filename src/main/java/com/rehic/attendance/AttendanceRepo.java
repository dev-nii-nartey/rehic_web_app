package com.rehic.attendance;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AttendanceRepo  extends MongoRepository<Attendance, String> {
    List<Attendance> findByMemberEmail(String memberEmail);
    List<Attendance> findByDate(LocalDate date);
    boolean existsByMemberEmailAndDate(String memberEmail, LocalDate date);
}
