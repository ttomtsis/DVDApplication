# This file aims to automatically configure a minikube cluster
# with all the necessary project resources

import os
import subprocess

clusterName = "my-innodb-cluster"
clusterInstances = 3


def checkClusterStatus():
    currentStatus = "Not Ready"

    while currentStatus != str(clusterInstances):
        command = 'kubectl get innodbcluster ' + clusterName + ' | awk "/1/ {print $3}"'
        currentStatus = subprocess.check_output(command, shell=True)
        currentStatus = currentStatus.decode().strip()
    os.system("echo All Pods Ready")


os.system("echo Initializing...")

# 1) Create config map and secret
os.system("echo Creating config map and secret")
os.system("kubectl apply -f ./configs/dvd-conf.yaml")
os.system("kubectl apply -f ./database/db-secret.yaml")

# 2) Create pv and pvc that will be used for innodb cluster's backups
os.system("echo Creating InnoDB cluster backup volume and claim")
os.system("kubectl apply -f ./database/innodb-backup.yaml")

# 3) Create InnoDB Cluster
os.system("echo Creating InnoDB cluster")
os.system("kubectl apply -f ./database/innodb-cluster.yaml")

# 4) Initialize cluster through the init pod
os.system("echo Initializing InnoDB cluster")
os.system("kubectl apply -f ./database/init-cluster.yaml")

# 5) Wait for Cluster to be Ready before proceeding
os.system("echo Waiting for InnoDB cluster to be ready")
checkClusterStatus()

# 6) Apply REST server service
os.system("echo Creating Rest Server Service")
os.system("kubectl apply -f ./rest-server/rest-server-service.yaml")

# 7) Apply REST server deployment
os.system("echo Creating Rest Server Deployment")
os.system("kubectl apply -f ./rest-server/rest-server-deployment.yaml")

# 8) Expose rest server outside of cluster through minikube
# NOTE: Terminal window must stay open
os.system("echo Exposing Rest Server Service")
os.system("minikube service rest-server-service")
