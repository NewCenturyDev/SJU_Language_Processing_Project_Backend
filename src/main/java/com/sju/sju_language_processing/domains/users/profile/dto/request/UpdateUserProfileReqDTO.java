package com.sju.sju_language_processing.domains.users.profile.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserProfileReqDTO {
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
