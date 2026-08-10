# Lab 28 — JWT Login TODOs

## Login path + body
POST /api/auth/login {username,password} → {accessToken, tokenType}

## Token response
JwtService issueToken / parseSubject / parseRole (lab stub OK)

## Bearer header form
Client: Authorization: Bearer <accessToken>

## Lab users/roles
Lab users: agent1 (AGENT), admin1 (ADMIN)

## Secret handling
Secret: env JWT_SECRET → northstar.security.jwt-secret (placeholder in .env.example)

## Scope
Pre-lab only. No real secrets.
