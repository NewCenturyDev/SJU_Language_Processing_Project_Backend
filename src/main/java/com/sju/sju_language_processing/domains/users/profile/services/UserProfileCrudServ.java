package com.sju.sju_language_processing.domains.users.profile.services;

import com.sju.sju_language_processing.domains.users.profile.dto.request.CreateUserProfileReqDTO;
import com.sju.sju_language_processing.domains.users.profile.dto.request.UpdateUserPasswordReqDTO;
import com.sju.sju_language_processing.domains.users.profile.dto.request.UpdateUserPermissionReqDTO;
import com.sju.sju_language_processing.domains.users.profile.dto.request.UpdateUserProfileReqDTO;
import com.sju.sju_language_processing.domains.users.profile.entity.Permission;
import com.sju.sju_language_processing.domains.users.profile.entity.UserProfile;
import com.sju.sju_language_processing.domains.users.profile.repository.UserProfileRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class UserProfileCrudServ extends UserProfileLogicServ implements UserProfileServCommon, UserProfileObservable {
    public UserProfileCrudServ(UserProfileRepo userProfileRepo, PasswordEncoder pwEncoder) {
        super(userProfileRepo, pwEncoder);
    }

    @Transactional
    public UserProfile createAdmin(CreateUserProfileReqDTO reqDTO) throws Exception {
        this.checkEmailDuplication(reqDTO.getEmail());
        this.checkPhoneDuplication(reqDTO.getPhone());

        UserProfile userProfile = UserProfile.builder()
                .email(reqDTO.getEmail())
                .password(pwEncoder.encode(reqDTO.getPassword()))
                .username(reqDTO.getUsername())
                .phone(reqDTO.getPhone())
                .name(reqDTO.getName())

                .permissions(this.grantAdminPerm())
                .active(true)
                .build();

        return userProfileRepo.save(userProfile);
    }

    @Override
    public UserProfile fetchUserProfileById(Long userId) throws Exception {
        return userProfileRepo.findById(userId)
                .orElseThrow(() -> new Exception(
                        userMsgSrc.getMessage("error.users.notExist", null, Locale.ENGLISH)
                ));
    }
    public UserProfile fetchUserProfileByEmail(String email) throws Exception {
        return userProfileRepo.findByEmail(email)
                .orElseThrow(() -> new Exception(
                        userMsgSrc.getMessage("error.users.notExist", null, Locale.ENGLISH)
                ));
    }
    public UserProfile fetchUserProfileByUsername(String username) throws Exception {
        return userProfileRepo.findByUsername(username)
                .orElseThrow(() -> new Exception(
                        userMsgSrc.getMessage("error.users.notExist", null, Locale.ENGLISH)
                ));
    }

    @Transactional
    public UserProfile updateUserProfile(Authentication auth, UpdateUserProfileReqDTO reqDTO) {
        UserProfile userProfile = this.fetchCurrentUser(auth);

        userProfile.setUsername(reqDTO.getUsername());
        userProfile.setPhone(reqDTO.getPhone());
        userProfile.setName(reqDTO.getName());

        return userProfileRepo.save(userProfile);
    }

    @Transactional
    public void updateUserPassword(Authentication auth, UpdateUserPasswordReqDTO reqDTO) {
        UserProfile userProfile = this.fetchCurrentUser(auth);

        // 기존 비밀번호 일치 확인
        if (!pwEncoder.matches(reqDTO.getPassword(), userProfile.getPassword())) {
            throw new BadCredentialsException(
                    userMsgSrc.getMessage("error.login.fail", null, Locale.ENGLISH)
            );
        }

        // 새로운 비밀번호가 기존 비밀번호와 다르면 업데이트
        if (!pwEncoder.encode(userProfile.getPassword()).equals(reqDTO.getNewPassword())) {
            userProfile.setPassword(pwEncoder.encode(reqDTO.getNewPassword()));
        }
        userProfileRepo.save(userProfile);
    }

    @Transactional
    public void updateUserPermission(Authentication auth, UpdateUserPermissionReqDTO reqDTO) throws Exception {
        // 관리자 권한을 부여하거나 박탈할 유저
        UserProfile userProfile = this.fetchUserProfileById(reqDTO.getId());
        // 권한을 제어하려는 유저 (슈퍼유저 - ROOT_ADMIN 만 허용)
        UserProfile superUserProfile = this.fetchCurrentUser(auth);
        if (!superUserProfile.getPermissions().contains(Permission.ROOT_ADMIN)) {
            throw new Exception(
                    userMsgSrc.getMessage("error.notEnoughPermission", null, Locale.ENGLISH)
            );
        }

        // 슈퍼유저 - ROOT_ADMIN 은 권한을 빅틸하거나 변경할 수 없음 (루트 계정)
        if (userProfile.getPermissions().contains(Permission.ROOT_ADMIN)) {
            throw new Exception(
                    userMsgSrc.getMessage("error.rootAdmin", null, Locale.ENGLISH)
            );
        }

        List<Permission> perms = userProfile.getPermissions();
        perms.clear();

        perms.add(reqDTO.getNewPermission());

        userProfile.setPermissions(perms);
        userProfileRepo.save(userProfile);
    }

    @Transactional
    public void deleteUserProfile(Authentication auth) {
        UserProfile userProfile = this.fetchCurrentUser(auth);
        userProfileRepo.delete(userProfile);
    }
}
