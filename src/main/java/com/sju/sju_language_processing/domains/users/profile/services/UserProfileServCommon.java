package com.sju.sju_language_processing.domains.users.profile.services;

import com.sju.sju_language_processing.commons.message.MessageConfig;
import com.sju.sju_language_processing.domains.users.profile.entity.Permission;
import com.sju.sju_language_processing.domains.users.profile.entity.UserProfile;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;

public interface UserProfileServCommon {
    MessageSource userMsgSrc = MessageConfig.getUserMsgSrc();

    // Common utility function for handling User Profile
    // Restriction: The code which has to be dependent with repository can't placed here.
    default UserProfile fetchCurrentUser(Authentication auth) {
        return (UserProfile) auth.getPrincipal();
    }

    // Distinguish whether user is Admin or general User
    default boolean checkCurrentUserIsAdmin(Authentication auth) {
        UserProfile profile = this.fetchCurrentUser(auth);
        return profile.getPermissions().contains(Permission.ADMIN);
    }
    default boolean checkCurrentUserIsUser(Authentication auth) {
        UserProfile profile = this.fetchCurrentUser(auth);
        return profile.getPermissions().contains(Permission.USER);
    }
}
