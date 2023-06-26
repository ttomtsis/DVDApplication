# This file deletes all the resources created by the setup script
# provided in the same folder.

import os
import subprocess

# Get number of DB pods created, so that all pv's are deleted
os.system("echo Getting number of database pods created")
command = "kubectl describe sts mysql-clusterling -n default | awk \'/Replicas:/ {print $2}\'"
desired_replicas = subprocess.check_output(command, shell=True)
desired_replicas = desired_replicas.decode().strip()
os.system("echo Number of replicas: " + desired_replicas)
desired_replicas = int(desired_replicas)

# Delete service and deployment of rest server
os.system("kubectl delete service rest-server-service")
os.system("kubectl delete deployment rest-server-deployment")

# Delete MySQL Router service and set
os.system("kubectl delete service mysql-router-service")
os.system("kubectl delete statefulset mysql-router")

# Delete MySQL DB Set and services
os.system("kubectl delete statefulset mysql-clusterling")
os.system("kubectl delete service mysql-service")

# Delete PVC's
command = "kubectl get pvc | awk '/mysql-clusterling/ {print $1}'"
result = subprocess.check_output(command, shell=True)
result = result.decode().strip()
pVolumeClaims = result.split("\n")

for x in range (0, desired_replicas, 1):
    command = "kubectl delete pvc "+pVolumeClaims.__getitem__(x)
    subprocess.run(command, shell=True)

# Delete config map and secret
os.system("kubectl delete configmap dvd-config")
os.system("kubectl delete secret db-secret")

# Delete PV's ( in case minikube did not delete them automatically )
command = "kubectl get pv | awk '/mysql-clusterling/ {print $1}'"
os.system("echo command is: " + command)
result = subprocess.check_output(command, shell=True)
result = result.decode().strip()
pvolumes = result.split("\n")

for x in range (0, desired_replicas, 1):
    command = "kubectl delete pv "+pvolumes.__getitem__(x)
    subprocess.run(command, shell=True)



