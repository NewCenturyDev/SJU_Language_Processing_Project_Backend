package com.sju.sju_language_processing.domains.users.profile;

import com.sju.sju_language_processing.commons.http.APIUtil;
import com.sju.sju_language_processing.commons.message.MessageConfig;
import com.sju.sju_language_processing.domains.users.profile.dto.request.*;
import com.sju.sju_language_processing.domains.users.profile.dto.response.*;
import com.sju.sju_language_processing.domains.users.profile.services.UserProfileCrudServ;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@AllArgsConstructor
public class UserProfileAPI {
    private final UserProfileCrudServ userProfileCrudServ;
    private final MessageSource msgSrc = MessageConfig.getUserMsgSrc();

    // 신규 사용자 계정 생성
    @PostMapping("/users/profiles")
    public ResponseEntity<?> createUserProfile(@Valid @RequestBody CreateUserProfileReqDTO reqDTO) {
        CreateUserProfileResDTO resDTO = new CreateUserProfileResDTO();
        return new APIUtil<CreateUserProfileResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setCreatedUserProfile(userProfileCrudServ.createUser(reqDTO));
            }
        }.execute(resDTO, "res.users.create.success");
    }

    // 사용자 프로파일 조회
    @GetMapping("/users/profiles")
    public ResponseEntity<?> fetchUserProfile(Authentication auth, @Valid FetchUserProfileReqDTO reqDTO, @RequestHeader("Request-Type") String reqType) {
        FetchUserProfileResDTO resDTO = new FetchUserProfileResDTO();
        FetchUserProfileReqOptionType reqOptionType = FetchUserProfileReqOptionType.valueOf(reqType);
        return new APIUtil<FetchUserProfileResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                switch(reqOptionType) {
                    case CURRENT -> resDTO.setUserProfile(userProfileCrudServ.fetchCurrentUser(auth));
                    case EMAIL -> resDTO.setUserProfile(userProfileCrudServ.fetchUserProfileByEmail(reqDTO.getEmail()));
                    case USERNAME -> resDTO.setUserProfile(userProfileCrudServ.fetchUserProfileByUsername(reqDTO.getUsername()));
                    default -> throw new Exception(
                            msgSrc.getMessage("valid.binding", null, Locale.ENGLISH)
                    );
                }
            }
        }.execute(resDTO, "res.users.fetch.success");
    }

    // 사용자 프로파일 업데이트 (관리자/유저 파라미터 다름에 주의)
    @PutMapping("/users/profiles")
    public ResponseEntity<?> updateUserProfile(Authentication auth, @Valid @RequestBody UpdateUserProfileReqDTO reqDTO) {
        UpdateUserProfileResDTO resDTO = new UpdateUserProfileResDTO();
        return new APIUtil<UpdateUserProfileResDTO>() {
            @Override
            protected void onSuccess() {
                resDTO.setUpdatedUserProfile(userProfileCrudServ.updateUserProfile(auth, reqDTO));
            }
        }.execute(resDTO, "res.users.update.success");
    }

    // 사용자 비밀번호 업데이트(변경)
    @PutMapping("/users/profiles/password")
    public ResponseEntity<?> updateUserPassword(Authentication auth, @Valid @RequestBody UpdateUserPasswordReqDTO reqDto) {
        UpdateUserPasswordResDTO resDTO = new UpdateUserPasswordResDTO();
        return new APIUtil<UpdateUserPasswordResDTO>() {
            @Override
            protected void onSuccess() {
                userProfileCrudServ.updateUserPassword(auth, reqDto);
            }
        }.execute(resDTO, "res.password.update.success");
    }

    // 사용자 권한 강제 변경 (루트 관리자만 가능)
    @PreAuthorize("hasAnyAuthority('ROOT_ADMIN')")
    @PutMapping("/users/profiles/permission")
    public ResponseEntity<?> updateUserPermission(Authentication auth, @Valid @RequestBody UpdateUserPermissionReqDTO reqDto) {
        UpdateUserPermissionResDTO resDTO = new UpdateUserPermissionResDTO();
        return new APIUtil<UpdateUserPermissionResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                userProfileCrudServ.updateUserPermission(auth, reqDto);
            }
        }.execute(resDTO, "res.permission.update.success");
    }

    // 회원탈퇴
    @DeleteMapping("/users/profiles")
    public ResponseEntity<?> deleteUserProfile(Authentication auth) {
        DeleteUserProfileResDTO resDTO = new DeleteUserProfileResDTO();
        return new APIUtil<DeleteUserProfileResDTO>() {
            @Override
            protected void onSuccess() {
                userProfileCrudServ.deleteUserProfile(auth);
            }
        }.execute(resDTO, "res.users.delete.success");
    }
}
