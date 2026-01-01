package com.cts.inventory_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrorMessage {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private Map<String, String> errors;
}