package com.rehic.attendance;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Document(collection = "attendances")
public record Attendance(UUID id, @Id @Indexed String memberEmail, LocalDate date, boolean attended) {
}
