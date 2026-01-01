package com.cts.auth_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SuccessDto {
    private String message;
    private LocalDateTime timestamp;
}