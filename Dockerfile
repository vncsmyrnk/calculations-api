# Stage 1: Base image
FROM eclipse-temurin:20-jdk-alpine as base
RUN apk add bash
SHELL ["/bin/bash", "-c"]
WORKDIR /var/app/
RUN <<EOF
apk add zip curl
curl -s "https://get.sdkman.io" | bash
source /root/.sdkman/bin/sdkman-init.sh 
sdk install gradle 8.5
sdk install springboot
EOF
COPY . .

# Stage 2: Build stage
FROM gradle:8.5.0-jdk20-alpine as build
WORKDIR /var/app/
COPY --from=base /var/app .
RUN ./gradlew bootJar

# Stage 3: Optimized deploy-ready image
LABEL org.opencontainers.image.source=https://github.com/clocked-app/calculations-api
FROM eclipse-temurin:20-jdk-alpine
ARG WTC_VERSION=v0.0.0
ENV WTC_VERSION=${WTC_VERSION}
WORKDIR /var/app/
COPY --from=build /var/app/build/libs/app.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

