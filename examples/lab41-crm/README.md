# Lab 41 — Containerized CRM

This lab packages the Lab 40 CRM as a multi-stage Docker image with hardened runtime settings, non-root execution, and
readiness checks.

## Commands used

```powershell
cd $env:USERPROFILE\java-bootcamp\examples\lab41-crm

docker build --pull -t crm-api:lab41 .
docker image inspect crm-api:lab41 --format "{{.Id}} {{.Size}} {{json .Config.User}}"

docker compose --project-name lab37-crm up -d

docker run -d --name crm-lab41 --network lab37-crm_default --memory=512m --env-file .env.local -p 8080:8080 crm-api:lab41

curl.exe http://127.0.0.1:8080/actuator/health/readiness
curl.exe -H "X-Correlation-Id: lab-request-001" "http://127.0.0.1:8080/api/customers?status=ACTIVE"
docker exec crm-lab41 id

docker stop --time 20 crm-lab41
```

## Evidence summary

- Readiness: `{"status":"UP"}`
- CRM smoke: HTTP 200 from `/api/customers?status=ACTIVE`
- User inside container: `uid=10001(spring) gid=10001(spring)`
- Failure experiment: invalid database host (`CRM_DB_HOST=no-such-host`) caused a Flyway/JDBC connection failure and a
  clean exit, proving the app fails clearly when dependencies are misconfigured

## Reflection Questions

1. Using a multi-stage Docker build with a separate runtime JRE image. That reduced image size and kept the final image
   lean by excluding Maven, source code, and build tools from the runtime layer. It also improved image safety by
   avoiding a larger root build environment and by letting us keep runtime execution strictly non-root.
2.
    - Non-root: docker exec crm-lab41 id returned uid=10001 (spring) gid=10001 (spring)
    - Readiness: curl.exe http://127.0.0.1:8080/actuator/health/readiness returned {"status":"UP"}
3. The hardest was the database/network issue, not file permissions. The app initially failed with The connection
   attempt failed and UnknownHostException: crm-postgres, which pointed to container DNS/network configuration rather
   than a health or permission problem. The fix was ensuring the app container was on lab37-crm_default and that
   .env.local used the correct DB host/password (crm-postgres / CrmLab_Strong1).