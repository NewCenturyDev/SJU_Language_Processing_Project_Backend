package com.sju.sju_language_processing.domains.users.profile.dto.request;

import com.sju.sju_language_processing.domains.users.profile.entity.Permission;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FetchUserProfileReqDTO {
    @Email(message = "valid.users.email.email")
    @Size(max = 50, message = "valid.users.email.size")
    private String email;
    @Size(min = 2, max = 20, message = "valid.users.username.size")
    private String username;
    private Permission permission;
}
