package com.sju.sju_language_processing.domains.users.profile.dto.response;

import com.sju.sju_language_processing.commons.http.GeneralResDTO;
import com.sju.sju_language_processing.domains.users.profile.entity.UserProfile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateUserProfileResDTO extends GeneralResDTO {
    private UserProfile createdUserProfile;
}
