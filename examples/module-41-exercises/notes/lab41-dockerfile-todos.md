# Lab 41 — Fill Dockerfile TODO Skeleton

## Step 1 — Skeleton

Create `Dockerfile.skeleton` notes:

```dockerfile
FROM eclipse-temurin:21 AS build
WORKDIR /workspace
COPY target/customer-service.jar app.jar
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre AS runtime
USER appuser
COPY --from=build /workspace/app.jar /app/app.jar
HEALTHCHECK CMD curl --fail http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java","-jar","/app/app.jar"]
```

## Step 2 — Fill blanks

Fill JDK/JRE image tags, copy paths, USER, and HEALTHCHECK using course conventions.

## Step 3 — Peer check

Mark any blank you are unsure about for Lab 41 confirmation.

## Step 4 — Security scrub

Confirm no secret ARG/ENV slipped in.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.