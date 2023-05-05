package com.sju.sju_language_processing.domains.users.profile.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserProfileReqDTO {
    @Email(message = "valid.users.email.email")
    @NotBlank(message = "valid.users.email.blank")
    @Size(max = 50, message = "valid.users.email.size")
    private String email;

    @NotBlank(message = "valid.users.password.blank")
    @Size(min = 8, max = 20, message = "valid.users.password.size")
    private String password;

    @NotBlank(message = "valid.users.username.blank")
    @Size(min = 2, max = 20, message = "valid.users.username.size")
    private String username;

    @NotBlank(message = "valid.users.phone.blank")
    @Size(min = 9, max = 13, message = "valid.users.phone.size")
    @Pattern(regexp = "(^02|^\\d{3})-(\\d{3}|\\d{4})-\\d{4}", message = "valid.users.phone.phone")
    private String phone;

    @NotBlank(message = "valid.users.name.blank")
    @Size(max = 50, message = "valid.users.name.size")
    private String name;
}
