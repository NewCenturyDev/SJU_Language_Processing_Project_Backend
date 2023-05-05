package com.sju.sju_language_processing.domains.users.profile.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserPasswordReqDTO {
    @NotBlank(message = "valid.users.password.blank")
    @Size(min = 8, max = 20, message = "valid.users.password.size")
    String password;
    @NotBlank(message = "valid.users.password.blank")
    @Size(min = 8, max = 20, message = "valid.users.password.size")
    String newPassword;
}
