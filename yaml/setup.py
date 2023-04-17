# This file aims to automatically configure a minikube cluster. The sequence of the steps is strict
# and should be followed closely. An InnoDB cluster is created with master node mysql-clusterling-0
# and 2 read only instances.

import os
import subprocess

db_desired_replicas=-1

def checkForPodReadiness(podSet, resourceType):
    os.system("echo Checking readiness for " + resourceType + ": " + podSet)
    # Get number of desired pods
    command = "kubectl describe " + resourceType + " " + podSet + " -n default | awk \'/Replicas:/ {print $2}\'"
    desired_replicas = subprocess.check_output(command, shell=True)
    desired_replicas = int(desired_replicas.decode().strip())
    os.system("echo Number of desired replicas: " + str(desired_replicas))
    os.system("echo Waiting for replica creation")

    if podSet=="mysql-clusterling":
        global db_desired_replicas
        db_desired_replicas=desired_replicas

    current_status = "Not ready"
    while current_status != "1/1":
        # Check if last replica of the statefulset has been created and is ready
        if resourceType=="deployment":
            command = "kubectl get pods | awk '/"+podSet+ "/ {print $2}'"
        if resourceType=="sts":
            command = "kubectl get pods | awk '/"+podSet+"-" + str(desired_replicas - 1) + "/ {print $2}'"
        current_status = subprocess.check_output(command, shell=True)
        current_status = current_status.decode().strip()
    os.system("echo All Pods Ready")

def configureInstances():
    global db_desired_replicas
    os.system("echo Configuring MySQL Instances")
    for x in range(0, db_desired_replicas, 1):
        os.system("echo Configuring mysql-clusterling-" + str(x))

        command= """kubectl exec mysql-clusterling-""" + str(x) + """ -- mysqlsh --user=root --password=icsd15201 --port=3306 \
        --execute="session.runSql('SET GLOBAL super_read_only = OFF;'); \
         var data = {host: 'localhost', port:3306, user:'root', password: 'icsd15201'}; \
          var options = {interactive: false}; \
          dba.configureInstance(data,options);" """

        result = subprocess.run(command, shell=True)
        exit_code = result.returncode

        if int(exit_code) == 0:
            os.system("echo Configuration successful")
        else:
            os.system("echo ERROR: Configuration failed, instance will not be added to cluster")

def addInstancesToCluster():
    global db_desired_replicas
    os.system("echo Configuring Master Node: mysql-clusterling-0")
    os.system("echo Will create cluster with " + str(db_desired_replicas) + " Instances" )

    for x in range(0, db_desired_replicas, 1):

        # Configure master node and create cluster
        if x==0:
            os.system("echo Configuring Master Node: mysql-clusterling-0")
            command = """kubectl exec mysql-clusterling-0 -- mysqlsh -u 'root' --password=icsd15201 --port=3306 --execute="dba.createCluster('MyCluster');"""
        # Add instances to cluster
        else:
            os.system("echo Adding instance mysql-clusterling-" + str(x) + " to cluster")
            command = """kubectl exec mysql-clusterling-0 -- mysqlsh -u root --password=icsd15201 --port=3306 \
                      --execute="var cluster=dba.getCluster();\
                       var options = {recoveryMethod: 'clone'};\
                       cluster.addInstance('root:icsd15201@mysql-clusterling-""" + str(
                x) + """.mysql-service.default.svc.cluster.local:3306', options);" """

        result = subprocess.run(command, shell=True)
        exit_code = result.returncode
        if int(exit_code) == 0:
            os.system("echo Configuration successful")
        else:
            # If configuration has failed, could be because instance is already part of a replication group
            os.system("echo ERROR: Configuration failed, retrying")

            # Connect to instance through MySQL Shell and try to resolve the issue
            command="""kubectl exec mysql-clusterling-"""+str(x)+""" \
            -- mysqlsh -u 'root' --password=icsd15201 --port=3306 --execute="\
             session.runSql('STOP GROUP_REPLICATION;').execute(); \
             session.runSql('RESET MASTER;').execute(); \
             session.runSql('RESET REPLICA;').execute();" """
            result = subprocess.run(command, shell=True)

            # Re-connect to master node and try to add instance again.
            command="""kubectl exec mysql-clusterling-0 -- mysqlsh -u 'root' --password=icsd15201 --port=3306 \
            --execute="var cluster=dba.getCluster();\
             var options = {recoveryMethod: 'clone'};\
               cluster.addInstance('mysql-clusterling-"""+str(x)+""".mysql-service.default.svc.cluster.local', options);" """
            result = subprocess.run(command, shell=True)

os.system("echo Initializing...")

# 0) Create config map and secret
os.system("kubectl apply -f dvd-conf.yaml")
os.system("kubectl apply -f db-secret.yaml")

# 1) DB Headless service is started
os.system("kubectl apply -f ./mysql/mysql-service.yaml")

# 2) DB statefulset is applied, pods are created
os.system("kubectl apply -f ./mysql/mysql-stateful-set.yaml")

# Make sure all pods are ready before proceeding
checkForPodReadiness("mysql-clusterling", "sts")

# 3) Configure DB Servers for InnoDB Cluster
configureInstances()

# 4) DB statefulset is restarted AFTER nodes are created ( and hence configured )
os.system("echo Restarting stateful set")
command = 'kubectl rollout restart statefulset mysql-clusterling'
result = subprocess.run(command, shell=True)

# Check that all nodes have restarted successfully and are ready
os.system("kubectl rollout status statefulset mysql-clusterling")

# 5) Execute script on master node to create InnoDB Cluster ( node 0 )
addInstancesToCluster()

# 6) ROUTER Headless service is started
os.system("echo Creating MySQL Router Service")
os.system("kubectl apply -f ./mysql/mysql-router-service.yaml")

# 7) ROUTER statefulset is applied, pod created
os.system("echo Creating MySQL Router Set")
os.system("kubectl apply -f ./mysql/mysql-router-stateful-set.yaml")

# Check when router will be ready and operational
checkForPodReadiness("mysql-router", "sts")

# 8) REST SERVER service is applied
os.system("echo Creating Rest Server Service")
os.system("kubectl apply -f ./restserver/restserver-service.yaml")

# 9) REST SERVER deployment is applied, pod created
os.system("echo Creating Rest Server Deployment")
os.system("kubectl apply -f ./restserver/restserver-deployment.yaml")

checkForPodReadiness("rest-server-deployment", "deployment")

# 10) Exposing rest server outside of cluster through minikube
# NOTE: Terminal window must stay open
os.system("echo Exposing Rest Server Service")
os.system("minikube service rest-server-service")