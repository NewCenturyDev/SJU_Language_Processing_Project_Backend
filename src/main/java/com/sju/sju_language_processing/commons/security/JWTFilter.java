package com.sju.sju_language_processing.commons.security;

import com.sju.sju_language_processing.domains.users.auth.services.UserAuthServ;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
/* JWT 토큰 필터를 구현하는 클래스 */
public class JWTFilter extends OncePerRequestFilter {
    private final JWTTokenProvider jwtTokenProvider;

    private final UserAuthServ userAuthServ;


    // 의존성 주입용 생성자
    public JWTFilter(UserAuthServ userAuthServ, JWTTokenProvider jwtTokenProvider) {
        this.userAuthServ = userAuthServ;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // request 헤더에서 Authorization 필드를 검출
        final String requestTokenHeader = request.getHeader("Authorization");

        String email = null;
        String jwtToken = null;
        Cookie authCookie = null;

        if (request.getCookies() != null) {
            authCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("Authorization")).findFirst().orElse(null);
        }

        if (requestTokenHeader != null) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                email = jwtTokenProvider.getEmailFromToken(jwtToken);
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        } else if (authCookie != null) {
            jwtToken = authCookie.getValue();
            try {
                email = jwtTokenProvider.getEmailFromToken(jwtToken);
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        } else {
            logger.warn("User requested Ajax but Authorization Header is null");
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userAuthServ.loadUserByUsername(email);
                if (!userDetails.isAccountNonLocked()) {
                    throw new LockedException("Account not verified");
                }
                if (jwtTokenProvider.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (UsernameNotFoundException e) {
                logger.warn("Login failed: User Not Found");
            } catch (LockedException e) {
                logger.warn("Login failed: User Not Verified");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    // 검증하지 않을 예외 조건
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return JWTFilterRules.getExcludesByRequest(request);
    }
}
