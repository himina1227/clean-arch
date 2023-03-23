package com.refactoring.cleanarchitecture.account.adapter.in.web.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider implements InitializingBean {
    private final String accessSecret;

    @Getter
    private final long accessTokenValidityInMilliseconds;


    private Key accessKey;

    public JwtProvider(
            @Value("${jwt.access-secret}") String accessSecret,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds) {
        this.accessSecret = accessSecret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        log.info("key string = {}", accessSecret);
        this.accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        PayloadDto payloadDto = PayloadDto.of(userDetails.getUsername(), authorities);

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.accessTokenValidityInMilliseconds);

        return createToken(userDetails.getUsername(), payloadDto, validity, this.accessKey);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(accessKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String getUserIdFromJwtToken(String jwt) {
        return Jwts.parserBuilder().setSigningKey(this.accessKey).build().parseClaimsJws(jwt).getBody().getSubject();
    }

    public Claims getClaimsFromJwtToken(String jwt) {
        if (validateToken(this.accessKey, jwt)) return Jwts.parserBuilder().setSigningKey(this.accessKey).build().parseClaimsJws(jwt).getBody();
        return null;
    }

    public boolean validateAccessToken(String token) {
        return validateToken(this.accessKey, token);
    }

    private boolean validateToken(Key key, String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new RuntimeException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT 토큰이 잘못되었습니다.");
        }
    }

    private String createToken(String loginId, PayloadDto payload, Date validity, Key signKey) {
        return Jwts.builder()
                .setClaims(payload.toMap())
                .setSubject(loginId)
                .signWith(signKey, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class PayloadDto{

        private String userId;
        private String role;

        public Map<String, Object> toMap(){
            Map<String, Object> map = new HashMap<>();
            map.put("userId", this.userId);
            map.put("role", this.role);

            return map;
        }

        public static PayloadDto of(String userId, String role){
            return PayloadDto.builder()
                    .userId(userId)
                    .role(role)
                    .build();
        }
    }
}
