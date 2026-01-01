package com.cts.auth_service.dtos;

import lombok.Data;

@Data
public class
LoginDto {
    private String usernameOrEmail;
    private String password;
}
