package com.sju.sju_language_processing.commons.security;

import com.sju.sju_language_processing.domains.users.profile.entity.UserProfile;
import com.sju.sju_language_processing.domains.users.profile.repository.UserProfileRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JWTAuthenticationProvider implements AuthenticationProvider {
    private UserProfileRepo userProfileRepo;
    private PasswordEncoder pwEncoder;

    @Override
    // 들어오는 모든 요청에 대한 로그인 여부 검증 (WebSecurityConfig 예외 url 제외)
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserProfile profile = userProfileRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        if (pwEncoder.matches(profile.getPassword(), password)) {
            throw new BadCredentialsException("Wrong password");
        }
        if (!profile.isAccountNonLocked()) {
            throw new BadCredentialsException("User not verified");
        }

        return new UsernamePasswordAuthenticationToken(email, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
