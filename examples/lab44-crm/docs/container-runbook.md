# Lab 41 — Container runbook

Work in **`java-bootcamp/examples/lab41-crm`** (not the course clone).

## Build

```bash
docker build --pull -t crm-api:lab41 .
docker image inspect crm-api:lab41 --format "{{.Id}} {{.Size}} {{json .Config.User}}"
docker image inspect crm-api:lab41 --format "{{json .RepoDigests}}"
```

## Run

```bash
# PowerShell (Windows)
cd $env:USERPROFILE\java-bootcamp\examples\lab37-crm
docker compose --project-name lab37-crm up -d

cd $env:USERPROFILE\java-bootcamp\examples\lab41-crm
Copy-Item .env.example .env.local -Force
# set CRM_DB_PASSWORD=CrmLab_Strong1 in .env.local before running (matches the Lab 37 Postgres instance in this repo)

docker run -d --name crm-lab41 --network lab37-crm_default \
  --memory=512m --env-file .env.local -p 8080:8080 crm-api:lab41
```

## Verify

- Readiness: `curl.exe http://localhost:8080/actuator/health/readiness` (Windows) or
  `curl http://localhost:8080/actuator/health/readiness`
- CRM smoke: `GET /api/customers?status=ACTIVE` with `X-Correlation-Id: lab-request-001` (Lab 40 has no
  `/api/v1/interactions`)
- User inside container: `docker exec crm-lab41 id` → expect UID 10001

### Output

```bash
PS C:\Users\austi\java-bootcamp\examples\lab41-crm> curl.exe http://127.0.0.1:8080/actuator/health/readiness
{"status":"UP"}
PS C:\Users\austi\java-bootcamp\examples\lab41-crm> curl.exe http://127.0.0.1:8080/actuator/health
{"status":"UP","groups":["liveness","readiness"]}
PS C:\Users\austi\java-bootcamp\examples\lab41-crm> curl.exe -H "X-Correlation-Id: lab-request-001" "http://127.0.0.1:8080/api/customers?status=ACTIVE"
{"content":[],"pageable":{"pageNumber":0,"pageSize":20,"sort":{"sorted":true,"empty":false,"unsorted":false},"offset":0,"paged":true,"unpaged":false},"last":true,"totalPages":0,"totalElements":0,"first":true,"size":20,"number":0,"sort":{"sorted":true,"empty":false,"unsorted":false},"numberOfElements":0,"empty":true}
PS C:\Users\austi\java-bootcamp\examples\lab41-crm> docker exec crm-lab41 id
uid=10001(spring) gid=10001(spring) groups=10001(spring)
PS C:\Users\austi\java-bootcamp\examples\lab41-crm> docker logs crm-lab41 --tail 100
Picked up JAVA_TOOL_OPTIONS: -XX:MaxRAMPercentage=75.0

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.3.5)

2026-08-21T03:20:51.152Z  INFO 1 --- [lab39-crm] [           main] com.northstar.crm.CrmApplication         : Starting CrmApplication v0.0.1-SNAPSHOT using Java 21.0.11 with PID 1 (/app/app.jar started by spring in /app)
2026-08-21T03:20:51.155Z  INFO 1 --- [lab39-crm] [           main] com.northstar.crm.CrmApplication         : The following 1 profile is active: "docker"
2026-08-21T03:20:52.055Z  INFO 1 --- [lab39-crm] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2026-08-21T03:20:52.094Z  INFO 1 --- [lab39-crm] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 34 ms. Found 2 JPA repository interfaces.
2026-08-21T03:20:52.780Z  INFO 1 --- [lab39-crm] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2026-08-21T03:20:52.795Z  INFO 1 --- [lab39-crm] [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2026-08-21T03:20:52.795Z  INFO 1 --- [lab39-crm] [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.31]
2026-08-21T03:20:52.821Z  INFO 1 --- [lab39-crm] [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2026-08-21T03:20:52.822Z  INFO 1 --- [lab39-crm] [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1610 ms
2026-08-21T03:20:53.196Z  INFO 1 --- [lab39-crm] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2026-08-21T03:20:53.356Z  INFO 1 --- [lab39-crm] [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection@ff1f465
2026-08-21T03:20:53.358Z  INFO 1 --- [lab39-crm] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2026-08-21T03:20:53.386Z  INFO 1 --- [lab39-crm] [           main] org.flywaydb.core.FlywayExecutor         : Database: jdbc:postgresql://crm-postgres:5432/crm_lab41 (PostgreSQL 16.15)
2026-08-21T03:20:53.437Z  INFO 1 --- [lab39-crm] [           main] o.f.core.internal.command.DbValidate     : Successfully validated 1 migration (execution time 00:00.030s)
2026-08-21T03:20:53.470Z  INFO 1 --- [lab39-crm] [           main] o.f.core.internal.command.DbMigrate      : Current version of schema "public": 1
2026-08-21T03:20:53.473Z  INFO 1 --- [lab39-crm] [           main] o.f.core.internal.command.DbMigrate      : Schema "public" is up to date. No migration necessary.
2026-08-21T03:20:53.572Z  INFO 1 --- [lab39-crm] [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2026-08-21T03:20:53.622Z  INFO 1 --- [lab39-crm] [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.5.3.Final
2026-08-21T03:20:53.645Z  INFO 1 --- [lab39-crm] [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2026-08-21T03:20:53.859Z  INFO 1 --- [lab39-crm] [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2026-08-21T03:20:54.682Z  INFO 1 --- [lab39-crm] [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2026-08-21T03:20:54.705Z  INFO 1 --- [lab39-crm] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2026-08-21T03:20:55.075Z  WARN 1 --- [lab39-crm] [           main] .s.s.UserDetailsServiceAutoConfiguration : 

Using generated security password: REMOVED FOR SECURITY. FOUND IN .ENV AS GENERATED_SECURITY_PASSWORD.

This generated password is for development use only. Your security configuration must be updated before running your application in production.

2026-08-21T03:20:55.086Z  INFO 1 --- [lab39-crm] [           main] r$InitializeUserDetailsManagerConfigurer : Global AuthenticationManager configured with UserDetailsService bean with name inMemoryUserDetailsManager
2026-08-21T03:20:55.748Z  INFO 1 --- [lab39-crm] [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 2 endpoints beneath base path '/actuator'
2026-08-21T03:20:55.841Z  INFO 1 --- [lab39-crm] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2026-08-21T03:20:55.851Z  INFO 1 --- [lab39-crm] [           main] com.northstar.crm.CrmApplication         : Started CrmApplication in 5.049 seconds (process running for 5.416)
2026-08-21T03:21:00.608Z  INFO 1 --- [lab39-crm] [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2026-08-21T03:21:00.609Z  INFO 1 --- [lab39-crm] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2026-08-21T03:21:00.610Z  INFO 1 --- [lab39-crm] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
2026-08-21T03:21:51.842Z  WARN 1 --- [lab39-crm] [nio-8080-exec-5] ration$PageModule$WarningLoggingModifier : Serializing PageImpl instances as-is is not supported, meaning that there is no guarantee about the stability of the resulting JSON structure!
        For a stable JSON structure, please use Spring Data's PagedModel (globally via @EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO))
        or Spring HATEOAS and Spring Data's PagedResourcesAssembler as documented in https://docs.spring.io/spring-data/commons/reference/repositories/core-extensions.html#core.web.pageables.

```

