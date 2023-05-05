package com.sju.sju_language_processing.commons.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderProvider {
    //비밀번호 암호화 유틸
    @Bean
    public PasswordEncoder pwEncoder() {
        return new BCryptPasswordEncoder();
    }
}
