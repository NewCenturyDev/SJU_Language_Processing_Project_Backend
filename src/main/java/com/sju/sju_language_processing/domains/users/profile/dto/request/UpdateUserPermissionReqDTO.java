package com.sju.sju_language_processing.domains.users.profile.dto.request;

import com.sju.sju_language_processing.domains.users.profile.entity.Permission;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateUserPermissionReqDTO {
    @NotBlank(message = "valid.users.id.blank")
    @PositiveOrZero(message = "valid.users.id.positive")
    private Long id;
    @NotNull(message = "valid.users.permission.null")
    private Permission newPermission;
}
