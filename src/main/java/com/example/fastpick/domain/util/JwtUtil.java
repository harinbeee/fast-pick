package com.example.fastpick.domain.util;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	private Key key;

	private final long TOKEN_TIME = 60 * 60 * 1000L;

	@PostConstruct
	public void init(){
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(bytes);
	}

	public String createToken(String mail, String role) {
		Date date = new Date();

		return Jwts.builder()
			.setSubject(mail)
			.claim("role",role)
			.setExpiration(new Date(date.getTime() + TOKEN_TIME))
			.setIssuedAt(date)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String substringToken (String tokenValue) {
		if( tokenValue != null && tokenValue.startsWith("Bearer ")) {
			return tokenValue.substring(7);
		}
		throw new NullPointerException("토큰이 없거나 Bearer로 시작하지 않습니다.");
	}

	public boolean validateToken(String token) {
		try{
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			log.error("유효하지 않은 JWT 토큰입니다.");
			return false;
		}
	}

	public Claims getUserInfoFromToken (String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
