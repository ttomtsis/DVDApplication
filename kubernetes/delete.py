# This file deletes all the resources created by the setup script
# provided in the same folder.

import os
import subprocess

clusterName = "my-innodb-cluster"
clusterInstances = 3


def getClusterInstances():
    global clusterInstances

    os.system("echo Getting number of cluster instances")

    # awk \'/Replicas:/ {print $2}\'
    command = 'kubectl get innodbcluster ' + clusterName + ' | awk "/1/ {print $4}"'
    clusterInstances = subprocess.check_output(command, shell=True)
    clusterInstances = int(clusterInstances.decode().strip())

    os.system("echo Number of instances is: " + str(clusterInstances))


# Delete service and deployment of REST server
os.system("echo Deleting REST server resources")
os.system("kubectl delete service rest-server-service")
os.system("kubectl delete deployment rest-server-deployment")

# Delete InnoDB cluster
os.system("echo Deleting InnoDB Cluster")
os.system("kubectl delete innodbcluster my-innodb-cluster")

# Delete InnoDB cluster PVC's
os.system("echo Deleting InnoDB Cluster PVC's")
command = 'kubectl get pvc | awk "/datadir-my-innodb-cluster/ {print $1}"'
result = subprocess.check_output(command, shell=True)
result = result.decode().strip()
pVolumeClaims = result.split("\n")

for x in range(0, clusterInstances, 1):
    command = "kubectl delete pvc " + pVolumeClaims.__getitem__(x)
    subprocess.run(command, shell=True)

# Delete InnoDB Cluster backup PVC and PV
os.system("kubectl delete pvc innodb-backup-pvc")
os.system("kubectl delete pv innodb-backup-pv")

# Delete config map and secret
os.system("echo Deleting config map and secret")
os.system("kubectl delete configmap dvd-config")
os.system("kubectl delete secret db-secret")

# Delete pod used to initialize the cluster
os.system("echo Deleting InnoDB cluster initialization pod")
os.system("kubectl delete pod mysql-init-cluster")
