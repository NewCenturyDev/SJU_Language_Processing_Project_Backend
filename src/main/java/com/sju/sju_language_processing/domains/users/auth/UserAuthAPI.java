package com.sju.sju_language_processing.domains.users.auth;

import com.sju.sju_language_processing.commons.http.APIUtil;
import com.sju.sju_language_processing.commons.security.JWTTokenProvider;
import com.sju.sju_language_processing.domains.users.auth.dto.request.LoginReqDTO;
import com.sju.sju_language_processing.domains.users.auth.dto.response.LoginResDTO;
import com.sju.sju_language_processing.domains.users.auth.services.UserAuthServ;
import com.sju.sju_language_processing.domains.users.profile.entity.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthAPI {
    private final UserAuthServ userAuthServ;
    private final JWTTokenProvider tokenProvider;

    public UserAuthAPI(UserAuthServ userAuthServ, JWTTokenProvider tokenProvider) {
        this.userAuthServ = userAuthServ;
        this.tokenProvider = tokenProvider;
    }

    // 이메일로 로그인
    @PostMapping("/users/auths/login")
    public ResponseEntity<?> loginAccount(@RequestBody LoginReqDTO reqDto) {
        LoginResDTO resDTO = new LoginResDTO();
        return new APIUtil<LoginResDTO>() {
            @Override
            protected void onSuccess() {
                UserProfile profile = userAuthServ.loginByCredential(reqDto.getEmail(), reqDto.getPassword());
                resDTO.setProfile(profile);
                resDTO.setToken(tokenProvider.createToken(profile.getEmail(), profile.getPermissions()));
            }
        }.execute(resDTO, "res.login.success");
    }
}
