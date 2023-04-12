:: This file works, but it is strongly recommended to use the
:: .bat script that relies on the python one instead

:: Delete service and deployment of rest server
kubectl delete service rest-server-service
kubectl delete deployment rest-server-deployment

:: Delete MySQL Router service and set
kubectl delete service mysql-router-service
kubectl delete statefulset mysql-router

:: Delete MySQL DB Set and services
kubectl delete statefulset mysql-clusterling
kubectl delete service mysql-service

:: Delete PVC
kubectl delete pvc db-volume-mysql-clusterling-0
kubectl delete pvc db-volume-mysql-clusterling-1
kubectl delete pvc db-volume-mysql-clusterling-2

:: Delete PV
kubectl get pv | awk '{ if ($6 ~ /default\/db-volume-mysql-clusterling/) print $1 }' | xargs -d \r kubectl delete pv
