package com.rehic.execptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String message;
    private Instant timestamp;
}