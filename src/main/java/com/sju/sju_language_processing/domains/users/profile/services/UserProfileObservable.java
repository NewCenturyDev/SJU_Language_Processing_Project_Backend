package com.sju.sju_language_processing.domains.users.profile.services;

import com.sju.sju_language_processing.domains.users.profile.entity.UserProfile;

public interface UserProfileObservable {
    UserProfile fetchUserProfileById(Long userId) throws Exception;
}
