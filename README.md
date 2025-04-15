# 1] Spring Boot Application - Docker Setup Guide

This guide walks you through the steps to install Docker, build a Docker image of your Spring Boot application, and run it in a container.

---

## 🐳 Prerequisites

Make sure you have the following installed:

- Java 17 (or compatible version with your Spring Boot app)
- Maven or Gradle
- Docker

---

## 🚀 Install Docker

### Windows / macOS:

1. Visit the official Docker website:  
   👉 https://www.docker.com/products/docker-desktop

2. Download and install Docker Desktop.

3. After installation, open Docker Desktop and ensure it's running.

### Ubuntu/Linux:

```bash
sudo apt-get update
sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

sudo mkdir -p /etc/apt/keyrings

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io
```

> Test Docker installation:  
```bash
docker --version
```

---

## 📦 Build the Spring Boot Application

Navigate to your project directory and build the JAR file.

### Using Maven:

```bash
./mvnw clean package
```

### Using Gradle:

```bash
./gradlew build
```

Ensure the `.jar` file is created in the `target/` or `build/libs/` directory.

---

## 🐳 Create Dockerfile

Create a file named `Dockerfile` in the root of your project and add the following:

```Dockerfile
# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/your-app-name.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

> Replace `your-app-name.jar` with the actual JAR file name.

---

## 🛠️ Build Docker Image

From the project root directory (where the Dockerfile is):

```bash
docker build -t springboot-app .
```

---

## ▶️ Run Docker Container

```bash
docker run -p 8080:8080 springboot-app
```

Now your Spring Boot app should be running at:  
👉 http://localhost:8080

---

## 📂 .dockerignore (Optional)

Create a `.dockerignore` file to avoid copying unnecessary files:

```bash
target/
*.git
*.md
*.iml
*.idea
```

---

## ✅ Verify

Check running containers:

```bash
docker ps
```

To stop the container:

```bash
docker stop <container_id>
```

---

## 📢 Notes

- For Spring profiles (e.g., dev, prod), you can pass environment variables:
```bash
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev springboot-app
```

- You can also tag the image with a version:
```bash
docker build -t springboot-app:v1 .
```

---

## 📬 Support

For issues or help, raise a ticket or reach out to the maintainer.

---


---

## 🧩 Docker Compose Setup (Optional)

If you want to manage multiple services like a database along with your Spring Boot app, use Docker Compose.

### 📁 Create `docker-compose.yml`

In your project root, create a file named `docker-compose.yml` with the following content:

```yaml
version: '3.8'

services:
  app:
    image: springboot-app
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    depends_on:
      - db

  db:
    image: postgres:15
    environment:
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
      POSTGRES_DB: mydb
    ports:
      - "5432:5432"
```

> ⚠️ Update environment variables and ports as needed.

---

### ▶️ Run Compose

```bash
docker-compose up --build
```

---

### ⏹️ Stop Compose

```bash
docker-compose down
```

---

### 📌 Notes

- You can define volumes and networks for persistent data and isolation.
- Make sure your `application.properties` or `application.yml` matches the DB config from Docker Compose.

Example `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://db:5432/mydb
spring.datasource.username=user
spring.datasource.password=password
```

---



-------------------------------------------------------------------------------------------------------



# 2] 🚀 Deploying Spring Boot Application to Kubernetes (Minikube & AWS EKS)

---

## 🐳 Step 1: Create Docker Image

Create a Docker image of your Spring Boot application and tag it as `demo:01`.

```bash
docker build -t demo:01 .
```

---

## 🧪 Step 2: Run Minikube and Access Dashboard

```bash
minikube start
minikube dashboard
```

---

## 📦 Step 3: Kubernetes Deployment (Minikube)

### 📁 Create Deployment YAML

Create `k8s_deployment.yaml` in your project root.

```bash
kubectl apply -f k8s_deployment.yaml
```

### 📌 Check Deployment

```bash
kubectl get deployments
kubectl get pod
```

### 🧰 Debugging Pods

```bash
kubectl describe pod <pod_name>
kubectl logs <pod_name>
```

### 🛠️ If Error: ImagePullBackOff

Load the image into Minikube:

```bash
minikube image load demo:01
```

---

## 🌐 Step 4: Kubernetes Service

### 📁 Create Service YAML

Create `k8s_service.yaml` and apply:

```bash
kubectl apply -f k8s_service.yaml
```

### 🕵️ Check Services

```bash
kubectl get service
kubectl get nodes -o wide
```

> 🔗 Use Node IP + Node Port:
>
> Example: `http://192.168.49.2:30996/category/List`

---

## 🗑️ Delete K8s Resources

```bash
kubectl delete service <service-name>
```

---

## 📊 Monitor via Dashboard

```bash
minikube dashboard
```

---

## ☁️ AWS EKS Deployment

### 🔧 Prerequisites

- AWS CLI & Kubectl installed
- Docker image built and tested
- AWS account

---

## 🐳 Push Docker Image to AWS ECR

1. Create a private repo on AWS ECR named `springboot-eks`
2. Follow AWS Push Commands for authentication and push

Example URI:

```bash
337909785951.dkr.ecr.us-east-1.amazonaws.com/springboot-eks:latest
```

---

## 🏗️ Create EKS Cluster

```bash
eksctl create cluster --name DemoEKS-cluster --version 1.32 --nodes=1 --node-type=t2.small --region us-east-1
```

> ⏳ Wait ~10 minutes

Update kubeconfig to connect:

```bash
aws eks --region us-east-1 update-kubeconfig --name DemoEKS-cluster
```

---

## 📄 Kubernetes YAML for EKS

Create a file `k8s_aws_EKS.yaml` with Deployment and Service using the ECR image URI.

```bash
kubectl apply -f k8s_aws_EKS.yaml
```

Check service:

```bash
kubectl get svc
```

> Example App URL:  
> `http://<load-balancer-external-ip>/category/List`

---

## 💡 EKS Clean Up

```bash
eksctl delete cluster --region us-east-1 --name DemoEKS-cluster
```

---

✅ You're now ready to deploy your Spring Boot app on both Minikube and AWS EKS with Kubernetes YAMLs!
