#### Output

```bash
[INFO] Analysis Started
[INFO] Finished Archive Analyzer (0 seconds)
[INFO] Finished File Name Analyzer (0 seconds)
[INFO] Finished Jar Analyzer (0 seconds)
[INFO] Finished Dependency Merging Analyzer (0 seconds)
[INFO] Finished Hint Analyzer (0 seconds)
[INFO] Finished Version Filter Analyzer (0 seconds)
[INFO] Created CPE Index (2 seconds)
[INFO] Finished CPE Analyzer (3 seconds)
[INFO] Finished False Positive Analyzer (0 seconds)
[INFO] Finished NVD CVE Analyzer (0 seconds)
[WARNING] Sonatype OSS Index Analyzer disabled due to missing credentials. Authentication with token is now required, and OSS Index is migrating to Sonatype Guide. See https://dependency-check.github.io/DependencyCheck/analyzers/oss-index-analyzer.html for more information on authentication with Sonatype Guide OSS Index.
[INFO] Finished Vulnerability Suppression Analyzer (0 seconds)
[INFO] Finished Known Exploited Vulnerability Analyzer (0 seconds)
[INFO] Finished Dependency Bundling Analyzer (0 seconds)
[INFO] Finished Unused Suppression Rule Analyzer (0 seconds)
[INFO] Analysis Complete (5 seconds)
[INFO] Writing HTML report to: C:\Users\austi\java-bootcamp\examples\lab40-crm\target\dependency-check-report.html
[INFO] Writing JSON report to: C:\Users\austi\java-bootcamp\examples\lab40-crm\target\dependency-check-report.json
[WARNING] 

One or more dependencies were identified with known vulnerabilities in lab40-crm:

jackson-databind-2.17.2.jar (pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2, cpe:2.3:a:fasterxml:jackson-core:2.17.2:*:*:*:*:*:*:*, cpe:2.3:a:fasterxml:jackson-databind:2.17.2:*:*:*:*:*:*:*, cpe:2.3:a:fasterxml:jackson-modules-java8:2.17.2:*:*:*:*:*:*:*) : CVE-2026-54512, CVE-2026-54513, CVE-2026-54514, CVE-2026-54515
log4j-api-2.23.1.jar (pkg:maven/org.apache.logging.log4j/log4j-api@2.23.1, cpe:2.3:a:apache:log4j:2.23.1:*:*:*:*:*:*:*) : CVE-2026-34479, CVE-2026-34477, CVE-2026-49844
postgresql-42.7.4.jar (pkg:maven/org.postgresql/postgresql@42.7.4, cpe:2.3:a:postgresql:postgresql_jdbc_driver:42.7.4:*:*:*:*:*:*:*) : CVE-2026-54291, CVE-2026-42198, CVE-2025-49146
spring-boot-3.3.5.jar (pkg:maven/org.springframework.boot/spring-boot@3.3.5, cpe:2.3:a:vmware:spring_boot:3.3.5:*:*:*:*:*:*:*) : CVE-2026-40974, CVE-2026-22733, CVE-2026-40972, CVE-2026-40975, CVE-2026-40973, CVE-2026-40977
spring-core-6.1.14.jar (pkg:maven/org.springframework/spring-core@6.1.14, cpe:2.3:a:pivotal_software:spring_framework:6.1.14:*:*:*:*:*:*:*, cpe:2.3:a:springsource:spring_framework:6.1.14:*:*:*:*:*:*:*, cpe:2.3:a:vmware:spring_framework:6.1.14:*:*:*:*:*:*:*) : CVE-2026-41855, CVE-2026-41838, CVE-2026-41842, CVE-2026-41848, CVE-2026-41850, CVE-2026-41851, CVE-2026-22740, CVE-2026-41844, CVE-2026-41845, CVE-2026-41846, CVE-2026-22737, CVE-2026-41840, CVE-2026-41841, CVE-2026-41843, CVE-2026-22745, CVE-2026-41852, CVE-2026-41853, CVE-2026-41839, CVE-2026-22741, CVE-2026-22735
spring-security-core-6.3.4.jar (pkg:maven/org.springframework.security/spring-security-core@6.3.4, cpe:2.3:a:pivotal_software:spring_security:6.3.4:*:*:*:*:*:*:*, cpe:2.3:a:vmware:spring_security:6.3.4:*:*:*:*:*:*:*) : CVE-2026-22732, CVE-2026-47838, CVE-2026-40988, CVE-2026-22748, CVE-2026-41706, CVE-2026-41003, CVE-2026-41694, CVE-2026-22746
tomcat-embed-core-10.1.31.jar (pkg:maven/org.apache.tomcat.embed/tomcat-embed-core@10.1.31, cpe:2.3:a:apache:tomcat:10.1.31:*:*:*:*:*:*:*, cpe:2.3:a:apache_tomcat:apache_tomcat:10.1.31:*:*:*:*:*:*:*) : CVE-2024-50379, CVE-2024-56337, CVE-2025-24813, CVE-2025-31651, CVE-2026-41293, CVE-2026-43512, CVE-2025-55754, CVE-2025-66614, CVE-2026-29145, CVE-2026-43515, CVE-2026-53434, CVE-2026-55276, CVE-2026-59083, CVE-2026-59084, CVE-2025-49124, CVE-2025-31650, CVE-2025-48988, CVE-2025-48989, CVE-2025-49125, CVE-2025-52520, CVE-2025-53506, CVE-2025-55752, CVE-2026-24734, CVE-2026-24880, CVE-2026-29146, CVE-2026-34483, CVE-2026-34487, CVE-2026-41284, CVE-2026-43513, CVE-2026-66299, CVE-2025-46701, CVE-2026-42498, CVE-2026-53404, CVE-2026-55957, CVE-2025-55668, CVE-2026-34500, CVE-2026-55955, CVE-2026-55956, CVE-2024-52318, CVE-2026-25854, CVE-2026-50229, CVE-2024-54677, CVE-2025-61795, CVE-2026-24733, CVE-2026-43514


See the dependency-check report for more details.


[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  42:57 min
[INFO] Finished at: 2026-08-20T11:40:25-04:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.owasp:dependency-check-maven:13.0.0:check (default-cli) on project lab40-crm: 
[ERROR] 
[ERROR] One or more dependencies were identified with vulnerabilities that have a CVSS score greater than or equal to '7.0': 
[ERROR] 
[ERROR] jackson-databind-2.17.2.jar (pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2, cpe:2.3:a:fasterxml:jackson-core:2.17.2:*:*:*:*:*:*:*, cpe:2.3:a:fasterxml:jackson-databind:2.17.2:*:*:*:*:*:*:*, cpe:2.3:a:fasterxml:jackson-modules-java8:2.17.2:*:*:*:*:*:*:*): CVE-2026-54512(8.1), CVE-2026-54513(8.1)
[ERROR] log4j-api-2.23.1.jar (pkg:maven/org.apache.logging.log4j/log4j-api@2.23.1, cpe:2.3:a:apache:log4j:2.23.1:*:*:*:*:*:*:*): CVE-2026-34479(6.9)
[ERROR] postgresql-42.7.4.jar (pkg:maven/org.postgresql/postgresql@42.7.4, cpe:2.3:a:postgresql:postgresql_jdbc_driver:42.7.4:*:*:*:*:*:*:*): CVE-2026-42198(7.5), CVE-2026-54291(8.2)
[ERROR] spring-boot-3.3.5.jar (pkg:maven/org.springframework.boot/spring-boot@3.3.5, cpe:2.3:a:vmware:spring_boot:3.3.5:*:*:*:*:*:*:*): CVE-2026-40972(7.5), CVE-2026-40975(7.5), CVE-2026-22733(8.1), CVE-2026-40973(7.0), CVE-2026-40974(9.8)
[ERROR] spring-core-6.1.14.jar (pkg:maven/org.springframework/spring-core@6.1.14, cpe:2.3:a:pivotal_software:spring_framework:6.1.14:*:*:*:*:*:*:*, cpe:2.3:a:springsource:spring_framework:6.1.14:*:*:*:*:*:*:*, cpe:2.3:a:vmware:spring_framework:6.1.14:*:*:*:*:*:*:*): CVE-2026-41838(7.5), CVE-2026-41848(7.5), CVE-2026-41851(7.5), CVE-2026-41850(7.5), CVE-2026-41855(9.8), CVE-2026-41842(7.5)
[ERROR] spring-security-core-6.3.4.jar (pkg:maven/org.springframework.security/spring-security-core@6.3.4, cpe:2.3:a:pivotal_software:spring_security:6.3.4:*:*:*:*:*:*:*, cpe:2.3:a:vmware:spring_security:6.3.4:*:*:*:*:*:*:*): CVE-2026-40988(7.5), CVE-2026-47838(8.1), CVE-2026-22732(9.1)
[ERROR] tomcat-embed-core-10.1.31.jar (pkg:maven/org.apache.tomcat.embed/tomcat-embed-core@10.1.31, cpe:2.3:a:apache:tomcat:10.1.31:*:*:*:*:*:*:*, cpe:2.3:a:apache_tomcat:apache_tomcat:10.1.31:*:*:*:*:*:*:*): CVE-2024-56337(9.8), CVE-2025-52520(7.5), CVE-2025-55754(9.6), CVE-2025-48988(7.5), CVE-2026-41284(7.5), CVE-2026-53404(7.3), CVE-2026-29145(9.1), CVE-2026-29146(7.5), CVE-2026-55276(9.1), CVE-2025-31651(9.8), CVE-2026-43513(7.5), CVE-2026-43512(9.8), CVE-2025-31650(7.5), CVE-2026-43515(9.1), CVE-2025-49124(8.4), CVE-2025-49125(7.5), CVE-2026-24734(7.5), CVE-2026-41293(9.8), CVE-2025-53506(7.5), CVE-2026-66299(7.5), CVE-2026-53434(9.1), CVE-2026-55957(7.3), CVE-2025-66614(9.1), CVE-2026-34487(7.5), CVE-2025-46701(7.3), CVE-2026-34483(7.5), CVE-2025-24813(9.8), CVE-2025-48989(7.5), CVE-2026-24880(7.5), CVE-2025-55752(7.5), CVE-2026-42498(7.3), CVE-2026-59083(9.1), CVE-2024-50379(9.8), CVE-2026-59084(9.1)
[ERROR] 
[ERROR] See the dependency-check report for more details.
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
```