package com.sju.sju_language_processing.domains.users.auth.services;

import com.sju.sju_language_processing.commons.message.MessageConfig;
import com.sju.sju_language_processing.domains.users.profile.entity.UserProfile;
import com.sju.sju_language_processing.domains.users.profile.repository.UserProfileRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class UserAuthServ implements UserDetailsService {
    private final UserProfileRepo userProfileRepo;
    private final PasswordEncoder pwEncoder;
    private final MessageSource msgSrc = MessageConfig.getUserMsgSrc();

    @Override
    // 스프링 시큐리티 유저 권한 정보 로드
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userProfileRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        msgSrc.getMessage("error.users.notExist", null, Locale.ENGLISH))
                );
    }

    // 유저네임과 비밀번호로 스프링 시큐리티 인증
    public UserProfile loginByCredential(String email, String password) {
        UserProfile profile = userProfileRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        // 비밀번호 확인
        if(!pwEncoder.matches(password, profile.getPassword())) {
            throw new BadCredentialsException(msgSrc.getMessage("error.login.fail", null, Locale.ENGLISH));
        }

        return profile;
    }
}
