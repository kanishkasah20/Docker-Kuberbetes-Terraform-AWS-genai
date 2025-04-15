## Deploying SpringBoot Application on AWS EKS(Elastic Kubernetes Service)

##### Pre-requirements
- AWS Account
- AWS CLI and kubectl install, setup
- Docker image of your application(check running status in docker desktop)

##### Steps:
1) Create a spring boot application
2) Create Docker image
3) Push Docker image to Elastic container registry
   - ECR(Elastic container registry) is a service provided by aws to store our docker images
   - Go on aws management console, search ecr , click on create private repository, give name "springboot-eks" then create repository.  [enter image link]
   - To push docker image to ecr (in upper side of repo page we can see a block having Push commands) 
   -

Image uri : 337909785951.dkr.ecr.us-east-1.amazonaws.com/springboot-eks:latest

4) Pull image from ECR and deploy into EKS
Amazon EKS is a managed service that makes it easy for you to use Kubernetes on AWS without needing to install and operate your own Kubernetes infrastructure.
- search eks and create a new cluster (you can create cluster from cli(kubectl) or by console ui)
cli command ["eksctl create cluster --name DemoEKS-cluster --version 1.32 --nodes=1 --node-type=t2.small --region us-east-1"]
- it will take 10-12 min to create a cluster 

- command to delete the cluster if you want to to cleanup resources, run ['eksctl delete cluster --region=us-east-1 --name=eksDemo-cluster']

- next we need to update our local kubeconfig file so that from our local terminal it can get connected to this particular cluster
- go to terminal and just update our kubeconfig file to our created cluster, so that it will load all required configuration required from local to connect to our eks
cli command ["aws eks --region us-east-1 update-kubeconfig --name DemoEKS-cluster"]
- since our cluster will be up and running ..now we can deploy our application to kubernetes clusters i.e EKS
- To deploy any application to kubernetes we need to thing: deployment object and service object and these two objects we create using .yaml configuration (under application folder we specify the configuration .yaml file  to tell kubernetes what image we want to deploy to cluster & what kind of service we want to expose)
- making kubernetes yaml file named as "k8s_aws_EKS.yaml" 
  - in yaml file we need to give image uri to specify run this image in eks cluster
  - so kubernetes will pull the specified image from ecr and run it on the eks cluster

- after checking external ip from "kubectl get svc" command , check the running springboot application on that ip with different defined api request like this 'http://a678c2fdbdf6347a89afe6c2d57d4856-731415783.us-east-1.elb.amazonaws.com/category/List'

-  then check the ec2 instance created, load balancers , eks cluster deployed application and stored in ec2 

- Dont forget to delete the eks cluster after practicing for(learning) so delete it using this command
'eksctl delete cluster DemoEKS-cluster'