# Read Me 
#  Coop Steamer Service

This is a Spring Boot-based microservice that connects to federated social platforms like **Mastodon** and **Pawoo**, streams and aggregates public posts in near real-time, and makes them accessible through reactive REST endpoints.

---

##  Features

-  Streams real-time public posts via **Server-Sent Events (SSE)**
-  Fetches buffered public posts on demand
-  Handles multiple platforms using a **generic federated provider**
-  Reactive and non-blocking with **Spring WebFlux**
-  Secured API keys via `application.properties`

---

##  Project Structure

```text
coop-steamer-service/
├── src/
│   ├── main/
│   │   ├── java/no/coop/task/streamer/
│   │   │   ├── config/                   # Platform properties config
│   │   │   ├── grpc/                     # Placeholder for gRPC (not active)
│   │   │   ├── models/                   # DTOs: PublicPost, AuthorProfile
│   │   │   ├── services/                 # Interfaces & Aggregator service
│   │   │   ├── services/implementations/ # FederatedPlatformPostService
│   │   │   ├── utils/                    # JSON parsing utility
│   │   │   └── CoopSteamerApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── proto/ (future gRPC support)
│   └── test/
│       └── java/no/coop/task/streamer/
│           └── tests for utils and service
└── pom.xml


### How to Run this application

* clone the project from github
* run "mvn clean install"
* Run the server locally "mvn spring-boot:run"
* You can also access the endpoints by CURL "curl -N -H "Accept: text/event-stream" http://localhost:8080/api/v1/posts/stream" and "curl -H "Accept: application/json" http://localhost:8080/api/v1/posts "
* For running inside Docker container:
* * install Docker on your local machine
* * go inside the project folder
* * run command "docker build -t coop-steamer-service ."
* * run "docker network create my-network"
* * run "docker run --network my-network --name coop-steamer-service -p 8080:8080 coop-steamer-service"
* * the service should be running after this

```

#  CI/CD Guide for Coop Steamer Service

This document explains how to set up Continuous Integration (CI) and Continuous Deployment (CD) for the `coop-steamer-service` using **GitHub Actions**, **Docker**, and **Kubernetes**.

---

##  CI/CD Goals

-  Automatically build and test the application on every push
-  Build and publish Docker images
-  Deploy to Kubernetes (e.g., GKE, EKS, or local K3s)
-  Rollback on failure (optional but encouraged)

---

##  Tools that can be used

- **GitHub Actions** – for CI/CD automation
- **Docker** – to package the Spring Boot app
- **Kubernetes** – for cloud-native deployment
- **Helm (optional)** – to manage deployment configs
- **Ingress/Nginx** – for routing traffic
- **Prometheus/Grafana (optional)** – for observability

---

##  CI Pipeline (GitHub Actions)

### `.github/workflows/ci.yml`

```yaml
name: CI - Build and Test

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Set up Java
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17

      - name: Cache Maven packages
        uses: actions/cache@v3
        with:
          path: ~/.m2
          key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
          restore-keys: ${{ runner.os }}-m2

      - name: Build and Test
        run: mvn clean verify
```
##  CD Pipeline (GitHub Actions)
name: CD - Deploy to Kubernetes

on:
push:
tags:
- 'v*.*.*'

jobs:
deploy:
runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v4

      - name: Set up Docker
        uses: docker/setup-buildx-action@v3

      - name: Log in to Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}

      - name: Build and Push Image
        run: |
          docker build -t ${{ secrets.DOCKER_USERNAME }}/coop-steamer-service:${{ github.ref_name }} .
          docker push ${{ secrets.DOCKER_USERNAME }}/coop-steamer-service:${{ github.ref_name }}

      - name: Set up Kubeconfig
        run: |
          echo "${{ secrets.KUBECONFIG }}" > kubeconfig.yaml
        env:
          KUBECONFIG: ${{ secrets.KUBECONFIG }}

      - name: Deploy to Kubernetes
        run: |
          kubectl --kubeconfig=kubeconfig.yaml set image deployment/coop-steamer-service coop-steamer-service=${{ secrets.DOCKER_USERNAME }}/coop-steamer-service:${{ github.ref_name }}
```