# Hello World Spring Boot - Tomcat Deployment

Spring Boot 3.x WAR project for external Tomcat 10.1 and Java 21.

## Build
```bash
mvn clean package
```
Expected artifact: `target/hello-world.war`

## Endpoints after deployment
- `/hello-world/` returns the Hello World message
- `/hello-world/health` returns `UP`

Example: `http://YOUR_TOMCAT_PUBLIC_IP:8080/hello-world/`

## Jenkins prerequisites
- JDK tool name `Java21`
- Maven tool name `Maven3`
- SSH Agent plugin
- Jenkins SSH credential ID `tomcat-ssh-key`, with the matching public key in Tomcat EC2 user's `~/.ssh/authorized_keys`
- Jenkins can reach Tomcat EC2 private IP on port 22
- Scoped sudo permission for installing the WAR into `/var/lib/tomcat10/webapps`

Configure a GitHub webhook to `https://YOUR_JENKINS_URL/github-webhook/` and enable the GitHub hook trigger in the Jenkins job. Confirm `APP_SERVER` matches your Tomcat EC2 private IP before running.

Keep Jenkins and Tomcat Manager access restricted; use a strong unique password.
