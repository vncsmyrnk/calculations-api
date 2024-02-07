# Calculations API

<img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot" /> <img src="https://img.shields.io/badge/Github%20Actions-282a2e?style=for-the-badge&logo=githubactions&logoColor=367cfe" />
<br>
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=clocked-app_calculations-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=clocked-app_calculations-api)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=clocked-app_calculations-api&metric=coverage)](https://sonarcloud.io/summary/new_code?id=clocked-app_calculations-api)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=clocked-app_calculations-api&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=clocked-app_calculations-api)
<br>
![CI](https://github.com/clocked-app/calculations-api/actions/workflows/ci.yml/badge.svg)
![Release](https://github.com/clocked-app/calculations-api/actions/workflows/release.yml/badge.svg)
<br>
<br>

This is an app built with Spring Boot and Gradle to calculate work time for a given parameter inputs.

Docker is used to handle the environments.

## Run

```bash
docker build --target base --tag wtc-image .
docker run -it --rm -v "$(pwd)":/var/app -p 8080:8080 wtc-image bash
./gradlew bootRun
```

## Development

To use the container as development environment, reuse the container created:

```bash
docker build --target base --tag wtc-image .
docker run -it --name wtc-app -v "$(pwd)":/var/app -p 8080:8080 wtc-image bash

# To run it again
docker start wtc-app

# To access it on terminal
docker exec -it wtc bash
```

To use [nvim](https://neovim.io/) inside the container for development of features, just run `.dev/nvim-setup.sh`. It will install and configure nvim and other dependencies.

This project uses hooks and actions for checking new changes. A minimum code coverage metric is used to verify the changes.

A SonarCloud action is used to check for vulnerabilities and static analysis.

## Deployment

The release automated workflow publishes the docker images on [Docker Hub](https://hub.docker.com/r/clockedwtc/calculations-api/tags) and [GitHub Container Registry](https://github.com/clocked-app/calculations-api/pkgs/container/wtc).

After pulling the images from the most appropriate location, execute one of the following commands to run the image:

```bash
docker run --rm -p {PORT}:8080 ghcr.io/clocked-app/calculations-api:{VERSION} # or
docker run --rm -p {PORT}:8080 clockedwtc/calculations-api:{VERSION}

```
