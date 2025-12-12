package com.urbanride.urbanride.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil{
    @Value("${jwt.secret:change-me-very-secret-key-change-it}")
    private String secret;
    @Value("${jwt.expiry:86400000}")
    private long expiry;
    private Key key(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(String userId){
        return Jwts.builder().setSubject(userId).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+expiry)).signWith(key()).compact();
    }
    public String validateAndGetUserId(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }
}

