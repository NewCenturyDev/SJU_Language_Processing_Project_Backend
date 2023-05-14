package com.sju.sju_language_processing.commons.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
/* 로그인 하지 않은 사용자의 요청을 차단 및 처리하는 클래스 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    // 토큰을 제공하지 않고 요청하는 경우
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        PrintWriter rawResponseWriter = response.getWriter();
        SecurityResponseManager securityResponseManager = new SecurityResponseManager();
        securityResponseManager.setResponseHeader(response);
        if (request.getRequestURL().toString().contains("admin")) {
            request.setAttribute("msg", "로그인 되지 않은 사용자이거나, 어드민 권한이 없거나, 로그인 토큰이 만료되었습니다.");
            request.setAttribute("nextPage", "/admin");
            request.getRequestDispatcher("/error/denied").forward(request, response);
        } else {
            rawResponseWriter.write(securityResponseManager.makeResponseBody(e));
            rawResponseWriter.flush();
            rawResponseWriter.close();
        }
    }
}
