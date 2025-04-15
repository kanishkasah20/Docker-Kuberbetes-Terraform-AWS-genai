## This Notes will walk me through steps to deploy my springboot application to kubernetes using yaml configuration

- create a docker image of our application as demo:01 version
- started minikube in cmd and access the miniqube dashboard

- how to create a deployment YAML file
-after creating kubernetes deplyment yaml file, then run this command for deployment by going inside your project folder of microservice application in java
*kubectl apply -f k8s_deployment.yaml*
-check the deployed resource in the cluster and runing pods in replicaSets (check the running state of pods)
*kubectl get deployments*
*kubectl get pod*
-to check why pods are not working or running use describe and logs command
*kubectl describe pod {pod_name}*
*kubectl logs {pod_name}*
-if you are facing error: ImagePullBackOff then Load the Image into MiniKube
(If you're using Minikube & have built the image locally, you need to load it into Minikube's Docker daemon bye using this command):
*minikube image load demo:01*

- how to create service YAML
-first create the service yaml file then run this command in cmd or terminal of you project folder
*kubectl apply -f k8s_service.yaml(service yaml file name)*
-check the created service
*kubectl get service*
-check the nodeport (beacuse we have used service type NodePort so we are accessing are application on node ip)
*kubectl get nodes -o wide*

--for checking internal ip address of nodeport
[PS C:\Users\Administrator\Desktop\practiceMicroservices\demo> kubectl get nodes -o wide
NAME       STATUS   ROLES           AGE   VERSION   INTERNAL-IP    EXTERNAL-IP   OS-IMAGE             KERNEL-VERSION                       CONTAINER-RUNTIME
minikube   Ready    control-plane   20h   v1.32.0   192.168.49.2   <none>        Ubuntu 22.04.5 LTS   5.15.167.4-microsoft-standard-WSL2   docker://27.4.1]
--for checking port number here we have i.e 30996
[PS C:\Users\Administrator\Desktop\practiceMicroservices\demo> kubectl get service
NAME                     TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)          AGE
kubernetes               ClusterIP   10.96.0.1    <none>        443/TCP          21h
springboot-k8s-service   NodePort    10.99.3.20   <none>        9090:30996/TCP   23m]


since we have exposed our service as a nodeport, so we need {nodeIp + nodeport} to access the api

*http://{nodeIp}:{PORT}/{API URL}*
i.e  *http://192.168.49.2:30996/category/List*

-> minikube ip
-> get nodes -o wide  (this command will give INTERNAL-IP)
-----Both the ip are same , we can use anyone



-for deleting any service from kubernete
*kubectl delete service spring-boot-k8s(service name)*

- How to deploy and monitor health of k8s component using k8s dashboard
Example:
[C:\Users\Administrator\.kube>minikube dashboard
* Verifying dashboard health ...
* Launching proxy ...
* Verifying proxy health ...
* Opening http://127.0.0.1:61178/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/ in your default browser...
]