# This file aims to automatically configure a minikube cluster. The sequence of the steps is strict
# and should be followed closely.
# DISCLAIMER: Currently has not been tested

# 1) DB Headless service is started
kubectl apply -f mysql/mysql-service.yaml

# 2) DB statefulset is applied, pods are created
kubectl apply -f mysql/mysql-stateful-set.yaml

# 3) Configure DB Servers for InnoDB Cluster ( add a loop here )
kubectl exec mysql-clusterling-0 -- mysqlsh --user=root --password=icsd15201 --execute="session.runSql('SET GLOBAL super_read_only = OFF;'); var data = {host: 'localhost', port:3306, user:'root', password: 'icsd15201'}; var options = {interactive: false}; dba.configureInstance(data,options);"
kubectl exec mysql-clusterling-1 -- mysqlsh --user=root --password=icsd15201 --execute="session.runSql('SET GLOBAL super_read_only = OFF;'); var data = {host: 'localhost', port:3306, user:'root', password: 'icsd15201'}; var options = {interactive: false}; dba.configureInstance(data,options);"
kubectl exec mysql-clusterling-2 -- mysqlsh --user=root --password=icsd15201 --execute="session.runSql('SET GLOBAL super_read_only = OFF;'); var data = {host: 'localhost', port:3306, user:'root', password: 'icsd15201'}; var options = {interactive: false}; dba.configureInstance(data,options);"

# 4) DB statefulset is restarted AFTER nodes are created ( and hence configured )
kubectl rollout restart statefulset mysql-stateful-set

# 5) Execute script on master node to create InnoDB Cluster ( node 0 )
kubectl exec  kubectl exec mysql-clusterling-0 -- mysqlsh -u 'root' --password='icsd15201' --file=create-cluster.js

# 6) ROUTER Headless service is started
kubectl apply -f mysql/mysql-router-service.yaml

# 7) ROUTER statefulset is applied, pod created
kubectl apply -f mysql/mysql-router-stateful-set.yaml

# 8) REST SERVER service is applied
kubectl apply -f restserver/restserver-service.yaml

# 9) REST SERVER deployment is applied, pod created
kubectl apply -f restserver/restserver-deployment.yaml

# 10) Exposing rest server outside of cluster through minikube
# NOTE: Terminal window must stay open
minikube service rest-server-service