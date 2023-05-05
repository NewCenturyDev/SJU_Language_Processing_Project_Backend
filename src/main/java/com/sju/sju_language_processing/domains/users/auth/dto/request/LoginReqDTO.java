package com.sju.sju_language_processing.domains.users.auth.dto.request;

import lombok.Data;

@Data
public class LoginReqDTO {
    private String email;
    private String password;
}
