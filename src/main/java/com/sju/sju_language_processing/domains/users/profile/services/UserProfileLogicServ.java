package com.sju.sju_language_processing.domains.users.profile.services;

import com.sju.sju_language_processing.commons.exception.objects.DTOValidityException;
import com.sju.sju_language_processing.domains.users.profile.entity.Permission;
import com.sju.sju_language_processing.domains.users.profile.repository.UserProfileRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class UserProfileLogicServ implements UserProfileServCommon {
    protected final UserProfileRepo userProfileRepo;
    protected final PasswordEncoder pwEncoder;

    protected List<Permission> grantUserPerm() {
        List<Permission> perms = new ArrayList<>();
        perms.add(Permission.USER);
        return perms;
    }

    protected List<Permission> grantAdminPerm() {
        List<Permission> perms = new ArrayList<>();
        perms.add(Permission.ADMIN);
        return perms;
    }

    protected void checkEmailDuplication(String email) throws Exception {
        // 이메일 중복 확인
        if (email != null && userProfileRepo.existsByEmail(email)) {
            throw new DTOValidityException(
                    userMsgSrc.getMessage("error.users.dup.email",null, Locale.ENGLISH)
            );
        }
    }

    protected void checkPhoneDuplication(String phone) throws Exception {
        // 전화번호 중복 확인
        if (phone != null && userProfileRepo.existsByPhone(phone.replaceAll("-", ""))) {
            throw new DTOValidityException(
                    userMsgSrc.getMessage("error.users.dup.phone",null,Locale.ENGLISH)
            );
        }
    }
}
