package com.terratech.api.util;


import com.terratech.api.model.Abstract.Person;
import io.jsonwebtoken.Jwts;

import java.util.Map;

public class JwtUtil {
    private static final String ISSUER = "terratech.contato@gmail.com";

    public static String generateToken(
            Person person,
            Map<String, Object> claims
    ) {
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(person.getEmail())
                .addClaims(claims)
                .compact();
    }

    public static String extractUsername(String token) {
        return token.toLowerCase();
    }
}
