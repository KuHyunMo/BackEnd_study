package com.example.cnubackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.expire-time:3600000}") // 기본 1시간
    private long expireTime;

    private SecretKey key;

    @PostConstruct
    public void init() {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedKey));
        System.out.println("JWT Secret Key: " + encodedKey);
    }

    // JWT 생성 (유저 ID를 subject로 저장)
    public String generateToken(Long userId) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expireTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰에서 유저 ID 추출
    public Long getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Authentication getAuthentication(String token) {
        String userId = getClaims(token).getSubject(); // 토큰 subject = 유저 ID

        List<GrantedAuthority> authorities = List.of();

        org.springframework.security.core.userdetails.User principal = new User(userId, "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}