### Verified Step 6 runtime settings

```bash
PS C:\Users\austi\java-bootcamp\examples\lab41-crm> docker inspect crm-lab41 --format "NetworkMode={{.HostConfig.NetworkMode}} Memory={{.HostConfig.Memory}}"
NetworkMode=lab37-crm_default Memory=536870912

PS C:\Users\austi\java-bootcamp\examples\lab41-crm> docker ps --filter "name=crm-lab41" --format "{{.Names}}|{{.Image}}|{{.Status}}|{{.Ports}}"
crm-lab41|crm-api:lab41|Up (healthy)|0.0.0.0:8080->8080/tcp, [::]:8080->8080/tcp
```

## Stop / graceful shutdown

```bash
docker stop --time 20 crm-lab41
```

## Registry and evidence pack

```bash
docker image inspect crm-api:lab41 --format "{{.Id}} {{.Size}} {{json .Config.User}}"
docker image inspect crm-api:lab41 --format "{{json .RepoDigests}}"
GIT_SHA=$(git rev-parse --short HEAD)
docker tag crm-api:lab41 "crm-api:1.0.0-$GIT_SHA"

git status --short
git remote -v
```

- Image ID: `sha256:3fb68fe73557d52f319d5522aaa5c4f2907ebe3d88354b47f284395011317eb8`
- Image size: `163879186` bytes
- Config.User: `"10001"`
- Digest: `crm-api@sha256:3fb68fe73557d52f319d5522aaa5c4f2907ebe3d88354b47f284395011317eb8`
- Tag for Lab 42: `crm-api:1.0.0-92c3c3b`
- Repo remote: `https://github.com/austincharlz/java-bootcamp.git`
- No `.env.local` or registry credentials were committed; `.env.local` stayed local and gitignored
- Credentials stay in local Docker auth/config only; never in Git or `.env*` files

## JDBC/container runtime facts

- DB host used from container: `crm-postgres`
- Network: `lab37-crm_default`
- Smoke command:

```bash
curl.exe -H "X-Correlation-Id: lab-request-001" "http://127.0.0.1:8080/api/customers?status=ACTIVE"
```

## Peer validation from runbook only

```powershell
cd $env:USERPROFILE\java-bootcamp\examples\lab41-crm

docker build --pull -t crm-api:lab41 .
docker rm -f crm-lab41 2>$null
docker run -d --name crm-lab41 --network lab37-crm_default --memory=512m --env-file .env.local -p 8080:8080 crm-api:lab41

for ($i = 1; $i -le 30; $i++) {
  $status = curl.exe -s -o NUL -w "%{http_code}" http://127.0.0.1:8080/actuator/health/readiness
  if ($status -eq "200") { break }
  Start-Sleep -Seconds 2
}

curl.exe -H "X-Correlation-Id: lab-request-001" "http://127.0.0.1:8080/api/customers?status=ACTIVE"
docker exec crm-lab41 id
docker stop --time 20 crm-lab41
```

Expected result: a fresh build, startup on `crm-postgres`, readiness `200`, API smoke `200`, `uid=10001(spring)`, and
then a graceful stop.