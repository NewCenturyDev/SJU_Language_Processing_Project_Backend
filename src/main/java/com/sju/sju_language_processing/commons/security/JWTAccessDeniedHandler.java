package com.sju.sju_language_processing.commons.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
/* 로그인한 유저가 권한이 없는 서비스에 접근시 차단 및 처리하는 클래스 */
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

    // 필요한 권한이 없이 접근하려 할 때
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        PrintWriter rawResponseWriter = response.getWriter();
        SecurityResponseManager securityResponseManager = new SecurityResponseManager();
        securityResponseManager.setResponseHeader(response);
        if (request.getRequestURL().toString().contains("admin")) {
            request.setAttribute("msg", "해당 리소스에 대한 어드민 권한이 없거나, 로그인 토큰이 만료되었습니다.");
            request.setAttribute("nextPage", "/admin");
            request.getRequestDispatcher("/error/denied").forward(request, response);
        } else {
            rawResponseWriter.write(securityResponseManager.makeResponseBody(e));
            rawResponseWriter.flush();
            rawResponseWriter.close();
        }
    }
}
