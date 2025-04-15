
# 🚀 Deploying Spring Boot Application to Kubernetes (Minikube & AWS EKS)

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
