package com.sju.sju_language_processing.domains.users.profile.repository;

import com.sju.sju_language_processing.domains.users.profile.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepo extends CrudRepository<UserProfile, Long> {
    Optional<UserProfile> findByEmail(String email);
    Optional<UserProfile> findByUsername(String username);

    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
}
