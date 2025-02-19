package com.rehic.attendance;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Repository
public interface AttendanceRepo extends MongoRepository<Attendance, UUID> {
    List<Attendance> findByMemberEmail(String memberEmail);
    List<Attendance> findByDate(LocalDate date);

    @Query(value = "{'memberEmail': ?0, 'date': ?1}", exists = true)
    boolean existsByMemberEmailAndDate(String memberEmail, LocalDate date);
}
