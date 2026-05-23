package com.example.fastpick.domain.user.util;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String tokenFromHeader = request.getHeader("Authorization");

		if(StringUtils.hasText(tokenFromHeader)) {
			String token = jwtUtil.substringToken(tokenFromHeader);

			if(jwtUtil.validateToken(token)) {
				Claims info = jwtUtil.getUserInfoFromToken(token);
				String userMail = info.getSubject();
				log.info("인증성공 | 이메일 : {}", userMail);

				SecurityContext context = SecurityContextHolder.createEmptyContext();
				Authentication authentication = new UsernamePasswordAuthenticationToken(userMail, null, null);
				context.setAuthentication(authentication);
				SecurityContextHolder.setContext(context);
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, " 토큰이 유효하지 않습니다. ");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}
