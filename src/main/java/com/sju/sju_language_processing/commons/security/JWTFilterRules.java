package com.sju.sju_language_processing.commons.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;

import java.util.Arrays;

public class JWTFilterRules {
    public static final String[] EXCLUDE_RULES = {
            "GET|/storage/**",
            "GET|/static/**",
            "GET|/admin",
            "GET|/error/**",
            "GET|/admin/auths/**",
            "GET|/oauth/**",
            "POST|/users/profiles",
            "POST|/users/profiles/privileged",
            "POST|/users/profiles/activate",
            "POST|/users/profiles/verify/**",
            "POST|/users/auths/**",
            "POST|/users/recovery/**",
            "POST|/oauth/**",
    };

    public static String[] getExcludesByHttpMethod(String method) {
        return Arrays.stream(EXCLUDE_RULES).filter(
                exclude -> {
                    // 구분자가 없는 경우 무조건 해당조건 무효처리
                    if (!exclude.contains("|")) {
                        return false;
                    }
                    // HTTP 메소드와 URL 이 제외 목록과 일치하면 인증 제외
                    String[] excludeEndpoint = exclude.split("\\|");
                    return excludeEndpoint[0].equalsIgnoreCase(method);
                }
        ).map(
                exclude -> (exclude.split("\\|")[1])
        ).toArray(String[]::new);
    }

    public static boolean getExcludesByRequest(@NonNull HttpServletRequest request) {
        return Arrays.stream(EXCLUDE_RULES).anyMatch(
                exclude -> {
                    // 구분자가 없는 경우 무조건 해당조건 무효처리
                    if (!exclude.contains("|")) {
                        return false;
                    }
                    // HTTP 메소드와 URL 이 제외 목록과 일치하면 인증 제외
                    String[] excludeEndpoint = exclude.split("\\|");
                    return excludeEndpoint[0].equalsIgnoreCase(request.getMethod())
                            && excludeEndpoint[1].equalsIgnoreCase(request.getServletPath());
                }
        );
    }
}
