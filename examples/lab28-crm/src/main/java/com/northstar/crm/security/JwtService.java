package com.northstar.crm.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String secret;

    public JwtService(@Value("${northstar.security.jwt-secret}") String secret) {
        this.secret = secret;
    }

    public String issueToken(String subject, String role) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Missing subject");
        }
        if (!"AGENT".equals(role) && !"ADMIN".equals(role)) {
            throw new IllegalArgumentException("Invalid role");
        }
        return "lab." + subject + "." + role + "." + signature();
    }

    public String parseSubject(String token) {
        return parseParts(token)[1];
    }

    public String parseRole(String token) {
        return parseParts(token)[2];
    }

    private String[] parseParts(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Invalid token");
        }
        String[] parts = token.split("\\.");
        if (parts.length != 4 || !"lab".equals(parts[0])) {
            throw new IllegalArgumentException("Invalid token");
        }
        if (!signature().equals(parts[3])) {
            throw new IllegalArgumentException("Invalid token signature");
        }
        if (parts[1].isBlank()) {
            throw new IllegalArgumentException("Invalid subject");
        }
        if (!"AGENT".equals(parts[2]) && !"ADMIN".equals(parts[2])) {
            throw new IllegalArgumentException("Invalid role");
        }
        return parts;
    }

    private String signature() {
        return Integer.toHexString(secret.hashCode());
    }
}